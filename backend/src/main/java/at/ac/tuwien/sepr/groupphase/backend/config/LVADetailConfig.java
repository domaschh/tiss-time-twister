package at.ac.tuwien.sepr.groupphase.backend.config;

import at.ac.tuwien.sepr.groupphase.backend.LVADetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class LVADetailConfig {
    private static final String BACHELOR_SHORTHANDS = "bachelor_shorthands.txt"; // replace with your file path
    private static final String MASTER_SHORTHANDS = "master_softwareengineering_shorthands.txt"; // replace with your file path
    private static final String LVA_PATH = "lvas.txt"; // replace with your file path

    @Bean
    public Map<String, LVADetail> shorthandMap() {
        Map<String, LVADetail> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BACHELOR_SHORTHANDS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String shorthand = parts[1];
                    String germanName = parts[0];
                    String englishName = parts[0];
                    var detail = new LVADetail(shorthand, "https://tiss.tuwien.ac.at/", "123.456");

                    map.put(germanName.trim(), detail);
                    map.put(englishName.trim(), detail);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(MASTER_SHORTHANDS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String shorthand = parts[1];
                    String germanName = parts[0];
                    String englishName = parts[0];
                    var detail = new LVADetail(shorthand, "https://tiss.tuwien.ac.at/", "123.456");

                    map.put(germanName.trim(), detail);
                    map.put(englishName.trim(), detail);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(LVA_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Use comma as the delimiter
                if (parts.length >= 4) {
                    String lvaType = parts[1];
                    String lvaNumber = parts[0];  // LVA number is the first part
                    String lvaName = parts[2];  // LVA number is the first part
                    String url = parts[3];        // URL is the fourth part

                    if (map.containsKey(lvaType + " " + lvaName)) {
                        LVADetail existingDetail = map.get(lvaType + " " + lvaName);

                        LVADetail updatedDetail = new LVADetail(existingDetail.shorthand(), url, lvaNumber);

                        map.replace(lvaType + " " + lvaName, updatedDetail);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
