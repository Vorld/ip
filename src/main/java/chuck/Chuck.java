package chuck;

import chuck.command.Command;
import chuck.command.Parser;
import chuck.storage.Storage;
import chuck.task.TaskList;

/**
 * Main class for the Chuck assistant application.
 * Chuck is a personal assistant that helps users organize their todos, deadlines, and events.
 */
public class Chuck {
    private Storage storage;
    private TaskList tasks;

    /**
     * Constructor for Chuck application with custom data file path.
     * Initializes the UI, storage, and task list.
     *
     * @param filePath the path to the data file
     */
    public Chuck(String filePath) {
        storage = new Storage(filePath);
        tasks = new TaskList();
        try {
            tasks = storage.loadTasks();
        } catch (ChuckException ce) {
            // Start with empty task list if loading fails
        }
    }


    /**
     * Processes a command and returns the response.
     */
    public String getResponse(String input) {
        try {
            Command parsedCommand = Parser.parse(input);
            return parsedCommand.execute(tasks, storage);
        } catch (ChuckException e) {
            return "Error: " + e.getMessage();
        }
    }

}
