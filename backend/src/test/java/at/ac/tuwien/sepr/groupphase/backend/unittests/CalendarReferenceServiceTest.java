package at.ac.tuwien.sepr.groupphase.backend.unittests;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.ConfigurationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import at.ac.tuwien.sepr.groupphase.backend.service.impl.CalendarReferenceServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CalendarReferenceServiceTest {

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
    private CalendarReferenceService service;

    @Autowired
    CalendarReferenceRepository calendarReferenceRepository;

    @Mock
    private ConfigurationRepository configurationRepository;

    @BeforeEach
    void beforeEach() {
        calendarReferenceRepository.deleteAll();
    }


    @Test
    void generateTokenIsUnique() {
        assertThat(service.generateToken()).isNotEqualTo(service.generateToken());
    }

    @Test
    void addUnpublishedConfigToCalendarReferenceThrows() {
        Configuration configuration = new Configuration();
        configuration.setPublished(false);
        CalendarReference calendarReference = new CalendarReference();
        calendarReference.setConfigurations(new ArrayList<>());
        when(configurationRepository.findById(any())).thenReturn(Optional.of(configuration));

        CalendarReferenceRepository calendarReferenceRepository = mock(CalendarReferenceRepository.class);
        when(calendarReferenceRepository.getReferenceById(any())).thenReturn(calendarReference);
        ConfigurationRepository configurationRepository = mock(ConfigurationRepository.class);
        when(configurationRepository.getReferenceById(any())).thenReturn(configuration);

        CalendarReferenceService serviceMock = new CalendarReferenceServiceImpl(calendarReferenceRepository, configurationRepository, null);


        assertThrows(NotFoundException.class, () -> serviceMock.addConfig(1L, 1L));

    }

    @Test
    void addPublishedConfigToCalendarReference() {
        Configuration configuration = new Configuration();
        configuration.setPublished(true);
        CalendarReference calendarReference = new CalendarReference();
        calendarReference.setConfigurations(new ArrayList<>());
        configuration.setId(1L);

        CalendarReferenceRepository calendarReferenceRepository = mock(CalendarReferenceRepository.class);
        when(calendarReferenceRepository.getReferenceById(any())).thenReturn(calendarReference);
        ConfigurationRepository configurationRepository = mock(ConfigurationRepository.class);
        when(configurationRepository.findById(any())).thenReturn(Optional.of(configuration));

        CalendarReferenceService serviceMock = new CalendarReferenceServiceImpl(calendarReferenceRepository, configurationRepository, null);

        assertDoesNotThrow(() -> serviceMock.addConfig(1L, 1L));
    }

    @Test
    void importCalendarFileWorks() throws IOException {
        InputStream is = new ClassPathResource("domasch_fixed.ics").getInputStream();
        MultipartFile multipartFile = new MockMultipartFile("name", is);
        CalendarReference result = service.addFile("test file name", multipartFile, "test username", null);

        assertNotNull(result);
        assertAll(
            () -> assertThat(result.getIcalData()).isNotNull(),
            () -> assertThat(result.getIcalData()).isEqualTo(multipartFile.getBytes()),
            () -> assertThat(result.getLink()).isNull(),
            () -> assertThat(result.getToken()).isNotNull());
    }

    @Test
    void importCalendarFileCanBeFound() throws IOException {
        InputStream is = new ClassPathResource("domasch_fixed.ics").getInputStream();
        MultipartFile multipartFile = new MockMultipartFile("name", is);
        CalendarReference result = service.addFile("test file name", multipartFile, "test username", null);

        CalendarReference resultFromId = service.getFromId(result.getId());
        assertTrue(service.getFromToken(result.getToken()).isPresent());
        var resultFromToken = service.getFromToken(result.getToken()).get();

        assertNotNull(resultFromId);
        assertAll(
            () -> assertThat(resultFromId.getIcalData()).isNotNull(),
            () -> assertThat(resultFromId.getIcalData()).isEqualTo(multipartFile.getBytes()),
            () -> assertThat(resultFromId.getLink()).isNull(),
            () -> assertEquals(resultFromId, resultFromToken));
    }

    //    @Test
    //    void importCalendarWithIdThatDoesNotExistThrows() {
    //        service.a
    //    }
}