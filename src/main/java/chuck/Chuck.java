package chuck;

import chuck.command.Command;
import chuck.command.Parser;
import chuck.storage.Storage;
import chuck.task.TaskList;
import chuck.ui.Ui;

/**
 * Main class for the Chuck assistant application.
 * Chuck is a personal assistant that helps users organize their todos, deadlines, and events.
 */
public class Chuck {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    /**
     * Constructor for Chuck application.
     * Initializes the UI, storage, and task list.
     */
    public Chuck() {
        ui = new Ui();
        storage = new Storage();
        tasks = new TaskList();
        try {
            tasks = storage.loadTasks();
        } catch (ChuckException ce) {
            ui.showError("Error loading tasks: " + ce.getMessage() + "\nStarting with empty task list.");
        }
    }

    /**
     * Runs the main application loop.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (ChuckException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }

}
