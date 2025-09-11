
package chuck.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import chuck.ChuckException;
import chuck.command.Parser;
import chuck.task.Deadline;
import chuck.task.Event;
import chuck.task.Task;
import chuck.task.TaskList;
import chuck.task.Todo;

/**
 * Handles loading and saving tasks to/from file storage in a text file format,
 * with error handling for missing or corrupted files.
 */
public class Storage {
    private static final String FILE_PATH = "./data";
    private static final String FILE_NAME = "chuck.txt";

    // TODO: set file_path and file_name in a constructor

    /**
     * Loads tasks from file and returns TaskList.
     * Handles missing files and corrupt data gracefully by continuing with empty list.
     *
     * @return TaskList containing all loaded tasks, or empty list if file not found
     * @throws ChuckException if there are critical errors during loading
     */
    public TaskList loadTasks() throws ChuckException {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File saveFile = new File(FILE_PATH + "/" + FILE_NAME);
            Scanner in = new Scanner(saveFile);

            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] lineData = line.split("\\|");
                String type = lineData[0].trim();

                switch (type) {
                case Todo.TYPE_SYMBOL: {
                    boolean isDone = Boolean.parseBoolean(lineData[1].trim());
                    String description = lineData[2].trim();
                    String tagString = lineData[3].trim();
                    Set<String> tags = tagString.isEmpty() ? new HashSet<>() : 
                        new HashSet<>(Arrays.asList(tagString.split(",")));
                    
                    tasks.add(new Todo(description, isDone, tags));
                    break;
                }
                case Deadline.TYPE_SYMBOL: {
                    boolean isDone = Boolean.parseBoolean(lineData[1].trim());
                    String description = lineData[2].trim();
                    String tagString = lineData[3].trim();
                    String dueDate = lineData[4].trim();
                    Set<String> tags = tagString.isEmpty() ? new HashSet<>() : 
                        new HashSet<>(Arrays.asList(tagString.split(",")));

                    LocalDateTime byDateTime = Parser.parseDateTime(dueDate);
                    tasks.add(new Deadline(description, isDone, byDateTime, tags));
                    break;
                }
                case Event.TYPE_SYMBOL: {
                    boolean isDone = Boolean.parseBoolean(lineData[1].trim());
                    String description = lineData[2].trim();
                    String tagString = lineData[3].trim();
                    String startDate = lineData[4].trim();
                    String endDate = lineData[5].trim();
                    Set<String> tags = tagString.isEmpty() ? new HashSet<>() : 
                        new HashSet<>(Arrays.asList(tagString.split(",")));

                    LocalDateTime fromDateTime = Parser.parseDateTime(startDate);
                    LocalDateTime toDateTime = Parser.parseDateTime(endDate);
                    tasks.add(new Event(description, isDone, fromDateTime, toDateTime, tags));
                    break;
                }
                default: {
                    // TODO: Not sure if this is the right Exception to throw here
                    System.out.println("Skipping incorrectly formatted line in save file: " + lineData + "...");
                }
                }
            }

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Couldn't find a save file! Continuing anyways...");
        } catch (DateTimeParseException dateTimeParseException) {
            System.out.println("Dates are formatted wrongly in the save file! Continuing anyways...");
        }

        return new TaskList(tasks);
    }

    /**
     * Saves all tasks from TaskList to file.
     * Creates the data directory if it doesn't exist.
     *
     * @param tasks the TaskList containing all tasks to save
     * @throws ChuckException if there are errors during the save operation
     */
    public void saveTasks(TaskList tasks) throws ChuckException {
        try {
            File directory = new File(FILE_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            PrintWriter out = new PrintWriter(FILE_PATH + "/" + FILE_NAME);
            // TODO: Breaking abstraction here kinda.
            tasks.getTasks().stream()
                    .map(Task::saveString)
                    .forEach(out::println);

            out.close();
        } catch (FileNotFoundException fileNotFoundException) {
            throw new ChuckException("Oops! There was an error saving your file!");
        }
    }
}
