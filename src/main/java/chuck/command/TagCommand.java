package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.Task;
import chuck.task.TaskList;

/**
 * Command to add or remove tags from a task.
 * Usage: tag [task_number] [tag1,tag2,tag3]
 * To remove tags, prefix with minus: tag [task_number] -[tag1,tag2]
 */
public class TagCommand extends Command {
    private final int taskNumber;
    private final String[] tags;
    private final boolean isRemove;

    /**
     * Creates a TagCommand.
     *
     * @param taskNumber the task number (1-indexed)
     * @param tagString the comma-separated tags, with optional '-' prefix for removal
     * @throws ChuckException if the tag string is invalid
     */
    public TagCommand(int taskNumber, String tagString) throws ChuckException {
        if (tagString == null || tagString.trim().isEmpty()) {
            throw new ChuckException("Please provide tags to add or remove!");
        }

        this.taskNumber = taskNumber;
        
        // Check if removing tags (starts with -)
        if (tagString.startsWith("-")) {
            this.isRemove = true;
            tagString = tagString.substring(1);
        } else {
            this.isRemove = false;
        }

        // Split tags by comma
        this.tags = tagString.split(",");
        for (int i = 0; i < tags.length; i++) {
            tags[i] = tags[i].trim();
            if (tags[i].isEmpty()) {
                throw new ChuckException("Tag names cannot be empty!");
            }
        }
    }

    /**
     * Parses arguments for the tag command.
     *
     * @param arguments the arguments containing task_number and tags
     * @return a new TagCommand instance
     * @throws ChuckException if the format is invalid
     */
    public static TagCommand parse(String arguments) throws ChuckException {
        if (arguments.trim().isEmpty()) {
            throw new ChuckException("Tag command requires a task number and tags! Usage: tag <task_number> <tags>");
        }

        String[] parts = arguments.trim().split(" ", 2);
        if (parts.length < 2) {
            throw new ChuckException("Tag command requires a task number and tags! Usage: tag <task_number> <tags>");
        }

        try {
            int taskNumber = Integer.parseInt(parts[0].trim());
            String tagString = parts[1].trim();
            return new TagCommand(taskNumber, tagString);
        } catch (NumberFormatException e) {
            throw new ChuckException("Please provide a valid task number for tag command!");
        }
    }

    /**
     * Executes the tag command by adding or removing tags from the specified task.
     *
     * @param tasks the task list containing the task to tag
     * @param storage the storage system for persistence
     * @return success message indicating what tags were added/removed
     * @throws ChuckException if the task number is invalid
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new ChuckException("Please provide a valid task number!");
        }

        Task task = tasks.get(taskNumber);

        StringBuilder message = new StringBuilder();
        
        if (isRemove) {
            // Remove tags
            int removedCount = 0;
            for (String tag : tags) {
                if (task.hasTag(tag)) {
                    task.removeTag(tag);
                    removedCount++;
                }
            }
            
            if (removedCount == 0) {
                message.append("No matching tags found to remove from task ").append(taskNumber);
            } else {
                message.append("Removed ").append(removedCount).append(" tag(s) from task ").append(taskNumber);
            }
        } else {
            // Add tags
            int addedCount = 0;
            for (String tag : tags) {
                if (!task.hasTag(tag)) {
                    task.addTag(tag);
                    addedCount++;
                }
            }
            
            if (addedCount == 0) {
                message.append("All tags already exist on task ").append(taskNumber);
            } else {
                message.append("Added ").append(addedCount).append(" tag(s) to task ").append(taskNumber);
            }
        }

        autoSave(tasks, storage);
        return message + "\n\n" + task.toDisplayString();
    }
}
