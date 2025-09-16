
package chuck.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import chuck.ChuckException;
import chuck.command.Parser;
import chuck.task.Task;
import chuck.task.TaskList;

/**
 * Handles loading and saving tasks to/from file storage in a text file format,
 * with error handling for missing or corrupted files.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from file and returns TaskList.
     * Handles missing files and corrupt data gracefully by continuing with empty list.
     *
     * @return TaskList containing all loaded tasks, or empty list if file not found
     * @throws ChuckException if there are critical errors during loading
     */
    public TaskList loadTasks() throws ChuckException {
        try {
            String content = readFileContent();
            return Parser.parseTasksFromFileContent(content);
        } catch (FileNotFoundException fileNotFoundException) {
            return new TaskList(new ArrayList<>());
        } catch (DateTimeParseException dateTimeParseException) {
            return new TaskList(new ArrayList<>());
        }
    }

    /**
     * Reads the entire content of the save file.
     *
     * @return file content as a single string
     * @throws FileNotFoundException if save file doesn't exist
     */
    private String readFileContent() throws FileNotFoundException {
        File saveFile = new File(filePath);
        Scanner in = new Scanner(saveFile);
        StringBuilder content = new StringBuilder();

        while (in.hasNextLine()) {
            content.append(in.nextLine()).append("\n");
        }

        in.close();
        return content.toString();
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
            File saveFile = new File(filePath);
            File directory = saveFile.getParentFile();
            if (directory != null && !directory.exists()) {
                directory.mkdirs();
            }
            PrintWriter out = new PrintWriter(filePath);

            tasks.getTasks().stream()
                    .map(Task::toSaveString)
                    .forEach(out::println);

            out.close();
        } catch (FileNotFoundException fileNotFoundException) {
            throw new ChuckException("Oops! There was an error saving your file!");
        }
    }
}
