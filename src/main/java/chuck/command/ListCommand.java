package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;
/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends Command {

    /**
     * Parses arguments for the list command.
     *
     * @param arguments the arguments (should be empty)
     * @return a new ListCommand instance
     * @throws ChuckException if arguments are provided when none expected
     */
    public static ListCommand parse(String arguments) throws ChuckException {
        return new ListCommand();
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        StringBuilder listMessage = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 1; i <= tasks.size(); i++) {
            listMessage.append(i).append(".").append(tasks.get(i)).append("\n");
        }
        return listMessage.toString().trim();
    }
}
