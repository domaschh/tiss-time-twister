package at.ac.tuwien.sepr.groupphase.backend.integrationtest;

import at.ac.tuwien.sepr.groupphase.backend.TestUtility;
import at.ac.tuwien.sepr.groupphase.backend.entity.*;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarService;
import at.ac.tuwien.sepr.groupphase.backend.service.PipelineService;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.component.VEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PipelineServiceTests {
    private CalendarReference cal = new CalendarReference();

    @Autowired
    private PipelineService pipelineService;

    @Autowired
    private CalendarService calendarService;

    @MockBean
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
        var returnedCalendar = calendarService.fetchCalendarByUrl(customMockUrl);
        assertEquals(310, returnedCalendar.getComponentList().getAll().size());
    }

    @Test
    void removesAllFunktionaleProgrammierungEvents() throws ParserException, IOException, URISyntaxException {
        String customMockUrl = "http://localhost:" + port + "/test-cal";


        Configuration configuration = new Configuration();
        Rule r = new Rule();
        Match m = new Match();
        Effect e = new DeleteEffect();
        m.setSummary("194.026 VU Funktionale Programmierung");
        CalendarReference calendarReference = new CalendarReference();
        calendarReference.setLink(customMockUrl);

        configuration.setRules(List.of(r));
        r.setEffect(e);
        r.setMatch(m);
        calendarReference.setConfigurations(List.of(configuration));
        UUID calendarUUID = UUID.randomUUID();
        calendarReference.setToken(calendarUUID);
        Mockito
            .when(calendarReferenceRepository.findCalendarReferenceByToken(calendarUUID))
            .thenReturn(calendarReference);

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
        assertEquals(269, returnedCalendar.getComponentList().getAll().size());
        Assertions.assertThat(returnedCalendar.getComponentList().getAll()).isNotEmpty();
    }
}
