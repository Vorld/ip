package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;

/**
 * Command to save tasks to storage.
 */
public class SaveCommand extends Command {
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        storage.saveTasks(tasks);
        return "Saved your tasks to hard disk!";
    }
}