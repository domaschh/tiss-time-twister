package at.ac.tuwien.sepr.groupphase.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShorthandConfig {
    private static final String FILE_PATH = "shorthands.txt"; // replace with your file path

    @Bean
    public Map<String, String> shorthandMap() {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String shorthand = parts[0];
                    String germanName = parts[1];
                    String englishName = parts[2];
                    map.put(germanName.trim(), shorthand);
                    map.put(englishName.trim(), shorthand);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
