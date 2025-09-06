package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;
import chuck.ui.Ui;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChuckException {
        StringBuilder listMessage = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 1; i <= tasks.size(); i++) {
            listMessage.append(i).append(".").append(tasks.get(i)).append("\n");
        }
        ui.showMessage(listMessage.toString().trim());
    }
}