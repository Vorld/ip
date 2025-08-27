public enum Command {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    SAVE("save"),
    BYE("bye");

    private final String command;

    Command(String command) {
        this.command = command;
    }

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
