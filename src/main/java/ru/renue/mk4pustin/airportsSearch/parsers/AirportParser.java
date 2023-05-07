package ru.renue.mk4pustin.airportsSearch.parsers;

import ru.renue.mk4pustin.airportsSearch.providers.ResourceProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class AirportParser {

    public static Map<String, Integer> parse(ResourceProvider provider) {
        final var airports = new TreeMap<String, Integer>();

        try (var reader = new BufferedReader(new InputStreamReader(provider.getResource()))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                var data = line.split(",");
                airports.put(data[1], lineNumber++);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return airports;
    }
}
