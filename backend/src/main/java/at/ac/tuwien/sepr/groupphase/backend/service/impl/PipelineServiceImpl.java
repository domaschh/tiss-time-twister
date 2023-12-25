package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.entity.DeleteEffect;
import at.ac.tuwien.sepr.groupphase.backend.entity.Match;
import at.ac.tuwien.sepr.groupphase.backend.entity.Rule;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.ConfigurationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarService;
import at.ac.tuwien.sepr.groupphase.backend.service.PipelineService;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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

    public PipelineServiceImpl(CalendarService calendarService, CalendarReferenceRepository calendarReferenceRepository) {
        this.calendarService = calendarService;
        this.calendarReferenceRepository = calendarReferenceRepository;
    }

    @Override
    public Calendar pipeCalendar(UUID token) throws ParserException, IOException, URISyntaxException {
        var calendarReference = calendarReferenceRepository.findCalendarReferenceByToken(token);
        var calendar = calendarService.fetchCalendarByUrl(calendarReference.getLink());
        List<Configuration> configurations = calendarReference.getConfigurations();
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
                                                  }, (vEvent1, vEvent2) -> vEvent2);

            newComponents.add(modifiedVEvent);
        });
        var componentList = new ComponentList<>(newComponents);
        calendar.setComponentList(componentList);
        return calendar;
    }
}
