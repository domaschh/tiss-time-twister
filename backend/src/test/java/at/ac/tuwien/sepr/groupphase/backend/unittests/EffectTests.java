package at.ac.tuwien.sepr.groupphase.backend.unittests;


import at.ac.tuwien.sepr.groupphase.backend.entity.DeleteEffect;
import at.ac.tuwien.sepr.groupphase.backend.entity.ModifyEffect;
import net.fortuna.ical4j.model.PropertyBuilder;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Summary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class EffectTests {

    VEvent vEvent;

    @BeforeEach
    void setup() {
        vEvent = new VEvent();
        vEvent.add(new PropertyBuilder().name("BEGIN").value("VEVENT").build());
        vEvent.add(new Summary("old"));
        vEvent.add(new Description("old"));
        vEvent.add(new Location("old"));
        vEvent.add(new PropertyBuilder().name("END").value("VEVENT").build());
    }

    @Test
    void deleteEffect() {
        DeleteEffect e = new DeleteEffect();
        assertNull(e.apply(vEvent));
    }

    @Test
    void modifyingEffect1Proprty() {
        ModifyEffect m = new ModifyEffect("new Title", null, null);
        var result = m.apply(vEvent);
        assertNotNull(result);
        assertEquals("new Title", result.getSummary().get().getValue());
        assertEquals("old", result.getDescription().get().getValue());
        assertEquals("old", result.getLocation().get().getValue());
    }

    @Test
    void modifyingEffect0Proprty() {
        ModifyEffect m = new ModifyEffect(null, null, null);
        var result = m.apply(vEvent);
        assertNotNull(result);
        assertEquals(result, vEvent);
    }

    @Test
    void modifyingEffectAllProperties() {
        ModifyEffect m = new ModifyEffect("new title", "new Description", "new Location");
        var result = m.apply(vEvent);
        assertNotNull(result);
        assertEquals("new title", result.getSummary().get().getValue());
        assertEquals("new Description", result.getDescription().get().getValue());
        assertEquals("new Location", result.getLocation().get().getValue());
    }
}
