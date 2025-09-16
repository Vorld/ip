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
        tasks.get(taskNumber).markDone();
        return "Nice! I've marked this task as done:\n\n" + tasks.get(taskNumber).toDisplayString();
    }
}
