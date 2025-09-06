package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;
import chuck.ui.Ui;

/**
 * Command to unmark a task (mark as not done).
 */
public class UnmarkCommand extends Command {
    private int taskNumber;
    
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChuckException {
        tasks.get(taskNumber).unmarkDone();
        ui.showMessage("OK, I've marked this task as not done yet:\n" + tasks.get(taskNumber));
    }
}