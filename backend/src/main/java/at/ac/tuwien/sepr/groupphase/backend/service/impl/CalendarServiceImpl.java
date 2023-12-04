package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.service.CalendarService;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class CalendarServiceImpl implements CalendarService {
    @Override
    public Calendar fetchCalendarByUrl(String url) throws ParserException, IOException {
        return new CalendarBuilder().build(new StringReader(fetchICalData(url)));
    }

    private String fetchICalData(String urlString) throws IOException {
        StringBuilder data = new StringBuilder();

        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        }

        return data.toString();
    }
}
