package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.Task;
import chuck.task.TaskList;

/**
 * Command to mark a task as done.
 */
public class MarkCommand extends Command {
    private int taskNumber;
    
    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Parses arguments for the mark command.
     *
     * @param arguments the task number to mark as done
     * @return a new MarkCommand instance
     * @throws ChuckException if the task number is invalid
     */
    public static MarkCommand parse(String arguments) throws ChuckException {
        try {
            int taskNumber = Integer.parseInt(arguments.trim());
            return new MarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new ChuckException("Please provide a valid task number for mark!");
        }
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new ChuckException("Task number " + taskNumber + " doesn't exist! You only have "
                    + tasks.size() + " tasks.");
        }

        Task task = tasks.get(taskNumber);
        task.markDone();
        return "You did it, good ol' friend! Marked this task as done:\n\n" + task.toDisplayString();
    }
}
