package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;

/**
 * Command to unmark a task (mark as not done).
 */
public class UnmarkCommand extends Command {
    private int taskNumber;
    
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        tasks.get(taskNumber).unmarkDone();
        return "OK, I've marked this task as not done yet:\n" + tasks.get(taskNumber);
    }
}