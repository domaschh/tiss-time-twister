package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.entity.DeleteEffect;
import at.ac.tuwien.sepr.groupphase.backend.entity.Match;
import at.ac.tuwien.sepr.groupphase.backend.entity.Rule;
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

    public PipelineServiceImpl(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Override
    public Calendar pipeCalendar(String calendarUrl) throws ParserException, IOException, URISyntaxException {
        //TODO: This is just mock pls remove alter and query all the configurations the user has
        Configuration c = new Configuration();
        Rule r = new Rule();
        Match m = new Match();
        m.setSummary("194.026 VU Funktionale Programmierung");
        r.setMatch(m);
        r.setEffect(new DeleteEffect());
        c.setRules(List.of(r));

        //Actual Service work
        Calendar calendar = calendarService.fetchCalendarByUrl(calendarUrl);
        List<CalendarComponent> newComponents = new ArrayList<>();
        newComponents.add(calendar.getComponentList().getAll().get(0));

        for (var event : calendar.getComponentList().getAll().stream().filter(VEvent.class::isInstance).toList()) {
            var vEvent = (VEvent) event;
            for (Rule ruleToApply : c.getRules()) {
                if (ruleToApply.getMatch().matches(vEvent)) {
                    vEvent = ruleToApply.getEffect().apply(vEvent);
                }
            }
            if (vEvent != null) {
                newComponents.add(vEvent);
            }
        }
        var componentList = new ComponentList<>(newComponents);
        calendar.setComponentList(componentList);
        return calendar;
    }
}
