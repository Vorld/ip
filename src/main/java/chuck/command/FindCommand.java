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

    /**
     * Parses arguments for the find command.
     *
     * @param arguments the search string to find in tasks
     * @return a new FindCommand instance
     * @throws ChuckException if the search string is empty
     */
    public static FindCommand parse(String arguments) throws ChuckException {
        if (arguments.trim().isEmpty()) {
            throw new ChuckException("You can't search for nothing :(");
        }
        return new FindCommand(arguments.trim());
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        assert searchString != null && !searchString.isEmpty() : "Search string should be validated in parse()";

        TaskList matchingTasks = tasks.find(searchString);

        if (!matchingTasks.isEmpty()) {
            return "Here are the matching tasks in your list:" + matchingTasks;
        } else {
            return "Good grief! Nothing found... maybe try a different search?";
        }
    }
}
