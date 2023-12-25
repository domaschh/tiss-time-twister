package at.ac.tuwien.sepr.groupphase.backend.integrationtest;

import at.ac.tuwien.sepr.groupphase.backend.entity.*;
import at.ac.tuwien.sepr.groupphase.backend.service.PipelineService;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.component.VEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PipelineServiceTests {
    private static final CalendarReference cal = new CalendarReference();

    @Autowired
    private PipelineService pipelineService;

    @Spy
    private CalendarService calendarService;
    @Spy
    private CalendarReferenceRepository calendarReferenceRepository;


    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Test
    void testIcsEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test-cal"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));
    }

    @Test
    void testCalendarWithCustomEndpoint() throws ParserException, IOException, URISyntaxException {
        cal.setId(0L);
        String customMockUrl = "http://localhost:" + port + "/test-cal";
        cal.setName("Test_Calendar");
        cal.setLink(customMockUrl);
        var returnedCalendar = calendarService.pipeCalendar(cal);
        assertEquals(returnedCalendar.getComponentList().getAll().size(),269);
    }

    @Test
    void removesAllFunktionaleProgrammierungEvents() throws ParserException, IOException, URISyntaxException {
        String fileContent = TestUtility.loadIcsFileAsString("domasch_fixed.ics");

        Configuration configuration = new Configuration();
        Rule r = new Rule();
        Match m = new Match();
        Effect e = new DeleteEffect();
        m.setSummary("194.026 VU Funktionale Programmierung");
        CalendarReference calendarReference = new CalendarReference();
        calendarReference.setLink(TISS_URL);

        configuration.setRules(List.of(r));
        r.setEffect(e);
        r.setMatch(m);
        calendarReference.setConfigurations(List.of(configuration));
        UUID calendarUUID = UUID.randomUUID();
        calendarReference.setToken(calendarUUID);
        Mockito
            .when(calendarReferenceRepository.findCalendarReferenceByToken(calendarUUID))
            .thenReturn(calendarReference);

        Mockito.when(calendarService.fetchCalendarByUrl(calendarReference.getLink())).thenReturn(TestUtility.loadCalendarForTests("domasch_fixed.ics"));

        var returnedCalendar = pipelineService.pipeCalendar(calendarUUID);

        var numberOfFProgEvents = returnedCalendar
            .getComponentList()
            .getAll()
            .stream()
            .filter(VEvent.class::isInstance)
            .filter(c -> ((VEvent) c).getSummary().get().getValue().equals("194.026 VU Funktionale Programmierung"))
            .toList()
            .size();
        assertEquals(0, numberOfFProgEvents);
        Assertions.assertThat(returnedCalendar.getComponentList().getAll()).isNotEmpty();
    }
}
