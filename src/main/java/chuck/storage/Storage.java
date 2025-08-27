
package chuck.storage;

import chuck.ChuckException;
import chuck.command.Parser;
import chuck.task.Deadline;
import chuck.task.Event;
import chuck.task.Task;
import chuck.task.TaskList;
import chuck.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Scanner;

public class Storage {
    private static final String FILE_PATH = "./data";
    private static final String FILE_NAME = "chuck.txt";

    public TaskList loadTasks() throws ChuckException {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File saveFile = new File(FILE_PATH + "/" + FILE_NAME);
            Scanner in = new Scanner(saveFile);

            while (in.hasNextLine()) {
                String[] lineData = in.nextLine().split("\\|");
                String type = lineData[0].trim();

                switch (type) {
                case Todo.SHORT_HAND: {
                    String description = lineData[2].trim();
                    boolean isDone = Boolean.parseBoolean(lineData[1].trim());
                    tasks.add(new Todo(description, isDone));
                    break;
                }
                case Deadline.SHORT_HAND: {
                    String description = lineData[2].trim();
                    boolean isDone = Boolean.parseBoolean(lineData[1].trim());
                    String by = lineData[3].trim();

                    LocalDateTime byDateTime = Parser.parseDateTime(by);
                    tasks.add(new Deadline(description, isDone, byDateTime));
                    break;
                }
                case Event.SHORT_HAND: {
                    String description = lineData[2].trim();
                    boolean isDone = Boolean.parseBoolean(lineData[1].trim());
                    String from = lineData[3].trim();
                    String to = lineData[4].trim();

                    LocalDateTime fromDateTime = Parser.parseDateTime(from);
                    LocalDateTime toDateTime = Parser.parseDateTime(to);
                    tasks.add(new Event(description, isDone, fromDateTime, toDateTime));
                    break;
                }
                }
            }

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Couldn't find a save file! Continuing anyways...");
        } catch (DateTimeParseException dateTimeParseException) {
            System.out.println("Dates are formatted wrongly in the save file! Continuing anyways...");
        }

        return new TaskList(tasks);
    }

    public void saveTasks(TaskList tasks) throws ChuckException {
        try {
            File directory = new File(FILE_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            PrintWriter out = new PrintWriter(FILE_PATH + "/" + FILE_NAME);

            for (Task t: tasks.getTasks()) {
                out.println(t.saveString());
            }

            out.close();
        } catch (FileNotFoundException fileNotFoundException) {
            throw new ChuckException("Oops! There was an error saving your file!");
        }
    }
}
