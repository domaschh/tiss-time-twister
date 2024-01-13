package at.ac.tuwien.sepr.groupphase.backend.integrationtest;


import at.ac.tuwien.sepr.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.MessageMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.StringReader;

import static at.ac.tuwien.sepr.groupphase.backend.basetest.TestData.ADMIN_ROLES;
import static at.ac.tuwien.sepr.groupphase.backend.basetest.TestData.ADMIN_USER;
import static at.ac.tuwien.sepr.groupphase.backend.basetest.TestData.ADMIN_USER_EMAIL;
import static at.ac.tuwien.sepr.groupphase.backend.basetest.TestData.CALENDAR_REFERENCE_URL;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
class CalendarReferenceIntegrationTests {

    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CalendarReferenceRepository calendarReferenceRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private CalendarReferenceService calendarReferenceService;
    @LocalServerPort
    private int port;

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

    @BeforeEach
    void beforeEach() {
        calendarReferenceRepository.deleteAll();
    }

    @Test
    void deletingNotExistingThrows() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(delete(CALENDAR_REFERENCE_URL + "/{id}", 1L)
                                                       .header(securityProperties.getAuthHeader(),
                                                               jwtTokenizer.getAuthToken(ADMIN_USER_EMAIL, ADMIN_ROLES)))
                                          .andDo(print())
                                          .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus()));
    }

    @Test
    void deleteExistingRequest() throws Exception {
        CalendarReference crf = new CalendarReference();
        crf.setLink("https://url.com");
        crf.setName("SomeName");
        crf.setUser(ADMIN_USER);

        Long id = calendarReferenceService.add(crf, ADMIN_USER_EMAIL).getId();
        MockHttpServletRequestBuilder getRequest = get(CALENDAR_REFERENCE_URL + "/{id}", id)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER_EMAIL, ADMIN_ROLES));
        MockHttpServletRequestBuilder deleteRequest = delete(CALENDAR_REFERENCE_URL + "/{id}", id)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER_EMAIL, ADMIN_ROLES));

        this.mockMvc.perform(getRequest).andDo(print()).andExpect(status().isOk()).andReturn();
        this.mockMvc.perform(deleteRequest).andDo(print()).andExpect(status().isOk()).andReturn();
        this.mockMvc.perform(getRequest).andDo(print()).andExpect(status().isNotFound()).andReturn();
        this.mockMvc.perform(deleteRequest).andDo(print()).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void reexportCalendarFullSizeWorksAndReturnsIcalAsString() throws Exception {
        CalendarReferenceDto calendarReferenceDto = new CalendarReferenceDto();
        String customMockUrl = "http://localhost:" + port + "/test-cal";
        calendarReferenceDto.setLink(customMockUrl);
        calendarReferenceDto.setName("TISS Calendar");
        MvcResult mvcResult = mockMvc.perform(put(CALENDAR_REFERENCE_URL) // Replace with your actual endpoint URL
                                                                          .contentType("application/json")
                                                                          .content(objectMapper.writeValueAsString(calendarReferenceDto))
                                                                          .header(securityProperties.getAuthHeader(),
                                                                                  jwtTokenizer.getAuthToken(ADMIN_USER_EMAIL, ADMIN_ROLES)))
                                     .andExpect(status().isOk()).andReturn();
        CalendarReferenceDto createdCalendarReference = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                               CalendarReferenceDto.class);
        assertNotNull(createdCalendarReference);

        MvcResult mvcResult2 = mockMvc.perform(get(CALENDAR_REFERENCE_URL + "/export/" + createdCalendarReference.getToken()) // Replace with your actual endpoint URL
                                                                                                                              .contentType(
                                                                                                                                  "application/json")
                                                                                                                              .header(
                                                                                                                                  securityProperties.getAuthHeader(),
                                                                                                                                  jwtTokenizer.getAuthToken(
                                                                                                                                      ADMIN_USER_EMAIL,
                                                                                                                                      ADMIN_ROLES)))
                                      .andExpect(status().isOk()).andReturn();
        var reexportedCal = mvcResult2.getResponse().getContentAsString();
        Calendar calendar = new CalendarBuilder().build(new StringReader(reexportedCal));
        assertNotNull(reexportedCal);
        assertNotNull(calendar);
        assertEquals(310, calendar.getComponentList().getAll().size());
    }

    @Test
    void reexportCalWithoutFprog() throws Exception {
        CalendarReferenceDto calendarReferenceDto = new CalendarReferenceDto();
        String customMockUrl = "http://localhost:" + port + "/test-cal";
        calendarReferenceDto.setLink(customMockUrl);
        calendarReferenceDto.setName("TISS Calendar");

        MvcResult mvcResult = mockMvc.perform(put(CALENDAR_REFERENCE_URL) // Replace with your actual endpoint URL
                                                                          .contentType("application/json")
                                                                          .content(objectMapper.writeValueAsString(calendarReferenceDto))
                                                                          .header(securityProperties.getAuthHeader(),
                                                                                  jwtTokenizer.getAuthToken(ADMIN_USER_EMAIL, ADMIN_ROLES)))
                                     .andExpect(status().isOk()).andReturn();
        CalendarReferenceDto createdCalendarReference = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                               CalendarReferenceDto.class);
        assertNotNull(createdCalendarReference);

        MvcResult mvcResult2 = mockMvc.perform(get(CALENDAR_REFERENCE_URL + "/export/" + createdCalendarReference.getToken()) // Replace with your actual endpoint URL
                                                                                                                              .contentType(
                                                                                                                                  "application/json")
                                                                                                                              .header(
                                                                                                                                  securityProperties.getAuthHeader(),
                                                                                                                                  jwtTokenizer.getAuthToken(
                                                                                                                                      ADMIN_USER_EMAIL,
                                                                                                                                      ADMIN_ROLES)))
                                      .andExpect(status().isOk()).andReturn();
        var reexportedCal = mvcResult2.getResponse().getContentAsString();
        Calendar calendar = new CalendarBuilder().build(new StringReader(reexportedCal));
        assertNotNull(reexportedCal);
        assertNotNull(calendar);
        assertEquals(310, calendar.getComponentList().getAll().size());
    }
}
