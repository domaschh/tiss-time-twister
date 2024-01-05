package at.ac.tuwien.sepr.groupphase.backend.integrationtest;


import at.ac.tuwien.sepr.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ConfigurationDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.ConfigurationMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.entity.Effect;
import at.ac.tuwien.sepr.groupphase.backend.entity.EffectType;
import at.ac.tuwien.sepr.groupphase.backend.entity.Match;
import at.ac.tuwien.sepr.groupphase.backend.entity.MatchType;
import at.ac.tuwien.sepr.groupphase.backend.entity.Rule;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static at.ac.tuwien.sepr.groupphase.backend.basetest.TestData.ADMIN_ROLES;
import static at.ac.tuwien.sepr.groupphase.backend.basetest.TestData.ADMIN_USER;
import static at.ac.tuwien.sepr.groupphase.backend.basetest.TestData.CALENDAR_REFERENCE_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PipelineEndpointTests {
    @Autowired
    ConfigurationMapper configurationMapper;
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
        match.setSummary(MatchType.CONTAINS, "Funktionale Programmierung");
        Effect effect = new Effect(EffectType.DELETE);
        rule.setMatch(match);
        rule.setEffect(effect);
        config.setRules(List.of(rule));
        mockedCalReference.setConfigurations(List.of(config));
        mockedCalReference.setEnabledDefaultConfigurations(-1L); //all default flags

        when(calendarReferenceRepository.findCalendarReferenceByToken(any()))
            .thenReturn(Optional.of(mockedCalReference));

        String path = CALENDAR_REFERENCE_URL + "/export/" + customUUID;
        MvcResult mvcResult2 = mockMvc.perform(get(path) // Replace with your actual endpoint URL
                                                         .contentType(MediaType.APPLICATION_JSON)
                                                         .header(securityProperties.getAuthHeader(),
                                                                 jwtTokenizer.getAuthToken(
                                                                     ADMIN_USER,
                                                                     ADMIN_ROLES)))
                                      .andExpect(status().isOk()).andReturn();
        var reexportedCal = mvcResult2.getResponse().getContentAsString();
        Calendar calendar = new CalendarBuilder().build(new StringReader(reexportedCal));
        assertNotNull(reexportedCal);
        assertNotNull(calendar);
        assertEquals(269, calendar.getComponentList().getAll().size());
    }

    @Test
    void previewWithoutFprog() throws Exception {
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
        match.setSummary(MatchType.CONTAINS, "Funktionale Programmierung");
        Effect effect = new Effect(EffectType.DELETE);
        rule.setMatch(match);
        rule.setEffect(effect);
        config.setRules(List.of(rule));
        mockedCalReference.setConfigurations(List.of(config));
        mockedCalReference.setEnabledDefaultConfigurations(-1L); //all default flags

        when(calendarReferenceRepository.findById(any()))
            .thenReturn(Optional.of(mockedCalReference));


        String configDto = objectMapper.writeValueAsString(List.of(configurationMapper.toDto(config)));
        String path = CALENDAR_REFERENCE_URL + "/preview/" + 1L; // Replace with your actual endpoint URL
        MvcResult mvcResult2 = mockMvc.perform(post(path).contentType("application/json")
                                                         .content(configDto)
                                                         .header(securityProperties.getAuthHeader(),
                                                                 jwtTokenizer.getAuthToken(ADMIN_USER,
                                                                                           ADMIN_ROLES)))
                                      .andExpect(status().isOk()).andReturn();
        var reexportedCal = mvcResult2.getResponse().getContentAsString();
        Calendar calendar = new CalendarBuilder().build(new StringReader(reexportedCal));
        assertNotNull(reexportedCal);
        assertNotNull(calendar);
        assertEquals(269, calendar.getComponentList().getAll().size());
    }

    @Test
    void reexportWhenDoesNotExist() throws Exception {
        when(calendarReferenceRepository.findById(any()))
            .thenReturn(Optional.empty());


        String path = CALENDAR_REFERENCE_URL + "/export/" + UUID.randomUUID(); // Replace with your actual endpoint URL
        mockMvc.perform(get(path).contentType(MediaType.APPLICATION_JSON)
                                 .header(securityProperties.getAuthHeader(),
                                         jwtTokenizer.getAuthToken(
                                             ADMIN_USER,
                                             ADMIN_ROLES)))
               .andExpect(status().isNotFound()).andReturn();

    }

    @Test
    void previewWhenDoesNotExist() throws Exception {
        when(calendarReferenceRepository.findById(any()))
            .thenReturn(Optional.empty());


        String configDto = objectMapper.writeValueAsString(List.of(new ConfigurationDto()));
        String path = CALENDAR_REFERENCE_URL + "/preview/" + 1L; // Replace with your actual endpoint URL
        mockMvc.perform(post(path).contentType("application/json")
                                  .content(configDto)
                                  .header(securityProperties.getAuthHeader(),
                                          jwtTokenizer.getAuthToken(ADMIN_USER,
                                                                    ADMIN_ROLES)))
               .andExpect(status().isNotFound()).andReturn();

    }

}
