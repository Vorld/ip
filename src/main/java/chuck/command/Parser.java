package chuck.command;

import chuck.ChuckException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    public static Command parseCommand(String input) {
        return Command.fromString(input);
    }

    public static String[] parseArguments(String input, Command command) {
        switch (command) {
        case MARK:
        case UNMARK:
            return new String[]{input.substring(command == Command.MARK ? 5 : 7).trim()};
        case DELETE:
            return new String[]{input.substring(7).trim()};
        case TODO:
            return new String[]{input.substring(4).trim()};
        case DEADLINE:
            String deadlineRest = input.substring(8).trim();
            String deadlineDescription = deadlineRest.substring(0, deadlineRest.indexOf("/by ")).trim();
            String by = deadlineRest.substring(deadlineRest.indexOf("/by ") + 4).trim();
            return new String[]{deadlineDescription, by};
        case EVENT:
            String eventRest = input.substring(5).trim();
            String eventDescription = eventRest.substring(0, eventRest.indexOf("/from ")).trim();
            String from = eventRest.substring(eventRest.indexOf("/from ") + 6, eventRest.indexOf("/to ")).trim();
            String to = eventRest.substring(eventRest.indexOf("/to ") + 4).trim();
            return new String[]{eventDescription, from, to};
        default:
            return new String[]{};
        }
    }

    public static LocalDateTime parseDateTime(String dateTimeString) throws ChuckException {
        try {
            return LocalDateTime.parse(dateTimeString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ChuckException("Invalid date format! Use yyyy-MM-dd HH:mm");
        }
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(OUTPUT_FORMATTER);
    }

    public static String formatDateTimeForSave(LocalDateTime dateTime) {
        return dateTime.format(INPUT_FORMATTER);
    }
}