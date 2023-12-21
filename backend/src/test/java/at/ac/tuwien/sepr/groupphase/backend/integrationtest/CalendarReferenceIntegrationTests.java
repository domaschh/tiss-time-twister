package at.ac.tuwien.sepr.groupphase.backend.integrationtest;


import at.ac.tuwien.sepr.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.DetailedMessageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.MessageMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Calendar;

import static at.ac.tuwien.sepr.groupphase.backend.basetest.TestData.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CalendarReferenceIntegrationTests {
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


    @BeforeEach
    void beforeEach() {
        calendarReferenceRepository.deleteAll();
    }

    @Test
    void deletingNotExistingThrows() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(delete(CALENDAR_REFERENCE_URL + "/{id}", 1L)
                                                       .header(securityProperties.getAuthHeader(),
                                                               jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                                          .andDo(print())
                                          .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()));
    }

    @Test
    @Transactional
    void deleteExistingRequest() throws Exception {
        CalendarReference crf = new CalendarReference();
        crf.setLink("https://url.com");
        crf.setName("SomeName");
        calendarReferenceRepository.save(crf);

        MvcResult mvcResult1 = this.mockMvc.perform(get(CALENDAR_REFERENCE_URL + "/{id}", 1L)
                                                       .header(securityProperties.getAuthHeader(),
                                                               jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                                          .andDo(print())
                                          .andReturn();
        assertEquals(mvcResult1.getResponse().getStatus(), 200);
        MvcResult mvcResult2 = this.mockMvc.perform(delete(CALENDAR_REFERENCE_URL + "/{id}", 1L)
                                                        .header(securityProperties.getAuthHeader(),
                                                                jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                                           .andDo(print())
                                           .andReturn();
        assertEquals(mvcResult2.getResponse().getStatus(), 200);
        MvcResult mvcResult3 = this.mockMvc.perform(get(CALENDAR_REFERENCE_URL + "/{id}", 1L)
                                                        .header(securityProperties.getAuthHeader(),
                                                                jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                                           .andDo(print())
                                           .andReturn();
        assertEquals(mvcResult3.getResponse().getStatus(), 404);
//        CalendarReferenceDto calrefReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
//                                                                         CalendarReferenceDto.class);
//        System.out.println(calrefReturn);
    }
}
