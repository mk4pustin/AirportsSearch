package ru.renue.mk4pustin.airportsSearch.parsers;

import ru.renue.mk4pustin.airportsSearch.constants.Messages;
import ru.renue.mk4pustin.airportsSearch.exceptions.FilterParserException;
import ru.renue.mk4pustin.airportsSearch.filter.Expression;
import ru.renue.mk4pustin.airportsSearch.filter.Filter;
import ru.renue.mk4pustin.airportsSearch.filter.Operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public final class FilterParser {
    public static final String EXPRESSION_PATTERN_STRING = "column\\[(\\d+)]\\s*(=|<>|>|<)\\s*(.*)";
    public static final String EXP_REGEX = "^exp\\d+$";
    public static final int MAX_COLUMN_NUMBER = 14;

    public static List<Filter> parse(String parsingLine) throws FilterParserException {
        if (parsingLine == null || parsingLine.isEmpty()) return null;

        var innerFilters = new HashMap<String, String>();
        parsingLine = parseExpressionsInBrackets(parsingLine, innerFilters);

        var strFilters = parsingLine.split("\\|\\|");
        var filters = new ArrayList<Filter>();
        var andFilters = new ArrayList<Filter>();

        for (var strFilter : strFilters) {
            var strExpressions = strFilter.split("&");
            var expressions = new Expression[strExpressions.length];

            for (var eIndex = 0; eIndex < expressions.length; eIndex++) {
                var curExpression = strExpressions[eIndex].trim();
                if (curExpression.matches(EXP_REGEX)) {
                    andFilters.add(new Filter(null, parse(innerFilters.get(curExpression))));
                } else {
                    expressions[eIndex] = parseExpression(curExpression);
                }
            }
            filters.add(new Filter(expressions, andFilters));
            andFilters = new ArrayList<>();
        }
        return filters;
    }

    private static String parseExpressionsInBrackets(String line, HashMap<String, String> innerFilters) throws FilterParserException {
        var openBracketsCount = 0;

        var startFilterIndex = 0;
        for (var index = 0; index < line.length(); index++) {
            var curChar = line.charAt(index);

            if (curChar == '(') {
                if (++openBracketsCount == 1) {
                    startFilterIndex = index;
                }
            } else if (curChar == ')') {
                if (openBracketsCount == 0)
                    throw new FilterParserException(Messages.BRACKETS_FORMAT_ERROR_MESSAGE);
                else {
                    if (--openBracketsCount == 0) {
                        var replaceStr = "exp" + (innerFilters.size() + 1);
                        innerFilters.put(replaceStr, line.substring(startFilterIndex + 1, index));
                        line = line.substring(0, startFilterIndex) + replaceStr + line.substring(index + 1);
                        index = startFilterIndex + replaceStr.length();

                    }
                }
            }
        }

        return line;
    }

    private static Expression parseExpression(String expression) throws FilterParserException {
        final var pattern = Pattern.compile(EXPRESSION_PATTERN_STRING);
        final var matcher = pattern.matcher(expression);
        if (matcher.matches()) {
            try {
                var columnNumber = Integer.parseInt(matcher.group(1));
                if (columnNumber < 1 || columnNumber > MAX_COLUMN_NUMBER)
                    throw new FilterParserException(Messages.COLUMN_NUMBER_ERROR_MESSAGE + MAX_COLUMN_NUMBER);

                var operation = Operation.fromString(matcher.group(2));
                var value = matcher.group(3).replaceAll("’", "");

                if (operation == null || value.isEmpty()) {
                    throw new FilterParserException(Messages.FILTER_FORMAT_ERROR_MESSAGE);
                }

                return new Expression(columnNumber, operation, value);
            } catch (NumberFormatException e) {
                throw new FilterParserException(e.getMessage());
            }
        } else {
            throw new FilterParserException(Messages.FILTER_FORMAT_ERROR_MESSAGE);
        }
    }

}
