package chuck.task;

import java.util.ArrayList;

/**
 * Manages a collection of tasks with operations to add, delete, and retrieve tasks.
 * Uses 1-indexed numbering for user-facing operations while internally using 0-indexed ArrayList.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list from existing ArrayList of tasks.
     *
     * @param tasks the ArrayList of tasks to initialize with
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        // TODO: Check for null here instead
        tasks.add(task);
    }

    /**
     * Removes task at the specified position (1-indexed).
     *
     * @param taskNumber the position of the task to remove (1-indexed)
     */
    public void delete(int taskNumber) {
        tasks.remove(taskNumber - 1);
    }

    /**
     * Gets task at the specified position (1-indexed).
     *
     * @param taskNumber the position of the task to retrieve (1-indexed)
     * @return the task at the specified position
     */
    public Task get(int taskNumber) {
        return tasks.get(taskNumber - 1);
    }

    /**
     * Finds tasks that contain the search string anywhere in its string representation.
     *
     * @param searchString the string to search
     * @return a TaskList of tasks that match the string
     */
    public TaskList find(String searchString) {
        TaskList matchingTasks = new TaskList();

        for (Task t: tasks) {
            if (t.toString().contains(searchString)) {
                matchingTasks.add(t);
            }
        }

        return matchingTasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the underlying ArrayList of tasks.
     *
     * @return the ArrayList containing all tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int counter = 1;

        for (Task t: tasks) {
            s.append("\n" + counter + "." + t);
            counter++;
        }

        return s.toString();
    }

    /**
     * Returns a nicely formatted display version of the task list for GUI.
     *
     * @return formatted task list with emojis and better spacing
     */
    public String toDisplayString() {
        if (tasks.isEmpty()) {
            return "Your list is emptier than my lunch bag! Time to add tasks or just relax.";
        }

        StringBuilder display = new StringBuilder();
        int counter = 1;

        for (Task task : tasks) {
            display.append(counter).append(". ");
            display.append(task.toDisplayString());
            if (counter < tasks.size()) {
                display.append("\n\n"); // Add spacing between tasks
            }
            counter++;
        }

        return display.toString();
    }
}
