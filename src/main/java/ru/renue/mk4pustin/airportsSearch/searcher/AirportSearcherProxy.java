package ru.renue.mk4pustin.airportsSearch.searcher;

import java.util.Map;

public class AirportSearcherProxy implements Searcher {
    public static final long MILLISECONDS_TO_NANOSECONDS = 1000000;
    private final AirportSearcher airportSearcher;

    private String time;

    public AirportSearcherProxy(AirportSearcher airportSearcher) {
        this.airportSearcher = airportSearcher;
    }

    @Override
    public Map<String, String> search() {
        var startTime = System.nanoTime();
        var result = airportSearcher.search();
        long endTime = System.nanoTime();

        time = (endTime - startTime) / MILLISECONDS_TO_NANOSECONDS + " mc";

        return result;
    }

    public String getTime() {
        return time;
    }
}
