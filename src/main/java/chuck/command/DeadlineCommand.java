package chuck.command;

import java.time.LocalDateTime;

import chuck.ChuckException;
import chuck.command.Parser;
import chuck.storage.Storage;
import chuck.task.Deadline;
import chuck.task.Task;
import chuck.task.TaskList;

/**
 * Command to create a new deadline task.
 */
public class DeadlineCommand extends Command {
    private String description;
    private String by;
    
    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        if (description.isEmpty()) {
            throw new ChuckException("Oops! Your description can't be empty :(");
        }

        if (by.isEmpty()) {
            throw new ChuckException("Oops! Your by date can't be empty :(");
        }

        LocalDateTime byDateTime = Parser.parseDateTime(by);
        tasks.add(new Deadline(description, byDateTime));
        Task addedTask = tasks.get(tasks.size());
        return "Got it. I've added this task:\n" + addedTask + "\nNow you have " + tasks.size()
                + " tasks in the list.";
    }
}