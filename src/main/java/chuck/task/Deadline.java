package chuck.task;

import java.time.LocalDateTime;
import java.util.Set;

import chuck.command.Parser;

/**
 * Represents a task with a deadline (due by specific date/time).
 * Extends the basic Task functionality with a deadline date and time.
 */
public class Deadline extends Task {
    public static final String TYPE_SYMBOL = "D";
    protected LocalDateTime by;

    /**
     * Creates a new deadline task with description and due date.
     *
     * @param description the description of the deadline task
     * @param by the date and time when the task is due
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Creates a new deadline task with description, completion status, and due date.
     *
     * @param description the description of the deadline task
     * @param isDone whether the deadline task is completed
     * @param by the date and time when the task is due
     */
    public Deadline(String description, boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Creates a new deadline task with description, completion status, due date, and tags.
     *
     * @param description the description of the deadline task
     * @param isDone whether the deadline task is completed
     * @param by the date and time when the task is due
     * @param tags the set of tags for this deadline
     */
    public Deadline(String description, boolean isDone, LocalDateTime by, Set<String> tags) {
        super(description, isDone, tags);
        this.by = by;
    }


    /**
     * Returns formatted string with [D] prefix and deadline information.
     *
     * @return string representation with deadline type indicator and due date
     */
    @Override
    public String toString() {
        return String.format("[%s]%s (by: %s)", TYPE_SYMBOL, super.toString(), Parser.formatDateTime(this.by));
    }

    /**
     * Returns formatted string for file storage with deadline data.
     *
     * @return string representation suitable for saving to file with due date
     */
    @Override
    public String saveString() {
        return String.format("%s | %s | %s", TYPE_SYMBOL, super.saveString(), Parser.formatDateTimeForSave(this.by));
    }
}
