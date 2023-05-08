package ru.renue.mk4pustin.airportsSearch.filter;

import ru.renue.mk4pustin.airportsSearch.constants.Messages;
import ru.renue.mk4pustin.airportsSearch.exceptions.FilterParserException;

import java.util.List;

public final class FilterApplier {

    public static boolean apply(String[] airport, List<Filter> filters) throws FilterParserException {
        if (filters == null || filters.isEmpty()) return false;

        filters_loop:
        for (var filter : filters) {
            if (filter == null) break;

            if (filter.getExpressions() != null) {
                for (var ex : filter.getExpressions()) {
                    if (ex == null) break;

                    var val = airport[ex.getColumnNumber() - 1];
                    if (val.equals("\\N")) {
                        if (ex.getOperation() == Operation.EQUAL) continue filters_loop;
                        else continue;
                    }

                    try {
                        if (val.startsWith("\"")) {
                            if (applyExpression(airport[ex.getColumnNumber() - 1].replaceAll("\"", ""), ex.getValue(), ex.getOperation())) {
                                continue filters_loop;
                            }
                        } else {
                            if (applyExpression(Double.parseDouble(airport[ex.getColumnNumber() - 1]),
                                    Double.parseDouble(ex.getValue()), ex.getOperation())) {
                                continue filters_loop;
                            }
                        }
                    } catch (Exception e) {
                        throw new FilterParserException(Messages.DIFFERENT_TYPES_ERROR_MESSAGE);
                    }
                }
            }
            var andFilters = filter.getAndFilters();
            if (andFilters == null || andFilters.isEmpty() || andFilters.get(0) == null) return true;
            if (filter.getAndFilters() != null && apply(airport, filter.getAndFilters())) return true;
        }

        return false;
    }

    private static <E extends Comparable<E>> boolean applyExpression(E airportVal, E filterVal, Operation op) {
        switch (op) {
            case LESS: {
                return airportVal.compareTo(filterVal) >= 0;
            }
            case MORE: {
                return airportVal.compareTo(filterVal) <= 0;
            }
            case EQUAL: {
                return airportVal.compareTo(filterVal) != 0;
            }
            case NOT_EQUAL: {
                return airportVal.compareTo(filterVal) == 0;
            }
        }

        return true;
    }
}
