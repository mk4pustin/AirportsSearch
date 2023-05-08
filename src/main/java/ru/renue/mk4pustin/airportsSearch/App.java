package ru.renue.mk4pustin.airportsSearch;

import ru.renue.mk4pustin.airportsSearch.constants.Messages;
import ru.renue.mk4pustin.airportsSearch.exceptions.FilterParserException;
import ru.renue.mk4pustin.airportsSearch.filter.Filter;
import ru.renue.mk4pustin.airportsSearch.parsers.AirportParser;
import ru.renue.mk4pustin.airportsSearch.parsers.FilterParser;
import ru.renue.mk4pustin.airportsSearch.providers.CSVResourceProvider;
import ru.renue.mk4pustin.airportsSearch.readers.ConsoleUserMessageReader;
import ru.renue.mk4pustin.airportsSearch.render.ConsoleMessageRender;
import ru.renue.mk4pustin.airportsSearch.searcher.AirportSearcher;
import ru.renue.mk4pustin.airportsSearch.searcher.AirportSearcherProxy;

import java.util.List;
import java.util.TreeMap;

public class App {
    public static void main(String[] args) {
        final var parser = new AirportParser();
        parser.setProvider(new CSVResourceProvider());

        final var airports = (TreeMap<String, Integer>) parser.parseByNames();

        final var render = new ConsoleMessageRender();
        final var reader = new ConsoleUserMessageReader();

        var userCommand = "";
        var airportNameBeginning = "";
        while (!checkOutput(userCommand)) {
            List<Filter> filters = null;

            try {
                render.render(Messages.INTRODUCE_FILTER_MESSAGE);
                userCommand = reader.read();
                if (!userCommand.isEmpty()) {
                    if (checkOutput(userCommand)) System.exit(0);
                    else filters = FilterParser.parse(userCommand);
                }

                render.render(Messages.INTRODUCE_AIRPORT_NAME_MESSAGE);
                userCommand = reader.read();
                if (checkOutput(userCommand)) System.exit(0);
                else airportNameBeginning = userCommand;

                var searcher = new AirportSearcher(airports, filters, airportNameBeginning, parser);
                var proxy = new AirportSearcherProxy(searcher);
                var res = proxy.search();

                for (var node : res.entrySet()) {
                    render.render(String.format(node.getKey() + "[%s]", node.getValue()));
                }
                render.render(Messages.RESULTING_MESSAGE + res.size());
                render.render(Messages.RESULTING_TIME + proxy.getTime());
            } catch (FilterParserException e) {
                render.render(Messages.REPEAT_MESSAGE);
            }
        }
    }

    private static boolean checkOutput(String msg) {
        return msg.equals(Messages.EXIT_COMMAND);
    }
}
