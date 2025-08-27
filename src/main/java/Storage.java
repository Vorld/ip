import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Scanner;

public class Storage {
    private static final String FILE_PATH = "./data";
    private static final String FILE_NAME = "chuck.txt";

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File saveFile = new File(FILE_PATH + "/" + FILE_NAME);
            Scanner in = new Scanner(saveFile);

            while (in.hasNextLine()) {
                String[] lineData = in.nextLine().split("\\|");
                String type = lineData[0].trim();

                switch (type) {
                case "T": {
                    String description = lineData[2].trim();
                    boolean isDone = Boolean.parseBoolean(lineData[1].trim());
                    tasks.add(new Todo(description, isDone));
                    break;
                }
                case "D": {
                    String description = lineData[2].trim();
                    boolean isDone = Boolean.parseBoolean(lineData[1].trim());
                    String by = lineData[3].trim();
                    tasks.add(new Deadline(description, isDone, by));
                    break;
                }
                case "E": {
                    String description = lineData[2].trim();
                    boolean isDone = Boolean.parseBoolean(lineData[1].trim());
                    String from = lineData[3].trim();
                    String to = lineData[4].trim();
                    tasks.add(new Event(description, isDone, from, to));
                    break;
                }
                }
            }

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Couldn't find a save file! Continuing anyways...");
        }

        return tasks;
    }

    public void saveTasks(ArrayList<Task> tasks) throws ChuckException {
        try {
            File directory = new File(FILE_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            PrintWriter out = new PrintWriter(FILE_PATH + "/" + FILE_NAME);

            for (Task t: tasks) {
                out.println(t.saveString());
            }

            out.close();
        } catch (FileNotFoundException fileNotFoundException) {
            throw new ChuckException("Oops! There was an error saving your file!");
        }
    }
}
