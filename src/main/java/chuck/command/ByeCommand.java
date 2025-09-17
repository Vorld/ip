package chuck.command;

import chuck.ChuckException;
import chuck.storage.Storage;
import chuck.task.TaskList;

/**
 * Command to exit the application.
 */
public class ByeCommand extends Command {

    /**
     * Parses arguments for the bye command.
     *
     * @param arguments the arguments (should be empty)
     * @return a new ByeCommand instance
     * @throws ChuckException if arguments are provided when none expected
     */
    public static ByeCommand parse(String arguments) throws ChuckException {
        return new ByeCommand();
    }

    @Override
    public String execute(TaskList tasks, Storage storage) throws ChuckException {
        return "See you later, good ol' pal!";
    }

    @Override
    public boolean isExit() {
        // TODO: make this actually quit the JavaFX app
        return true;
    }
}
