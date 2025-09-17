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

    /**
     * Parses arguments for the unmark command.
     *
     * @param arguments the task number to mark as not done
     * @return a new UnmarkCommand instance
     * @throws ChuckException if the task number is invalid
     */
    public static UnmarkCommand parse(String arguments) throws ChuckException {
        try {
            int taskNumber = Integer.parseInt(arguments.trim());
            return new UnmarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new ChuckException("Please provide a valid task number for unmark!");
        }
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        tasks.get(taskNumber).unmarkDone();
        return "Sigh... back to square one! I've marked this task as not done yet:\n\n" + tasks.get(taskNumber).toDisplayString();
    }
}
