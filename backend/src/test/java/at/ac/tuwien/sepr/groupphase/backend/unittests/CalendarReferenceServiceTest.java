package at.ac.tuwien.sepr.groupphase.backend.unittests;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.ConfigurationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import at.ac.tuwien.sepr.groupphase.backend.service.impl.CalendarReferenceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CalendarReferenceServiceTest {

    @Autowired
    private CalendarReferenceService service;

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

        CalendarReferenceRepository calendarReferenceRepository = mock(CalendarReferenceRepository.class);
        when(calendarReferenceRepository.getReferenceById(any())).thenReturn(calendarReference);
        ConfigurationRepository configurationRepository = mock(ConfigurationRepository.class);
        when(configurationRepository.getReferenceById(any())).thenReturn(configuration);

        CalendarReferenceService serviceMock = new CalendarReferenceServiceImpl(calendarReferenceRepository, configurationRepository, null);


        assertThrows(AccessDeniedException.class, () -> serviceMock.addConfig(1L, 1L));

    }

    @Test
    void addPublishedConfigToCalendarReference() {
        Configuration configuration = new Configuration();
        configuration.setPublished(true);
        CalendarReference calendarReference = new CalendarReference();
        calendarReference.setConfigurations(new ArrayList<>());

        CalendarReferenceRepository calendarReferenceRepository = mock(CalendarReferenceRepository.class);
        when(calendarReferenceRepository.getReferenceById(any())).thenReturn(calendarReference);
        ConfigurationRepository configurationRepository = mock(ConfigurationRepository.class);
        when(configurationRepository.getReferenceById(any())).thenReturn(configuration);

        CalendarReferenceService serviceMock = new CalendarReferenceServiceImpl(calendarReferenceRepository, configurationRepository, null);

        assertDoesNotThrow(() -> serviceMock.addConfig(1L, 1L));
    }
}