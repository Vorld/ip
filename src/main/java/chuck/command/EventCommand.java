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
    
    /**
     * Creates a new event command.
     *
     * @param description The description of the event task
     * @param startDate The start date of the event task
     * @param endDate The end date of the event task
     */
    public EventCommand(String description, String startDate, String endDate) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Parses arguments for the event command.
     *
     * @param arguments the arguments containing description /from startDate /to endDate
     * @return a new EventCommand instance
     * @throws ChuckException if the format is invalid
     */
    public static EventCommand parse(String arguments) throws ChuckException {
        if (!arguments.contains("/from ")) {
            throw new ChuckException("Ensure you have a /from date for event tasks!");
        }
        if (!arguments.contains("/to ")) {
            throw new ChuckException("Ensure you have a /to date for event tasks!");
        }

        String description = arguments.substring(0, arguments.indexOf("/from ")).trim();
        String startDate = arguments.substring(arguments.indexOf("/from ") + 6, arguments.indexOf("/to ")).trim();
        String endDate = arguments.substring(arguments.indexOf("/to ") + 4).trim();
        return new EventCommand(description, startDate, endDate);
    }
    
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        if (description.isEmpty()) {
            throw new ChuckException("Your description can't be empty :(");
        }

        if (startDate.isEmpty()) {
            throw new ChuckException("Your from date can't be empty :(");
        }
        if (endDate.isEmpty()) {
            throw new ChuckException("Your to date can't be empty :(");
        }

        LocalDateTime fromDateTime = Parser.parseDateTime(startDate);
        LocalDateTime toDateTime = Parser.parseDateTime(endDate);
        tasks.add(new Event(description, fromDateTime, toDateTime));
        Task addedTask = tasks.get(tasks.size());
        return "Good grief, your schedule is filling up! Added this event:\n\n"
                + addedTask.toDisplayString()
                + "\n\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
