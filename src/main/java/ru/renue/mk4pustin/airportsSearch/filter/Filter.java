package ru.renue.mk4pustin.airportsSearch.filter;

import java.util.Arrays;
import java.util.List;

public class Filter {
    private Expression[] expressions;
    private List<Filter> andFilters;

    public Filter(Expression[] expressions, List<Filter> andFilters) {
        this.expressions = expressions;
        this.andFilters = andFilters;
    }

    public Expression[] getExpressions() {
        return expressions;
    }

    public void setExpressions(Expression[] expressions) {
        this.expressions = expressions;
    }

    public List<Filter> getAndFilters() {
        return andFilters;
    }

    public void setAndFilters(List<Filter> andFilters) {
        this.andFilters = andFilters;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "expressions=" + Arrays.toString(expressions) +
                ", andFilters=" + andFilters +
                ", l=" + andFilters.size() +
                '}';
    }
}
