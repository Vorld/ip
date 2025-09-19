package chuck.command;

import java.time.LocalDateTime;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.Deadline;
import chuck.task.Task;
import chuck.task.TaskList;

/**
 * Command to create a new deadline task.
 */
public class DeadlineCommand extends Command {
    private String description;
    private String dueDate;
    
    /**
     * Creates a new deadline command.
     *
     * @param description The description of the deadline task
     * @param dueDate The due date of the deadline task
     */
    public DeadlineCommand(String description, String dueDate) {
        this.description = description;
        this.dueDate = dueDate;
    }

    /**
     * Parses arguments for the deadline command.
     *
     * @param arguments the arguments containing description /by dueDate
     * @return a new DeadlineCommand instance
     * @throws ChuckException if the format is invalid
     */
    public static DeadlineCommand parse(String arguments) throws ChuckException {
        if (!arguments.contains("/by ")) {
            throw new ChuckException("Ensure you have a /by date for deadline tasks!");
        }

        int byIndex = arguments.indexOf("/by ");
        if (byIndex == 0) {
            throw new ChuckException("Your description can't be empty :(");
        }
        if (byIndex + 4 >= arguments.length()) {
            throw new ChuckException("Your by date can't be empty :(");
        }

        String description = arguments.substring(0, byIndex).trim();
        String dueDate = arguments.substring(byIndex + 4).trim();

        if (description.isEmpty()) {
            throw new ChuckException("Your description can't be empty :(");
        }
        if (dueDate.isEmpty()) {
            throw new ChuckException("Your by date can't be empty :(");
        }

        return new DeadlineCommand(description, dueDate);
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        assert description != null && !description.isEmpty() : "Description should be validated in parse()";
        assert dueDate != null && !dueDate.isEmpty() : "Due date should be validated in parse()";

        LocalDateTime byDateTime = Parser.parseDateTime(dueDate);
        tasks.add(new Deadline(description, byDateTime));
        Task addedTask = tasks.get(tasks.size());
        autoSave(tasks, storage);
        return "Sigh... deadlines, deadlines! But don't worry, I've added:\n\n"
                + addedTask.toDisplayString()
                + "\n\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
