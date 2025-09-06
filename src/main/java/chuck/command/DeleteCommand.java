package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.Task;
import chuck.task.TaskList;
import chuck.ui.Ui;

/**
 * Command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private int taskNumber;
    
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChuckException {
        Task deletedTask = tasks.get(taskNumber);
        tasks.delete(taskNumber);
        ui.showMessage("Noted. I've removed this task:\n" + deletedTask);
    }
}