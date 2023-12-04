package at.ac.tuwien.sepr.groupphase.backend.unittests;


import at.ac.tuwien.sepr.groupphase.backend.entity.DeleteEffect;
import at.ac.tuwien.sepr.groupphase.backend.entity.ModifyEffect;
import jakarta.persistence.OneToMany;
import net.fortuna.ical4j.model.PropertyBuilder;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Summary;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EffectTests {

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
        assertEquals(Optional.empty(),e.apply(vEvent));
    }

    @Test
    void modifyingEffect1Proprty() {
        ModifyEffect m = new ModifyEffect("new Title", null, null);
        var result = m.apply(vEvent);
        assertEquals(result.isPresent(), true);
        assertEquals(result.get().getSummary().get().getValue(), "new Title");
        assertEquals(result.get().getDescription().get().getValue(), "old");
        assertEquals(result.get().getLocation().get().getValue(), "old");
    }

    @Test
    void modifyingEffect0Proprty() {
        ModifyEffect m = new ModifyEffect(null, null, null);
        var result = m.apply(vEvent);
        assertEquals(result.isPresent(), true);
        assertEquals(result.get(), vEvent);
    }

    @Test
    void modifyingEffectAllProperties() {
        ModifyEffect m = new ModifyEffect("new title", "new Description", "new Location");
        var result = m.apply(vEvent);
        assertEquals(result.isPresent(), true);
        assertEquals(result.get().getSummary().get().getValue(), "new title");
        assertEquals(result.get().getDescription().get().getValue(), "new Description");
        assertEquals(result.get().getLocation().get().getValue(), "new Location");
    }
}
