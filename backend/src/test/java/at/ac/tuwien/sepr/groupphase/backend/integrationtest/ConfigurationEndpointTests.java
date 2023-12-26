package at.ac.tuwien.sepr.groupphase.backend.integrationtest;


import at.ac.tuwien.sepr.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ConfigurationDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EffectDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.MatchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RuleDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.ConfigurationMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.EffectType;
import at.ac.tuwien.sepr.groupphase.backend.entity.MatchType;
import at.ac.tuwien.sepr.groupphase.backend.repository.ConfigurationRepository;
import at.ac.tuwien.sepr.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import java.util.Arrays;
import java.util.List;

import static at.ac.tuwien.sepr.groupphase.backend.basetest.TestData.ADMIN_ROLES;
import static at.ac.tuwien.sepr.groupphase.backend.basetest.TestData.ADMIN_USER;
import static at.ac.tuwien.sepr.groupphase.backend.basetest.TestData.CONFIG_BASE_URI;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ConfigurationEndpointTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConfigurationMapper configurationMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    @BeforeEach
    void beforeEach() {
        configurationRepository.deleteAll();
    }

    private ConfigurationDto generateConfigurationWithoutRules() {
        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setDescription("Insightful Description");
        configurationDto.setTitle("Informing Title");
        return configurationDto;
    }

    private ConfigurationDto configWithRules(List<RuleDto> rules) {
        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setDescription("Insightful Description");
        configurationDto.setTitle("Informing Title");
        configurationDto.setRules(rules);
        return configurationDto;
    }

    @Test
    void creatingConfigurationWorks() throws Exception {
        ConfigurationDto config = generateConfigurationWithoutRules();
        generateEmptyConfig(config);
    }

    @Test
    void creatingConfigurationWithRulesWorks() throws Exception {
        EffectDto effectDto = new EffectDto(null, "Blabla", "Blabla", "blabla", EffectType.DELETE);
        MatchDto matchDto = new MatchDto(null,
                                         MatchType.EQUALS,
                                         "dasd",
                                         MatchType.EQUALS,
                                         "dasd",
                                         MatchType.REGEX,
                                         "dasdas");
        RuleDto rule1 = new RuleDto(null, effectDto, matchDto);
        ConfigurationDto config = configWithRules(List.of(rule1));
        ConfigurationDto createdConfig = generateEmptyConfig(config);
        assertFalse(createdConfig.getRules().isEmpty());
        var firstCreatedRule = createdConfig.getRules().get(0);
        assertAll(() -> assertTrue(firstCreatedRule.getId() >= 0),
                  () -> assertTrue(firstCreatedRule.getMatch().id() >= 0),
                  () -> assertTrue(firstCreatedRule.getEffect().id() >= 0));
    }

    @Test
    @Transactional
    void gettingConfigurationByIdWorks() throws Exception {
        ConfigurationDto config = generateConfigurationWithoutRules();
        ConfigurationDto generated = generateEmptyConfig(config);
        MvcResult mvcResult = this.mockMvc.perform(get(CONFIG_BASE_URI + "/" + generated.getId())
                                                       .header(securityProperties.getAuthHeader(),
                                                               jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                                          .andDo(print())
                                          .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        ConfigurationDto returnedConfigDto = objectMapper.readValue(response.getContentAsString(),
                                                                    ConfigurationDto.class);
        assertNotNull(returnedConfigDto);
        assertEquals(generated.getId(), returnedConfigDto.getId());
    }

    @Test
    @Transactional
    @Disabled // user connection missing
    void gettingByUserWorks() throws Exception {
        ConfigurationDto config = generateConfigurationWithoutRules();
        ConfigurationDto generated = generateEmptyConfig(config);
        MvcResult mvcResult = this.mockMvc.perform(get(CONFIG_BASE_URI + "/all/" + 123)//TODO
                                                                                       .header(securityProperties.getAuthHeader(),
                                                                                               jwtTokenizer.getAuthToken(ADMIN_USER,
                                                                                                                         ADMIN_ROLES)))
                                          .andDo(print())
                                          .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        List<ConfigurationDto> returnedConfigDto = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
                                                                                        ConfigurationDto[].class));
        assertNotNull(returnedConfigDto);
        assertEquals(1, returnedConfigDto.size());
    }

    @Test
    @Transactional
    void gettingAllPublicWorks() throws Exception {
        ConfigurationDto config = generateConfigurationWithoutRules();
        config.setPublished(true);
        ConfigurationDto generated = generateEmptyConfig(config);
        MvcResult mvcResult = this.mockMvc.perform(get(CONFIG_BASE_URI + "/allPublic")
                                                       .header(securityProperties.getAuthHeader(),
                                                               jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                                          .andDo(print())
                                          .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        List<ConfigurationDto> returnedConfigDto = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
                                                                                        ConfigurationDto[].class));
        assertNotNull(returnedConfigDto);
        assertEquals(1, returnedConfigDto.size());
        assertEquals(generated, returnedConfigDto.get(0));
    }

    @Test
    @Transactional
    void changingVisibilityWorks() throws Exception {
        ConfigurationDto config = generateConfigurationWithoutRules();
        ConfigurationDto generated = generateEmptyConfig(config);
        assertFalse(generated.isPublished());
        MvcResult mvcResult = this.mockMvc.perform(put(CONFIG_BASE_URI + "/changeVisibility/" + generated.getId())
                                                       .header(securityProperties.getAuthHeader(),
                                                               jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                                          .andDo(print())
                                          .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        ConfigurationDto returnedConfigDto = objectMapper.readValue(response.getContentAsString(),
                                                                    ConfigurationDto.class);
        assertNotNull(returnedConfigDto);
        assertEquals(generated.getId(), returnedConfigDto.getId());
        assertTrue(returnedConfigDto.isPublished());
    }

    @Test
    @Transactional
    void deletingConfigurationWorks() throws Exception {
        ConfigurationDto config = generateConfigurationWithoutRules();
        ConfigurationDto generated = generateEmptyConfig(config);
        MvcResult mvcResult = this.mockMvc.perform(get(CONFIG_BASE_URI + "/" + generated.getId())
                                                       .header(securityProperties.getAuthHeader(),
                                                               jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                                          .andDo(print())
                                          .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        ConfigurationDto returnedConfigDto = objectMapper.readValue(response.getContentAsString(),
                                                                    ConfigurationDto.class);
        assertNotNull(returnedConfigDto);
        assertEquals(generated.getId(), returnedConfigDto.getId());

        MvcResult mvcResultdelete = this.mockMvc.perform(delete(CONFIG_BASE_URI + "/" + generated.getId())
                                                             .header(securityProperties.getAuthHeader(),
                                                                     jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                                                .andDo(print())
                                                .andReturn();
        MockHttpServletResponse responseDelete = mvcResultdelete.getResponse();
        assertEquals(HttpStatus.OK.value(), responseDelete.getStatus());

        MvcResult mvcResultGettingAfter = this.mockMvc.perform(get(CONFIG_BASE_URI + "/" + generated.getId())
                                                                   .header(securityProperties.getAuthHeader(),
                                                                           jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                                                      .andDo(print())
                                                      .andReturn();
        MockHttpServletResponse responseAfter = mvcResultGettingAfter.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), responseAfter.getStatus());
    }


    private ConfigurationDto generateEmptyConfig(ConfigurationDto input) throws Exception {
        String body = objectMapper.writeValueAsString(input);
        MvcResult mvcResult = this.mockMvc.perform(put(CONFIG_BASE_URI)
                                                       .contentType(MediaType.APPLICATION_JSON)
                                                       .content(body)
                                                       .header(securityProperties.getAuthHeader(),
                                                               jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                                          .andDo(print())
                                          .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        ConfigurationDto returnedConfigDto = objectMapper.readValue(response.getContentAsString(),
                                                                    ConfigurationDto.class);

        assertNotNull(returnedConfigDto.getId());
        assertTrue(returnedConfigDto.getId() >= 1);
        return returnedConfigDto;
    }

}
