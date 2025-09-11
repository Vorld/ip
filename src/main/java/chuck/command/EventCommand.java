package chuck.command;

import java.time.LocalDateTime;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.Event;
import chuck.task.Task;
import chuck.task.TaskList;

/**
 * Command to create a new event task.
 */
public class EventCommand extends Command {
    private String description;
    private String startDate;
    private String endDate;
    
    public EventCommand(String description, String startDate, String endDate) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        if (description.isEmpty()) {
            throw new ChuckException("Oops! Your description can't be empty :(");
        }

        if (startDate.isEmpty()) {
            throw new ChuckException("Oops! Your from date can't be empty :(");
        }
        if (endDate.isEmpty()) {
            throw new ChuckException("Oops! Your to date can't be empty :(");
        }

        LocalDateTime fromDateTime = Parser.parseDateTime(startDate);
        LocalDateTime toDateTime = Parser.parseDateTime(endDate);
        tasks.add(new Event(description, fromDateTime, toDateTime));
        Task addedTask = tasks.get(tasks.size());
        return "Got it. I've added this task:\n" + addedTask + "\nNow you have " + tasks.size()
                + " tasks in the list.";
    }
}
