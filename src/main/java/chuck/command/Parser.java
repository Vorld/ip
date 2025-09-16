package chuck.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import chuck.ChuckException;
import chuck.task.Deadline;
import chuck.task.Event;
import chuck.task.Task;
import chuck.task.TaskList;
import chuck.task.Todo;

/**
 * Utility class for parsing user input and formatting date/time strings.
 * Handles command parsing, creates appropriate Command objects, and date/time conversions
 * between different formats for display and storage.
 * Also handles parsing tasks from file content for Storage operations.
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

    /**
     * Parses tag string into a Set of tags.
     *
     * @param tagString comma-separated tag string
     * @return Set of individual tags, empty set if tagString is empty
     */
    public static Set<String> parseTags(String tagString) {
        return tagString.isEmpty() ? new HashSet<>() :
            new HashSet<>(Arrays.asList(tagString.split(",")));
    }

    /**
     * Parses file content and returns TaskList containing all tasks.
     *
     * @param content the file content as a single string
     * @return TaskList containing all parsed tasks
     * @throws ChuckException if there are critical parsing errors
     */
    public static TaskList parseTasksFromFileContent(String content) throws ChuckException {
        ArrayList<Task> tasks = new ArrayList<>();

        String[] lines = content.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            Task task = parseTaskFromLine(line);
            tasks.add(task);
        }

        return new TaskList(tasks);
    }

    /**
     * Parses a single line from save file into a Task.
     *
     * @param line the save file line
     * @return Task instance or null if invalid format
     * @throws ChuckException if parsing fails
     */
    private static Task parseTaskFromLine(String line) throws ChuckException {
        String[] data = line.split("\\|");
        if (data.length == 0) {
            return null;
        }

        String type = data[0].trim();
        return switch (type) {
            case Todo.TYPE_SYMBOL -> Todo.fromSaveString(line);
            case Deadline.TYPE_SYMBOL -> Deadline.fromSaveString(line);
            case Event.TYPE_SYMBOL -> Event.fromSaveString(line);
            default -> {
                // TODO: show the error on GUI?
                System.out.println("Skipping incorrectly formatted line in save file: " + line);
                yield null;
            }
        };
    }

}
