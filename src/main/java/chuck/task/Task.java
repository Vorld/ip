package chuck.task;

/**
 * Abstract base class representing a task with description and completion status (isDone).
 * This class provides common functionality for all task types including
 * marking as done/undone and a basic string representation.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description, initially not done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        assert description != null && !description.trim().isEmpty() :"Task description cannot be null or empty";

        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates a new task with description and completion status.
     *
     * @param description the description of the task
     * @param isDone whether the task is completed
     */
    public Task(String description, boolean isDone) {
        assert description != null && !description.trim().isEmpty() : 
            "Task description cannot be null or empty";

        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the status icon for this task.
     *
     * @return "X" if task is done, " " (space) if not done
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks this task as completed.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void unmarkDone() {
        this.isDone = false;
    }

    /**
     * Returns string representation showing status and description.
     *
     * @return formatted string with status icon and task description
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }

    /**
     * Returns formatted string for saving to file.
     *
     * @return string representation suitable for file storage
     */
    public String saveString() {
        return String.format("%s | %s", this.isDone, this.description);
    };
}
