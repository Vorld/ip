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

    /**
     * Parses arguments for the todo command.
     *
     * @param arguments the description of the todo task
     * @return a new TodoCommand instance
     * @throws ChuckException if the description is empty
     */
    public static TodoCommand parse(String arguments) throws ChuckException {
        if (arguments.trim().isEmpty()) {
            throw new ChuckException("Your description can't be empty :(");
        }
        return new TodoCommand(arguments.trim());
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        assert description != null && !description.isEmpty() : "Description should be validated in parse()";

        tasks.add(new Todo(description));
        Task addedTask = tasks.get(tasks.size());
        return "Rats! Another task, but I've got it covered:\n\n"
                + addedTask.toDisplayString()
                + "\n\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
