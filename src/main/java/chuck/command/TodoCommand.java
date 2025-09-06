package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.Task;
import chuck.task.TaskList;
import chuck.task.Todo;
/**
 * Command to create a new todo task.
 */
public class TodoCommand extends Command {
    private String description;
    
    public TodoCommand(String description) {
        this.description = description;
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        if (description.isEmpty()) {
            throw new ChuckException("Oops! Your description can't be empty :(");
        }

        tasks.add(new Todo(description));
        Task addedTask = tasks.get(tasks.size());
        return "Got it. I've added this task:\n" + addedTask + "\nNow you have " + tasks.size()
                + " tasks in the list.";
    }
}