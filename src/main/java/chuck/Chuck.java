package chuck;

import chuck.command.Command;
import chuck.command.Parser;
import chuck.storage.Storage;
import chuck.task.TaskList;
import chuck.ui.Gui;
import javafx.application.Application;

/**
 * Main class for the Chuck assistant application.
 * Chuck is a personal assistant that helps users organize their todos, deadlines, and events.
 */
public class Chuck {
    private Storage storage;
    private TaskList tasks;
    private String loadingWarning;
    private boolean isExit;

    /**
     * Constructor for Chuck application with custom data file path.
     * Initializes the UI, storage, and task list.
     *
     * @param filePath the path to the data file
     */
    public Chuck(String filePath) {
        storage = new Storage(filePath);
        tasks = new TaskList();
        loadingWarning = null;
        isExit = false;
        try {
            tasks = storage.loadTasks();
        } catch (ChuckException ce) {
            // Start with empty task list if loading fails, but remember the warning
            tasks = new TaskList();
            loadingWarning = ce.getMessage();
        }
    }


    /**
     * Processes a command and returns the response.
     */
    public String getResponse(String input) {
        try {
            Command parsedCommand = Parser.parse(input);
            String response = parsedCommand.execute(tasks, storage);
            isExit = parsedCommand.isExit();
            return response;
        } catch (ChuckException e) {
            return e.getMessage();
        }
    }

    /**
     * Returns true if the last executed command was an exit command
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Returns welcome message to display on initialisation
     */
    public String getWelcomeMessage() {
        return "Good grief! It's me, Chuck. What can I help you with today?";
    }

    /**
     * Returns loading warning message if there were issues loading tasks, null otherwise
     */
    public String getLoadingWarning() {
        return loadingWarning;
    }

    /**
     * Entry point of the Chuck application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Gui.class, args);
    }

}
