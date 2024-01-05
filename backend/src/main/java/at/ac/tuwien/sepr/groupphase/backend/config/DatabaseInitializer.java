package at.ac.tuwien.sepr.groupphase.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            ResultSet resultSet = conn.getMetaData().getCatalogs();

            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);

                if ("tiss-postgres".equals(databaseName)) {

                    return; // Database exists
                }
            }

            // If here, database does not exist, create it
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate("CREATE DATABASE \"tiss-postgres\"");
            }
        }
    }
}
