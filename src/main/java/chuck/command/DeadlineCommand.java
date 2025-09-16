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

        String description = arguments.substring(0, arguments.indexOf("/by ")).trim();
        String dueDate = arguments.substring(arguments.indexOf("/by ") + 4).trim();
        return new DeadlineCommand(description, dueDate);
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        if (description.isEmpty()) {
            throw new ChuckException("Oops! Your description can't be empty :(");
        }

        if (dueDate.isEmpty()) {
            throw new ChuckException("Oops! Your by date can't be empty :(");
        }

        LocalDateTime byDateTime = Parser.parseDateTime(dueDate);
        tasks.add(new Deadline(description, byDateTime));
        Task addedTask = tasks.get(tasks.size());
        return "Got it. I've added this task:\n" + addedTask + "\nNow you have " + tasks.size()
                + " tasks in the list.";
    }
}
