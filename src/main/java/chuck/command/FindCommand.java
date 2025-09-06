package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;
import chuck.ui.Ui;

/**
 * Command to find tasks containing a search string.
 */
public class FindCommand extends Command {
    private String searchString;
    
    public FindCommand(String searchString) {
        this.searchString = searchString;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChuckException {
        if (searchString.isEmpty()) {
            throw new ChuckException("Oops! You can't search for nothing :(");
        }

        TaskList matchingTasks = tasks.find(searchString);

        if (matchingTasks.size() > 0) {
            ui.showMessage("Here are the matching tasks in your list:" + matchingTasks);
        } else {
            ui.showMessage("No matching tasks found :(");
        }
    }
}