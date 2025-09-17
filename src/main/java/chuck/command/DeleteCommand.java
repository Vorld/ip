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

    /**
     * Parses arguments for the delete command.
     *
     * @param arguments the task number to delete
     * @return a new DeleteCommand instance
     * @throws ChuckException if the task number is invalid
     */
    public static DeleteCommand parse(String arguments) throws ChuckException {
        try {
            int taskNumber = Integer.parseInt(arguments.trim());
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new ChuckException("Please provide a valid task number for delete!");
        }
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        Task deletedTask = tasks.get(taskNumber);
        tasks.delete(taskNumber);
        return "Poof! Gone forever. Removed this task:\n" + deletedTask;
    }
}
