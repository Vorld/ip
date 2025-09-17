package chuck.task;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base class representing a task with description and completion status (isDone).
 * This class provides common functionality for all task types including
 * marking as done/undone and a basic string representation.
 *
 * Subclasses should implement a static fromSaveString(String line) method that parses
 * a saved task string and returns an instance of the specific task type. This method
 * should handle the parsing of the line format produced by toSaveString().
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected Set<String> tags;

    /**
     * Creates a new task with the given description, initially not done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        assert description != null && !description.trim().isEmpty() : "Task description cannot be null or empty";

        this.description = description;
        this.isDone = false;
        this.tags = new HashSet<>();
    }

    /**
     * Creates a new task with description and completion status.
     *
     * @param description the description of the task
     * @param isDone whether the task is completed
     */
    public Task(String description, boolean isDone) {
        assert description != null && !description.trim().isEmpty()
                : "Task description cannot be null or empty";

        this.description = description;
        this.isDone = isDone;
        this.tags = new HashSet<>();
    }

    /**
     * Creates a new task with description, completion status, and tags.
     *
     * @param description the description of the task
     * @param isDone whether the task is completed
     * @param tags the set of tags for this task
     */
    public Task(String description, boolean isDone, Set<String> tags) {
        assert description != null && !description.trim().isEmpty()
                : "Task description cannot be null or empty";
        assert tags != null : "Tags set cannot be null";

        this.description = description;
        this.isDone = isDone;
        this.tags = new HashSet<>(tags);
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

    public void addTag(String tag) {
        assert tag != null && !tag.trim().isEmpty() : "Tag cannot be null or empty";
        this.tags.add(tag);
    }

    public void removeTag(String tag) {
        assert tag != null : "Tag cannot be null";
        this.tags.remove(tag);
    }

    public Set<String> getTags() {
        return this.tags;
    }

    public boolean hasTag(String tag) {
        assert tag != null : "Tag cannot be null";
        return this.tags.contains(tag);
    }

    /**
     * Returns string representation showing status and description.
     *
     * @return formatted string with status icon and task description
     */
    @Override
    public String toString() {
        String tagString = tags.isEmpty() ? ""
                : "#" + String.join(" #", tags);
        return String.format("[%s] %s %s",
                this.getStatusIcon(),
                this.description,
                tagString);
    }

    /**
     * Returns a formatted display string for GUI presentation.
     *
     * @return nicely formatted task string for display
     */
    public String toDisplayString() {
        StringBuilder display = new StringBuilder();

        // Status with simple symbols
        display.append(isDone ? "[✓] " : "[ ] ");

        // Task description
        display.append(description);

        // Tags with nice formatting
        if (!tags.isEmpty()) {
            display.append("\n   Tags: ").append(String.join(", ", tags));
        }

        return display.toString();
    }

    /**
     * Returns formatted string for saving to file.
     *
     * @return string representation suitable for file storage
     */
    public String toSaveString() {
        String tagString = tags.isEmpty() ? "" : String.join(",", tags);
        return String.format("%s | %s | %s", 
                this.isDone, 
                this.description, 
                tagString);
    };
}
