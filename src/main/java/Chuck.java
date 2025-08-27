import java.util.ArrayList;
import java.util.Scanner;

public class Chuck {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________\n");
        System.out.println("Hey! I'm Chuck, short for Charlie.\n");
        System.out.println("How can I help you?\n");
        System.out.println("____________________________________________________________");

        ArrayList<Task> tasks = new ArrayList<>();
        Storage storage = new Storage();
        tasks = storage.loadTasks();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String input = scanner.nextLine();
                Command command = Command.fromString(input);

                if (command == null) {
                    throw new ChuckException("Oops! That's not a real Chuck command!");
                }

                switch (command) {
                case BYE: {
                    System.out.println("____________________________________________________________");
                    System.out.println("See you around!");
                    System.out.println("____________________________________________________________");
                    return;
                }
                case LIST:{
                    System.out.println("____________________________________________________________");
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                    break;
                }
                case DELETE: {
                    System.out.println("____________________________________________________________");
                    String taskNumberStr = input.substring(7);
                    int taskNumber = Integer.parseInt(taskNumberStr);

                    System.out.println("Noted. I've removed this task:");
                    System.out.println(tasks.get(taskNumber - 1));
                    tasks.remove(taskNumber - 1);
                    break;
                }
                case MARK: {
                    System.out.println("____________________________________________________________");
                    String taskNumberStr = input.substring(5);
                    int taskNumber = Integer.parseInt(taskNumberStr);

                    tasks.get(taskNumber - 1).markDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks.get(taskNumber - 1));
                    break;
                }
                case UNMARK: {
                    System.out.println("____________________________________________________________");
                    String taskNumberStr = input.substring(7);
                    int taskNumber = Integer.parseInt(taskNumberStr);

                    tasks.get(taskNumber - 1).unmarkDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks.get(taskNumber - 1));
                    break;
                }
                case TODO: {
                    System.out.println("____________________________________________________________");
                    String description = input.substring(4).trim();

                    if (description.isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    tasks.add(new Todo(description));
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    break;
                }
                case DEADLINE: {
                    System.out.println("____________________________________________________________");
                    String rest = input.substring(8).trim();

                    if (!rest.contains("/by ")) {
                        throw new ChuckException("Ensure you have a /by date for deadline tasks!");
                    }

                    String description = rest.substring(0, rest.indexOf("/by ")).trim();

                    if (description.isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    String by = rest.substring(rest.indexOf("/by ") + 4).trim();

                    if (by.isEmpty()) {
                        throw new ChuckException("Oops! Your by date can't be empty :(");
                    }

                    tasks.add(new Deadline(description, by));
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + (tasks.size()) + " tasks in the list.");
                    break;
                }
                case EVENT: {
                    System.out.println("____________________________________________________________");
                    String rest = input.substring(5).trim();

                    if (!rest.contains("/from ")) {
                        throw new ChuckException("Ensure you have a /from date for event tasks!");
                    }
                    if (!rest.contains("/to ")) {
                        throw new ChuckException("Ensure you have a /to date for event tasks!");
                    }

                    String description = rest.substring(0, rest.indexOf("/from ")).trim();


                    if (description.isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    String from = rest.substring(rest.indexOf("/from ") + 6, rest.indexOf("/to ")).trim();
                    String to = rest.substring(rest.indexOf("/to ") + 4).trim();

                    if (from.isEmpty()) {
                        throw new ChuckException("Oops! Your from date can't be empty :(");
                    }
                    if (to.isEmpty()) {
                        throw new ChuckException("Oops! Your to date can't be empty :(");
                    }

                    tasks.add(new Event(description, from, to));
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + (tasks.size()) + " tasks in the list.");
                    break;
                } case SAVE: {
                    System.out.println("Saved your tasks to hard disk!");
                    storage.saveTasks(tasks);
                }
                }

                System.out.println("____________________________________________________________");
            } catch (ChuckException ce) {
                System.out.println(ce.getMessage());
                System.out.println("____________________________________________________________");
            }
        }

    }
}
