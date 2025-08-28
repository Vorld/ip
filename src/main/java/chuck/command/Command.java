package chuck.command;

/**
 * Enum representing the available commands in the Chuck application.
 */
public enum Command {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    LIST("list"),
    FIND("find"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    SAVE("save"),
    BYE("bye");

    private final String command;

    /**
     * Constructor for enum values with command string.
     *
     * @param command the string representation of the command
     */
    Command(String command) {
        this.command = command;
    }

    /**
     * Parses input string and returns corresponding Command enum.
     * Extracts the first word from the input and matches it against available commands.
     *
     * @param input the string to parse
     * @return the matching Command enum, or null if no match found
     */
    public static Command fromString(String input) {
        String command = input.split(" ")[0];
        for (Command type : values()) {
            if (type.command.equals(command)) {
                return type;
            }
        }

        // If the input matches none of the commands we return null
        return null;
    }
}
