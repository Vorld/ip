package chuck.task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void delete(int taskNumber) {
        tasks.remove(taskNumber - 1);
    }

    public Task get(int taskNumber) {
        return tasks.get(taskNumber - 1);
    }

    public TaskList find(String searchString) {
        TaskList matchingTasks = new TaskList();

        for (Task t: tasks) {
            if (t.toString().contains(searchString)) {
                matchingTasks.add(t);
            }
        }

        return matchingTasks;
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int counter = 1;

        for (Task t: tasks) {
            s.append("\n" + counter + "." + t);
        }

        return s.toString();
    }
}