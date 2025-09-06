package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;

/**
 * Command to find tasks containing a search string.
 */
public class FindCommand extends Command {
    private String searchString;
    
    public FindCommand(String searchString) {
        this.searchString = searchString;
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        if (searchString.isEmpty()) {
            throw new ChuckException("Oops! You can't search for nothing :(");
        }

        TaskList matchingTasks = tasks.find(searchString);

        if (matchingTasks.size() > 0) {
            return "Here are the matching tasks in your list:" + matchingTasks;
        } else {
            return "No matching tasks found :(";
        }
    }
}
