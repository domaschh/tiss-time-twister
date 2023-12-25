package at.ac.tuwien.sepr.groupphase.backend;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TestUtility {
    public static String loadIcsFileAsString(String fileName) throws IOException {
        // Assuming the file is in src/test/resources
        String path = "src/test/resources/" + fileName;
        return Files.lines(Paths.get(path)).collect(Collectors.joining("\n"));
    }

    public static Calendar loadCalendarForTests(String filename) throws IOException, ParserException {
        String path = "src/test/resources/" + filename;
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        }

        return new CalendarBuilder().build(new StringReader(data.toString()));
    }
}