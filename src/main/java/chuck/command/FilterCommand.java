package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.Task;
import chuck.task.TaskList;

/**
 * Command to filter tasks by tag.
 * Usage: filter [tag]
 * Shows tasks that have the specified tag.
 */
public class FilterCommand extends Command {
    private final String tag;

    /**
     * Creates a FilterCommand.
     *
     * @param tag the tag to filter by
     * @throws ChuckException if the tag is invalid
     */
    public FilterCommand(String tag) throws ChuckException {
        if (tag == null || tag.trim().isEmpty()) {
            throw new ChuckException("Please provide a tag to filter by!");
        }
        this.tag = tag.trim();
    }

    /**
     * Parses arguments for the filter command.
     *
     * @param arguments the tag to filter by
     * @return a new FilterCommand instance
     * @throws ChuckException if the format is invalid
     */
    public static FilterCommand parse(String arguments) throws ChuckException {
        if (arguments.trim().isEmpty()) {
            throw new ChuckException("Filter command requires a tag! Usage: filter <tag>");
        }

        return new FilterCommand(arguments.trim());
    }

    /**
     * Executes the filter command by showing tasks that have the specified tag.
     *
     * @param tasks the task list to filter
     * @param storage the storage system (not used for filtering)
     * @return string showing matching tasks
     * @throws ChuckException if no tasks match the filter criteria
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        StringBuilder result = new StringBuilder();
        int matchCount = 0;

        result.append("Rats! Filtering through all this mess... here are the tasks with tag '")
                .append(tag)
                .append("':\n");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i + 1); // TaskList uses 1-based indexing
            
            if (task.hasTag(tag)) {
                matchCount++;
                result.append(matchCount).append(". ").append(task.toDisplayString());
                if (matchCount < tasks.size()) { // Add spacing between tasks
                    result.append("\n\n");
                }
            }
        }

        if (matchCount == 0) {
            throw new ChuckException("No tasks found with that tag. Maybe you misspelled it?");
        }

        return result.toString().trim();
    }
}
