package ru.renue.mk4pustin.airportsSearch.providers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CSVResourceProvider implements ResourceProvider {
    @Override
    public InputStream getResource() {
        final var props = new Properties();
        try (var stream = getClass().getResourceAsStream("/app.properties")) {
            if (stream == null)
                throw new RuntimeException("Resource access error. Unable to find \"app.properties\"");

            props.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file \"app.properties\"", e);
        }

        InputStream resourceStream;
        try {
            var path = props.getProperty("airports.csv.path");
            resourceStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return resourceStream;
    }
}
