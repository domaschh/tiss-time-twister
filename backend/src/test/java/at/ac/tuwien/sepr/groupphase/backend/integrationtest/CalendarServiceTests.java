package at.ac.tuwien.sepr.groupphase.backend.integrationtest;


import at.ac.tuwien.sepr.groupphase.backend.service.CalendarService;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CalendarServiceTests {
    private static final String TISS_URL = "https://tiss.tuwien.ac.at/events/rest/calendar/personal?locale=de&token=58e1c7c3-bcd9-4c0e-9b63-5a6aed29ab2a";
    private static final String TUWEL_URL = "https://tuwel.tuwien.ac.at/calendar/export_execute.php?userid=159114&authtoken=90eac1be94543b058aaff7751c03bbf47bdcbd08&preset_what=all&preset_time=custom";

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

    @Autowired
    private CalendarService calendarService;

    @Test
    void fetchCalendarByUrl() throws ParserException, IOException, URISyntaxException {
        Calendar calendar = calendarService.fetchCalendarByUrl(TISS_URL);

        var list = calendar.getComponentList();
        list.getAll().forEach(event -> System.out.println(event + "" + event.getClass()));
        assertNotNull(calendar);
    }

    @Test
    void fetchCalendarByUrl_InvalidUrl_Fails() {
        assertAll(
            () -> assertThrows(ParserException.class, () -> calendarService.fetchCalendarByUrl("https://url.com")),
            () -> assertThrows(URISyntaxException.class, () -> calendarService.fetchCalendarByUrl("not correct URI syntax")));
    }

    @Test
    void fetchTuwelCalendarWorks() throws ParserException, IOException, URISyntaxException {
        Calendar calendar = calendarService.fetchCalendarByUrl(TUWEL_URL);

        var list = calendar.getComponentList();
        list.getAll().forEach(event -> System.out.println(event + "" + event.getClass()));
        assertNotNull(calendar);

    }

}
