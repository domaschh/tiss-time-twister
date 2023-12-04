package at.ac.tuwien.sepr.groupphase.backend.unittests;


import at.ac.tuwien.sepr.groupphase.backend.entity.Match;
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
public class MatchTests {

    @Test
    public void matchBySummary() {
        VEvent vevent = new VEvent();
        vevent.add(new PropertyBuilder().name("BEGIN").value("VEVENT").build());
        vevent.add(new Summary("194.026 Funktionale Programmierung"));
        vevent.add(new PropertyBuilder().name("END").value("VEVENT").build());

        Match m = new Match();
        m.setId(1);
        m.setSummary("194.026 Funktionale Programmierung");

        assertTrue(m.matches(vevent));
    }

    @Test
    public void matchBySummaryAndDescription() {
        VEvent vevent = new VEvent();
        vevent.add(new PropertyBuilder().name("BEGIN").value("VEVENT").build());
        vevent.add(new Summary("194.026 Funktionale Programmierung"));
        vevent.add(new Description("Funktionale Programmierung"));
        vevent.add(new PropertyBuilder().name("END").value("VEVENT").build());

        Match m = new Match();
        m.setId(1);
        m.setSummary("194.026 Funktionale Programmierung");
        m.setDescription("Funktionale Programmierung");

        assertTrue(m.matches(vevent));
    }

    @Test
    public void matchBySummaryAndDescriptionAndLocation() {
        VEvent vevent = new VEvent();
        vevent.add(new PropertyBuilder().name("BEGIN").value("VEVENT").build());
        vevent.add(new Summary("194.026 Funktionale Programmierung"));
        vevent.add(new Description("Funktionale Programmierung"));
        vevent.add(new Location("EI 3 Sahulka HS - UIW"));
        vevent.add(new PropertyBuilder().name("END").value("VEVENT").build());

        Match m = new Match();
        m.setId(1);
        m.setSummary("194.026 Funktionale Programmierung");
        m.setDescription("Funktionale Programmierung");
        m.setLocation("EI 3 Sahulka HS - UIW");

        assertTrue(m.matches(vevent));
    }
    @Test
    public void matchBySummaryAndDescriptionAndLocationDoesNotMatch() {
        VEvent vevent = new VEvent();
        vevent.add(new PropertyBuilder().name("BEGIN").value("VEVENT").build());
        vevent.add(new Summary("194.026 Funktionale Programmierung"));
        vevent.add(new Description("Funktionale Programmierung"));
        vevent.add(new Location("EI 3 Sahulka HS - UIW"));
        vevent.add(new PropertyBuilder().name("END").value("VEVENT").build());

        Match m = new Match();
        m.setId(1);
        m.setSummary("194.026 Funktionale Programmierung");
        m.setDescription("NotTheDescription");
        m.setLocation("EI 3 Sahulka HS - UIW");

        assertFalse(m.matches(vevent));
    }
}
