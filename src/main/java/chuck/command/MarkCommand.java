package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;
import chuck.ui.Ui;

/**
 * Command to mark a task as done.
 */
public class MarkCommand extends Command {
    private int taskNumber;
    
    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChuckException {
        tasks.get(taskNumber).markDone();
        ui.showMessage("Nice! I've marked this task as done:\n" + tasks.get(taskNumber));
    }
}