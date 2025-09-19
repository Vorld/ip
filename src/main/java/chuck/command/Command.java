package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;

/**
 * Abstract base class for all commands in the Chuck application.
 * Each concrete command class should implement a static parse(String arguments) method
 * that returns an instance of the command parsed from the given arguments.
 */
public abstract class Command {
    
    /**
     * Executes the command with the given context.
     *
     * @param tasks the task list to operate on
     * @param storage the storage system for persistence
     * @return the response message for the user
     * @throws ChuckException if there are errors during command execution
     */
    public abstract String execute(TaskList tasks, Storage storage) throws ChuckException;
    
    /**
     * Returns whether this command causes the application to exit.
     * By default, commands do not cause exit.
     *
     * @return true if this command should terminate the application, false otherwise
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Helper method to automatically save tasks after modification.
     * Should be called by commands that modify the task list.
     *
     * @param tasks the task list to save
     * @param storage the storage system for persistence
     * @throws ChuckException if there are errors during save
     */
    protected void autoSave(TaskList tasks, Storage storage) throws ChuckException {
        storage.saveTasks(tasks);
    }
}
