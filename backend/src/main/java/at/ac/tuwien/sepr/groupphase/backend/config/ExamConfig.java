package at.ac.tuwien.sepr.groupphase.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExamConfig {
    private static final String filePath = "lvas.txt"; // Replace with your file path

    @Bean
    public Map<String, String> config() {
        Map<String, String> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Use comma as the delimiter
                if (parts.length >= 4) {
                    String lvaNumber = parts[0];  // LVA number is the first part
                    String url = parts[3];        // URL is the fourth part
                    map.put(lvaNumber, url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
