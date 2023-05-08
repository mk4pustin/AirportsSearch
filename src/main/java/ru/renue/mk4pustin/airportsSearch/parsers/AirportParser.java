package ru.renue.mk4pustin.airportsSearch.parsers;

import ru.renue.mk4pustin.airportsSearch.exceptions.FilterParserException;
import ru.renue.mk4pustin.airportsSearch.filter.Filter;
import ru.renue.mk4pustin.airportsSearch.filter.FilterApplier;
import ru.renue.mk4pustin.airportsSearch.providers.ResourceProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AirportParser {
    private ResourceProvider provider;

    public SortedMap<String, Integer> parseByNames() {
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

    public Map<String, String> parseByLineNumbersAndFilter(List<Integer> lineNumbers, List<Filter> filters) throws FilterParserException {
        final var airports = new HashMap<String, String>();

        try (var reader = new BufferedReader(new InputStreamReader(provider.getResource()))) {
            int lineNumber = 1;

            for (var value : lineNumbers) {
                while (lineNumber < value) {
                    reader.readLine();
                    lineNumber++;
                }
                var data = reader.readLine();
                lineNumber++;
                var cols = data.split(",");
                if (filters == null || FilterApplier.apply(cols, filters)) {
                    airports.put(cols[1], data);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return airports;
    }

    public void setProvider(ResourceProvider provider) {
        this.provider = provider;
    }
}