package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;

/**
 * Command to save tasks to storage.
 */
public class SaveCommand extends Command {

    /**
     * Parses arguments for the save command.
     *
     * @param arguments the arguments (should be empty)
     * @return a new SaveCommand instance
     * @throws ChuckException if arguments are provided when none expected
     */
    public static SaveCommand parse(String arguments) throws ChuckException {
        return new SaveCommand();
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        storage.saveTasks(tasks);
        return "Whew! Your tasks are safely saved to disk.";
    }
}
