package chuck.command;

import chuck.ChuckException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing user input and formatting date/time strings.
 * Handles command parsing, argument extraction, and date/time conversions
 * between different formats for display and storage.
 */
public class Parser {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    /**
     * Parses user input to identify the command type.
     *
     * @param input the user input string
     * @return the Command enum corresponding to the input, or null if no match
     */
    public static Command parseCommand(String input) {
        return Command.fromString(input);
    }

    /**
     * Extracts command arguments based on command type.
     * Different commands have different argument patterns and parsing rules.
     *
     * @param input the full user input string
     * @param command the command type to parse arguments for
     * @return array of parsed arguments specific to the command type
     */
    public static String[] parseArguments(String input, Command command) {
        switch (command) {
        case MARK:
        case UNMARK:
            return new String[]{input.substring(command == Command.MARK ? 5 : 7).trim()};
        case FIND:
            return new String[]{input.substring(5).trim()};
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

    /**
     * Converts date/time string to LocalDateTime using input format.
     *
     * @param dateTimeString the date/time string in "yyyy-MM-dd HH:mm" format
     * @return LocalDateTime object parsed from the input string
     * @throws ChuckException if the date/time string format is invalid
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws ChuckException {
        try {
            return LocalDateTime.parse(dateTimeString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ChuckException("Invalid date format! Use yyyy-MM-dd HH:mm");
        }
    }

    /**
     * Formats LocalDateTime for display to user.
     *
     * @param dateTime the LocalDateTime to format
     * @return formatted string in "MMM dd yyyy h:mma" format for display
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(OUTPUT_FORMATTER);
    }

    /**
     * Formats LocalDateTime for saving to file.
     *
     * @param dateTime the LocalDateTime to format
     * @return formatted string in "yyyy-MM-dd HH:mm" format for file storage
     */
    public static String formatDateTimeForSave(LocalDateTime dateTime) {
        return dateTime.format(INPUT_FORMATTER);
    }
}