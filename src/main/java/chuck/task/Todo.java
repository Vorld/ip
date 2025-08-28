package chuck.task;

/**
 * Represents a simple todo task.
 * A todo task only has a description and completion status.
 */
public class Todo extends Task{
    public static final String SHORT_HAND = "T";

    /**
     * Creates a new todo with the given description.
     *
     * @param description the description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Creates a new todo with description and completion status.
     *
     * @param description the description of the todo task
     * @param isDone whether the todo is completed
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns formatted string with [T] prefix and task details.
     *
     * @return string representation with todo type indicator
     */
    @Override
    public String toString() {
        return String.format("[%s]%s", SHORT_HAND, super.toString());
    }

    /**
     * Returns formatted string for file storage with T type indicator.
     *
     * @return string representation suitable for saving to file
     */
    @Override
    public String saveString() {
        return String.format("%s | %s", "T", super.saveString());
    }
}
