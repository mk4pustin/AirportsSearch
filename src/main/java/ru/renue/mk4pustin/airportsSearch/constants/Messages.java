package ru.renue.mk4pustin.airportsSearch.constants;

public final class Messages {
    public static final String INTRODUCE_FILTER_MESSAGE = "Enter filter:";
    public static final String INTRODUCE_AIRPORT_NAME_MESSAGE = "Enter the beginning of the airport name:";
    public static final String EXIT_COMMAND = "!quit";
    public static final String FILTER_FORMAT_ERROR_MESSAGE = "Filter format error. Correct format: " +
            "column[<column number from 1>]<comparison operation><value>";
    public static final String COLUMN_NUMBER_ERROR_MESSAGE = "Error in column number. It must be greater " +
            "than zero and less than ";
    public static final String BRACKETS_FORMAT_ERROR_MESSAGE = "Error defining expressions in brackets.";
    public static final String RESULTING_MESSAGE = "Number of lines found: ";
    public static final String RESULTING_TIME = "Search time: ";
    public static final String REPEAT_MESSAGE = "Please try again...";
    public static final String DIFFERENT_TYPES_ERROR_MESSAGE = "Error comparing with table value. " +
            "Different types of values.";
}
