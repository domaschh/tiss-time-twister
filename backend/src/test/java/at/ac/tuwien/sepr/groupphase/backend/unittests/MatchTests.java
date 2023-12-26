package at.ac.tuwien.sepr.groupphase.backend.unittests;


import at.ac.tuwien.sepr.groupphase.backend.entity.Match;
import at.ac.tuwien.sepr.groupphase.backend.entity.MatchType;
import net.fortuna.ical4j.model.PropertyBuilder;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Summary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class MatchTests {

    @Test
    void matchBySummary() {
        VEvent vevent = new VEvent();
        vevent.add(new PropertyBuilder().name("BEGIN").value("VEVENT").build());
        vevent.add(new Summary("194.026 Funktionale Programmierung"));
        vevent.add(new PropertyBuilder().name("END").value("VEVENT").build());

        Match m = new Match();
        m.setId(1);
        m.setSummaryMatchType(MatchType.EQUALS);
        m.setSummary("194.026 Funktionale Programmierung");

        assertTrue(m.matches(vevent));
    }

    @Test
    void matchBySummaryAndDescription() {
        VEvent vevent = new VEvent();
        vevent.add(new PropertyBuilder().name("BEGIN").value("VEVENT").build());
        vevent.add(new Summary("194.026 Funktionale Programmierung"));
        vevent.add(new Description("Funktionale Programmierung"));
        vevent.add(new PropertyBuilder().name("END").value("VEVENT").build());

        Match m = new Match();
        m.setId(1);
        m.setSummary("194.026");
        m.setDescription("Programmierung");
        m.setSummaryMatchType(MatchType.STARTS_WITH);
        m.setDescriptionMatchType(MatchType.CONTAINS);

        assertTrue(m.matches(vevent));
    }

    @Test
    void matchBySummaryAndDescriptionAndLocation() {
        VEvent vevent = new VEvent();
        vevent.add(new PropertyBuilder().name("BEGIN").value("VEVENT").build());
        vevent.add(new Summary("194.026 Funktionale Programmierung"));
        vevent.add(new Description("Funktionale Programmierung"));
        vevent.add(new Location("EI 3 Sahulka HS - UIW"));
        vevent.add(new PropertyBuilder().name("END").value("VEVENT").build());

        Match m = new Match();
        m.setId(1);
        m.setSummary(MatchType.STARTS_WITH, "194.026");
        m.setDescription(MatchType.EQUALS, "Funktionale Programmierung");
        m.setLocation(MatchType.CONTAINS, "Sahulka");

        assertTrue(m.matches(vevent));
    }

    @Test
    void matchBySummaryAndDescriptionAndLocationDoesNotMatch() {
        VEvent vevent = new VEvent();
        vevent.add(new PropertyBuilder().name("BEGIN").value("VEVENT").build());
        vevent.add(new Summary("194.026 Funktionale Programmierung"));
        vevent.add(new Description("Funktionale Programmierung"));
        vevent.add(new Location("EI 3 Sahulka HS - UIW"));
        vevent.add(new PropertyBuilder().name("END").value("VEVENT").build());

        Match m = new Match();
        m.setId(1);
        m.setSummary(MatchType.STARTS_WITH, "194.026 Funktionale Programmierung");
        m.setDescription(MatchType.CONTAINS, "NotTheDescription");
        m.setLocation(MatchType.CONTAINS, "Sahulka");

        assertFalse(m.matches(vevent));
    }
}
