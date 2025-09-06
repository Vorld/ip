package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.Task;
import chuck.task.TaskList;

/**
 * Command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private int taskNumber;
    
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        Task deletedTask = tasks.get(taskNumber);
        tasks.delete(taskNumber);
        return "Noted. I've removed this task:\n" + deletedTask;
    }
}
