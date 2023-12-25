package at.ac.tuwien.sepr.groupphase.backend.integrationtest;


import at.ac.tuwien.sepr.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.*;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.StringReader;
import java.util.List;
import java.util.UUID;

import static at.ac.tuwien.sepr.groupphase.backend.basetest.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PipelineEndpointTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalendarReferenceRepository calendarReferenceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;


    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        calendarReferenceRepository.deleteAll();
    }

    @Test
    @Order(1)
    void reexportCalWithoutFprog() throws Exception {
        CalendarReferenceDto calendarReferenceDto = new CalendarReferenceDto();
        String customMockUrl = "http://localhost:" + port + "/test-cal";
        calendarReferenceDto.setLink(customMockUrl);
        calendarReferenceDto.setName("TISS Calendar");
        CalendarReference mockedCalReference = new CalendarReference();
        mockedCalReference.setId(1L);
        var customUUID = UUID.randomUUID();
        mockedCalReference.setToken(customUUID);
        mockedCalReference.setLink(customMockUrl);
        Configuration config = new Configuration();
        config.setId(1L);
        var rule = new Rule();
        Match match = new Match();
        match.setSummary("Funktionale Programmierung");
        Effect effect = new Effect(EffectType.DELETE);
        rule.setMatch(match);
        rule.setEffect(effect);
        config.setRules(List.of(rule));
        mockedCalReference.setConfigurations(List.of(config));
        when(calendarReferenceRepository.findCalendarReferenceByToken(any()))
            .thenReturn(mockedCalReference);

        MvcResult mvcResult2 = mockMvc.perform(get(CALENDAR_REFERENCE_URL + "/export/" + customUUID) // Replace with your actual endpoint URL
                                                                                                                              .contentType("application/json")
                                                                                                                              .header(securityProperties.getAuthHeader(),
                                                                                                                                      jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                                      .andExpect(status().isOk()).andReturn();
        var reexportedCal = mvcResult2.getResponse().getContentAsString();
        Calendar calendar = new CalendarBuilder().build(new StringReader(reexportedCal));
        assertNotNull(reexportedCal);
        assertNotNull(calendar);
        assertEquals(269, calendar.getComponentList().getAll().size());
    }
}
