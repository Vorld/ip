package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;
import chuck.ui.Ui;

/**
 * Command to exit the application.
 */
public class ByeCommand extends Command {
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChuckException {
        ui.showMessage("See you around!");
    }
    
    @Override
    public boolean isExit() {
        return true;
    }
}