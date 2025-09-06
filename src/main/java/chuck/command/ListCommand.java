package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;
/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        StringBuilder listMessage = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 1; i <= tasks.size(); i++) {
            listMessage.append(i).append(".").append(tasks.get(i)).append("\n");
        }
        return listMessage.toString().trim();
    }
}