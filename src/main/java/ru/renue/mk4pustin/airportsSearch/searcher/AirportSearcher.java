package ru.renue.mk4pustin.airportsSearch.searcher;

import ru.renue.mk4pustin.airportsSearch.exceptions.FilterParserException;
import ru.renue.mk4pustin.airportsSearch.filter.Filter;
import ru.renue.mk4pustin.airportsSearch.parsers.AirportParser;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class AirportSearcher implements Searcher {
    private SortedMap<String, Integer> airports;
    private List<Filter> filters;
    private String nameBeginning;
    private AirportParser parser;

    public AirportSearcher(SortedMap<String, Integer> airports, List<Filter> filters, String nameBeginning, AirportParser parser) {
        this.airports = airports;
        this.filters = filters;
        this.nameBeginning = nameBeginning;
        this.parser = parser;
    }

    public Map<String, String> search() throws FilterParserException {
        nameBeginning = "\"" + nameBeginning;
        final var nameBeginningAirports = airports.subMap(nameBeginning, nameBeginning + Character.MAX_VALUE);

        final var lineNumbers = nameBeginningAirports.values().stream().sorted().collect(Collectors.toList());

        return parser.parseByLineNumbersAndFilter(lineNumbers, filters);
    }

    public void setAirports(SortedMap<String, Integer> airports) {
        this.airports = airports;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public void setNameBeginning(String nameBeginning) {
        this.nameBeginning = nameBeginning;
    }

    public void setParser(AirportParser parser) {
        this.parser = parser;
    }
}
