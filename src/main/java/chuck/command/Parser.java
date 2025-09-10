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
        String[] commandTokens = input.trim().split(" ", 2);
        String commandWord = commandTokens[0].toLowerCase();
        String arguments = commandTokens.length > 1 ? commandTokens[1] : "";

        switch (commandWord) {
        case "bye":
            return new ByeCommand();
        case "list":
            return new ListCommand();
        case "find":
            return new FindCommand(arguments);
        case "delete":
            try {
                int taskNumber = Integer.parseInt(arguments.trim());
                return new DeleteCommand(taskNumber);
            } catch (NumberFormatException e) {
                throw new ChuckException("Please provide a valid task number for delete!");
            }
        case "mark":
            try {
                int taskNumber = Integer.parseInt(arguments.trim());
                return new MarkCommand(taskNumber);
            } catch (NumberFormatException e) {
                throw new ChuckException("Please provide a valid task number for mark!");
            }
        case "unmark":
            try {
                int taskNumber = Integer.parseInt(arguments.trim());
                return new UnmarkCommand(taskNumber);
            } catch (NumberFormatException e) {
                throw new ChuckException("Please provide a valid task number for unmark!");
            }
        case "todo":
            return new TodoCommand(arguments);
        case "deadline":
            return parseDeadlineCommand(arguments);
        case "event":
            return parseEventCommand(arguments);
        case "save":
            return new SaveCommand();
        default:
            throw new ChuckException("Oops! That's not a real Chuck command!");
        }
    }

    /**
     * Parses deadline command arguments and creates a DeadlineCommand.
     *
     * @param arguments the arguments for the deadline command
     * @return DeadlineCommand object
     * @throws ChuckException if the format is invalid
     */
    private static DeadlineCommand parseDeadlineCommand(String arguments) throws ChuckException {
        if (!arguments.contains("/by ")) {
            throw new ChuckException("Ensure you have a /by date for deadline tasks!");
        }

        String description = arguments.substring(0, arguments.indexOf("/by ")).trim();
        String dueDate = arguments.substring(arguments.indexOf("/by ") + 4).trim();
        return new DeadlineCommand(description, dueDate);
    }

    /**
     * Parses event command arguments and creates an EventCommand.
     *
     * @param arguments the arguments for the event command
     * @return EventCommand object
     * @throws ChuckException if the format is invalid
     */
    private static EventCommand parseEventCommand(String arguments) throws ChuckException {
        if (!arguments.contains("/from ")) {
            throw new ChuckException("Ensure you have a /from date for event tasks!");
        }
        if (!arguments.contains("/to ")) {
            throw new ChuckException("Ensure you have a /to date for event tasks!");
        }

        String description = arguments.substring(0, arguments.indexOf("/from ")).trim();
        String startDate = arguments.substring(arguments.indexOf("/from ") + 6, arguments.indexOf("/to ")).trim();
        String endDate = arguments.substring(arguments.indexOf("/to ") + 4).trim();
        return new EventCommand(description, startDate, endDate);
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
