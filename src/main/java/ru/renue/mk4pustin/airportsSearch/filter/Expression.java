package ru.renue.mk4pustin.airportsSearch.filter;

public class Expression {
    private int columnNumber;
    private Operation operation;
    private String value;

    public Expression(int columnNumber, Operation operation, String value) {
        this.columnNumber = columnNumber;
        this.operation = operation;
        this.value = value;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "columnNumber=" + columnNumber +
                ", operation=" + operation +
                ", value='" + value + '\'' +
                '}';
    }
}
