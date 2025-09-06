package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;

/**
 * Command to exit the application.
 */
public class ByeCommand extends Command {

    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        return "See you around!";
    }

    @Override
    public boolean isExit() {
        // TODO: make this actually quit the JavaFX app
        return true;
    }
}
