package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;
import chuck.ui.Ui;

/**
 * Command to save tasks to storage.
 */
public class SaveCommand extends Command {
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChuckException {
        storage.saveTasks(tasks);
        ui.showMessage("Saved your tasks to hard disk!");
    }
}