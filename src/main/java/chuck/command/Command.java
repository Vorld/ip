package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;
import chuck.ui.Ui;

/**
 * Abstract base class for all commands in the Chuck application.
 */
public abstract class Command {
    
    /**
     * Executes the command with the given context.
     *
     * @param tasks the task list to operate on
     * @param ui the user interface for input/output
     * @param storage the storage system for persistence
     * @throws ChuckException if there are errors during command execution
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws ChuckException;
    
    /**
     * Returns whether this command causes the application to exit.
     * By default, commands do not cause exit.
     *
     * @return true if this command should terminate the application, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}