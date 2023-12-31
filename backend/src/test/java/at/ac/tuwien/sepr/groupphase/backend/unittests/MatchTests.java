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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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

    @Test
    void extensiveMatchTest() {
        VEvent vevent = new VEvent();
        vevent.add(new PropertyBuilder().name("BEGIN").value("VEVENT").build());
        vevent.add(new Summary("194.026 Funktionale Programmierung"));
        vevent.add(new Description("Funktionale Programmierung"));
        vevent.add(new Location("EI 3 Sahulka HS - UIW"));
        vevent.add(new PropertyBuilder().name("END").value("VEVENT").build());

        Match m = new Match();
        m.setId(1);
        assertAll(
            () -> {
                m.setSummary(MatchType.EQUALS, "194.026 Funktionale Programmierung");
                m.setDescription(MatchType.EQUALS, "Funktionale Programmierung");
                m.setLocation(MatchType.EQUALS, "EI 3 Sahulka HS - UIW");
                try {
                    assertTrue(m.matches(vevent));
                } catch (AssertionError e) {
                    fail(vevent.toString() + '\n' + m + '\n' + e);
                }
            },
            () -> {
                m.setSummary(MatchType.EQUALS, null);
                m.setDescription(MatchType.EQUALS, null);
                m.setLocation(MatchType.EQUALS, null);
                try {
                    assertTrue(m.matches(vevent));
                } catch (AssertionError e) {
                    fail(vevent.toString() + '\n' + m + '\n' + e);
                }
            },
            () -> {
                m.setSummary(MatchType.REGEX, ".*");
                m.setDescription(MatchType.REGEX, ".*");
                m.setLocation(MatchType.REGEX, ".*");
                try {
                    assertTrue(m.matches(vevent));
                } catch (AssertionError e) {
                    fail(vevent.toString() + '\n' + m + '\n' + e);
                }
            },
            () -> {
                m.setSummary(MatchType.REGEX, ".*unktional.*ierung.*");
                m.setDescription(MatchType.REGEX, ".*unktional.*ierung.*");
                m.setLocation(MatchType.REGEX, "EI [1-9]*.* HS.*");
                try {
                    assertTrue(m.matches(vevent));
                } catch (AssertionError e) {
                    fail(vevent.toString() + '\n' + m + '\n' + e);
                }
            },
            () -> {
                m.setSummary(MatchType.CONTAINS, "");
                m.setDescription(MatchType.CONTAINS, "");
                m.setLocation(MatchType.CONTAINS, "");
                try {
                    assertTrue(m.matches(vevent));
                } catch (AssertionError e) {
                    fail(vevent.toString() + '\n' + m + '\n' + e);
                }
            },
            () -> {
                m.setSummary(MatchType.STARTS_WITH, "194.026");
                m.setDescription(MatchType.STARTS_WITH, "");
                m.setLocation(MatchType.STARTS_WITH, "");
                try {
                    assertTrue(m.matches(vevent));
                } catch (AssertionError e) {
                    fail(vevent.toString() + '\n' + m + '\n' + e);
                }
            },
            () -> {
                m.setSummary(MatchType.EQUALS, "");
                m.setDescription(MatchType.EQUALS, null);
                m.setLocation(MatchType.EQUALS, null);
                try {
                    assertTrue(m.matches(vevent));
                } catch (AssertionError e) {
                    fail(vevent.toString() + '\n' + m + '\n' + e);
                }
            },
            () -> {
                m.setSummary(MatchType.REGEX, ".*");
                m.setDescription(MatchType.REGEX, "");
                m.setLocation(MatchType.REGEX, ".*");
                try {
                    assertTrue(m.matches(vevent));
                } catch (AssertionError e) {
                    fail(vevent.toString() + '\n' + m + '\n' + e);
                }
            },
            () -> {
                m.setSummary(MatchType.REGEX, ".*unktional.*ierung.*");
                m.setDescription(MatchType.REGEX, ".*unktional.*ierung.*");
                m.setLocation(MatchType.REGEX, "EI [1-9]*.* HS");
                try {
                    assertFalse(m.matches(vevent));
                } catch (AssertionError e) {
                    fail(vevent.toString() + '\n' + m + '\n' + e);
                }
            },
            () -> {
                m.setSummary(MatchType.CONTAINS, "");
                m.setDescription(MatchType.CONTAINS, "");
                m.setLocation(MatchType.CONTAINS, ".*");
                try {
                    assertFalse(m.matches(vevent));
                } catch (AssertionError e) {
                    fail(vevent.toString() + '\n' + m + '\n' + e);
                }
            },
            () -> {
                m.setSummary(MatchType.STARTS_WITH, "");
                m.setDescription(MatchType.STARTS_WITH, "");
                m.setLocation(MatchType.STARTS_WITH, "not start");
                try {
                    assertFalse(m.matches(vevent));
                } catch (AssertionError e) {
                    fail(vevent.toString() + '\n' + m + '\n' + e);
                }
            });

    }
}
