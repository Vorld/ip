package chuck.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import chuck.ChuckException;

/**
 * Utility class for parsing user input and formatting date/time strings.
 * Handles command parsing, creates appropriate Command objects, and date/time conversions
 * between different formats for display and storage.
 */
public class Parser {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    /**
     * Parses user input and returns the appropriate Command object.
     *
     * @param input the user input string
     * @return the Command object corresponding to the input
     * @throws ChuckException if the command is invalid or parsing fails
     */
    public static Command parse(String input) throws ChuckException {
        assert input != null && !input.trim().isEmpty() : "Input cannot be null or empty";

        String[] commandTokens = input.trim().split(" ", 2);
        String commandWord = commandTokens[0].toLowerCase();
        String arguments = commandTokens.length > 1 ? commandTokens[1] : "";

        Command result = switch (commandWord) {
            case "bye" -> ByeCommand.parse(arguments);
            case "list" -> ListCommand.parse(arguments);
            case "find" -> FindCommand.parse(arguments);
            case "delete" -> DeleteCommand.parse(arguments);
            case "mark" -> MarkCommand.parse(arguments);
            case "unmark" -> UnmarkCommand.parse(arguments);
            case "todo" -> TodoCommand.parse(arguments);
            case "deadline" -> DeadlineCommand.parse(arguments);
            case "event" -> EventCommand.parse(arguments);
            case "save" -> SaveCommand.parse(arguments);
            case "tag" -> TagCommand.parse(arguments);
            case "filter" -> FilterCommand.parse(arguments);
            default -> throw new ChuckException("Oops! That's not a real Chuck command!");
        };

        assert result != null : "Parser must return a valid Command object";
        return result;
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
