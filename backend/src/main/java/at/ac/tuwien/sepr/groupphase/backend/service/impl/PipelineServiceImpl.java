package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.LVADetail;
import at.ac.tuwien.sepr.groupphase.backend.TissRoom;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarService;
import at.ac.tuwien.sepr.groupphase.backend.service.PipelineService;
import at.ac.tuwien.sepr.groupphase.backend.service.TissService;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//BEGIN:VEVENT
//    DTSTAMP:20231204T175902Z
//    DTSTART;TZID=Europe/Vienna:20231003T110000
//    DTEND;TZID=Europe/Vienna:20231003T130000
//    SUMMARY:194.026 VU Funktionale Programmierung
//    LOCATION:EI 3 Sahulka HS - UIW
//    CATEGORIES:COURSE
//    DESCRIPTION:194.026 Funktionale Programmierung
//    UID:20231215T131308Z-4927809@tiss.tuwien.ac.at
//END:VEVENT
@Service
public class PipelineServiceImpl implements PipelineService {
    private final CalendarService calendarService;
    private final CalendarReferenceRepository calendarReferenceRepository;
    private final TissService tissService;

    public PipelineServiceImpl(CalendarService calendarService, CalendarReferenceRepository calendarReferenceRepository,
                               TissService tissService) {
        this.calendarService = calendarService;
        this.calendarReferenceRepository = calendarReferenceRepository;
        this.tissService = tissService;
    }

    @Override
    public Calendar pipeCalendar(UUID token) throws ParserException, IOException, URISyntaxException {
        Optional<CalendarReference> optionalCalendarReference = calendarReferenceRepository.findCalendarReferenceByToken(token);
        if (optionalCalendarReference.isEmpty()) {
            throw new NotFoundException("Calendar with token " + token + " does not exist");
        }
        var calendar = calendarService.fetchCalendarByUrl(optionalCalendarReference.get().getLink());
        List<Configuration> configurations = optionalCalendarReference.get().getConfigurations();
        return applyConfigurations(calendar, configurations, optionalCalendarReference.get().getEnabledDefaultConfigurations());
    }

    @Override
    public Calendar previewConfiguration(long id, List<Configuration> configurations) throws ParserException, IOException, URISyntaxException {
        Optional<CalendarReference> optionalCalendarReference = calendarReferenceRepository.findById(id);
        if (optionalCalendarReference.isEmpty()) {
            throw new NotFoundException("Calendar with id " + id + " does not exist");
        }
        var calendar = calendarService.fetchCalendarByUrl(optionalCalendarReference.get().getLink());
        return applyConfigurations(calendar, configurations, optionalCalendarReference.get().getEnabledDefaultConfigurations());
    }

    private Calendar applyConfigurations(Calendar calendar, List<Configuration> configurations, Long enabledDefaultConfigurations) {
        List<CalendarComponent> newComponents = new ArrayList<>();
        newComponents.add(calendar.getComponentList().getAll().get(0));
        calendar.getComponentList().getAll().stream().filter(VEvent.class::isInstance).forEach(v -> {
            VEvent vEvent = (VEvent) v;
            VEvent modifiedVEvent = configurations.stream()
                                                  .flatMap(configuration -> configuration.getRules().stream())
                                                  .reduce(vEvent, (currentVEvent, rule) -> {
                                                      if (rule.getMatch().matches(currentVEvent)) {
                                                          return rule.getEffect().apply(currentVEvent);
                                                      } else {
                                                          return currentVEvent;
                                                      }
                                                  }, (VEvent vEvent1, VEvent vEvent2) -> vEvent2);
            if (modifiedVEvent != null && enabledDefaultConfigurations != null) {
                enhanceTissEvent(modifiedVEvent, enabledDefaultConfigurations);
                newComponents.add(modifiedVEvent);
            }
        });
        var componentList = new ComponentList<>(newComponents);
        calendar.setComponentList(componentList);
        return calendar;
    }

    private void enhanceTissEvent(VEvent vEvent, Long enabledDefaultConfigurations) {

        if ((enabledDefaultConfigurations & 0b1) > 0) {
            LVADetail detail;
            if (vEvent.getSummary().isPresent()) {
                detail = tissService.mapLVANameShorthand(onlyLongName(vEvent.getSummary().get().toString().trim()));
            } else {
                detail = null;
            }
            if (detail != null) {
                vEvent.getProperty(Property.SUMMARY).ifPresent(a -> a.setValue(detail.shorthand()));
            }
        }

        if ((enabledDefaultConfigurations & 0b10) > 0) {
            TissRoom tissRoom;
            if (vEvent.getLocation().isPresent()) {
                tissRoom = tissService.fetchCorrectLocation(this.onlyLongName(vEvent.getLocation().get().toString()));
            } else {
                tissRoom = null;
            }

            if (tissRoom != null) {
                vEvent.getProperty(Property.LOCATION).ifPresent(a -> a.setValue(tissRoom.address()));
            }
        }
    }

    private String onlyLongName(String input) {
        String[] parts = input.split(" ");
        if (parts.length > 2) {
            return String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
        } else {
            return null;
        }
    }
}
