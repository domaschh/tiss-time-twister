package at.ac.tuwien.sepr.groupphase.backend.config;

import at.ac.tuwien.sepr.groupphase.backend.TissRoom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class TissRoomConfig {
    private static final String filePath = "rooms.txt"; // replace with your file path

    @Bean
    public Map<String, TissRoom> roomConfig() {
        Map<String, TissRoom> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 8) {
                    String name = parts[0];
                    String capacity = parts[1];
                    String address = parts[6];
                    String tissLink = parts[8];
                    map.put(name, new TissRoom(address, capacity, tissLink));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
