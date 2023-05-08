package ru.renue.mk4pustin.airportsSearch.filter;

public enum Operation {
    LESS("<"),
    MORE(">"),
    EQUAL("="),
    NOT_EQUAL("<>");

    private final String op;

    Operation(String op) {
        this.op = op;
    }

    public static Operation fromString(String op) {
        for (var operation : Operation.values()) {
            if (op.equals(operation.op)) return operation;
        }

        return null;
    }
}