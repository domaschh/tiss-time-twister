package at.ac.tuwien.sepr.groupphase.backend.unittests;


import at.ac.tuwien.sepr.groupphase.backend.entity.Effect;
import at.ac.tuwien.sepr.groupphase.backend.entity.EffectType;
import net.fortuna.ical4j.model.PropertyBuilder;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Summary;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class EffectTests {

    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", postgres::getUsername);
        propertyRegistry.add("spring.datasource.password", postgres::getPassword);
    }

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
        Effect e = new Effect();
        e.setEffectType(EffectType.DELETE);
        assertNull(e.apply(vEvent));
    }

    @Test
    void modifyingEffect1Proprty() {
        Effect m = new Effect("new Title", null, null, EffectType.MODIFY);
        var result = m.apply(vEvent);
        assertNotNull(result);
        assertEquals("new Title", result.getSummary().get().getValue());
        assertEquals("old", result.getDescription().get().getValue());
        assertEquals("old", result.getLocation().get().getValue());
    }

    @Test
    void modifyingEffect0Proprty() {
        Effect m = new Effect(null, null, null, EffectType.MODIFY);
        var result = m.apply(vEvent);
        assertNotNull(result);
        assertEquals(result, vEvent);
    }

    @Test
    void modifyingEffectAllProperties() {
        Effect m = new Effect("new title", "new Description", "new Location", EffectType.MODIFY);
        var result = m.apply(vEvent);
        assertNotNull(result);
        assertEquals("new title", result.getSummary().get().getValue());
        assertEquals("new Description", result.getDescription().get().getValue());
        assertEquals("new Location", result.getLocation().get().getValue());
    }
}
