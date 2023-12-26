package at.ac.tuwien.sepr.groupphase.backend;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class MockCalendarEndpoint {

    @GetMapping(value = "/test-cal", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getIcsContent() throws IOException {
        try (InputStream is = new ClassPathResource("domasch_fixed.ics").getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
