package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;

/**
 * Command to mark a task as done.
 */
public class MarkCommand extends Command {
    private int taskNumber;
    
    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        tasks.get(taskNumber).markDone();
        return "Nice! I've marked this task as done:\n" + tasks.get(taskNumber);
    }
}
