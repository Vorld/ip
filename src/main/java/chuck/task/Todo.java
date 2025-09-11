package chuck.task;

import java.util.Set;

/**
 * Represents a simple todo task.
 * A todo task only has a description and completion status.
 */
public class Todo extends Task {
    public static final String TYPE_SYMBOL = "T";

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
     * Creates a new todo with description, completion status, and tags.
     *
     * @param description the description of the todo task
     * @param isDone whether the todo is completed
     * @param tags the set of tags for this todo
     */
    public Todo(String description, boolean isDone, Set<String> tags) {
        super(description, isDone, tags);
    }

    /**
     * Returns formatted string with [T] prefix and task details.
     *
     * @return string representation with todo type indicator
     */
    @Override
    public String toString() {
        return String.format("[%s]%s", TYPE_SYMBOL, super.toString());
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
