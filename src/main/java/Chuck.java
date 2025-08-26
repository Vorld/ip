import java.util.ArrayList;
import java.util.Scanner;

public class Chuck {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________\n");
        System.out.println("Hey! I'm Chuck, short for Charlie.\n");
        System.out.println("How can I help you?\n");
        System.out.println("____________________________________________________________");

        ArrayList<Task> taskList = new ArrayList<>();
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
                    for (int i = 0; i < taskList.size(); i++) {
                        System.out.println((i + 1) + "." + taskList.get(i));
                    }
                    break;
                }
                case DELETE: {
                    System.out.println("____________________________________________________________");
                    String taskNumberStr = input.substring(7);
                    int taskNumber = Integer.parseInt(taskNumberStr);

                    System.out.println("Noted. I've removed this task:");
                    System.out.println(taskList.get(taskNumber - 1));
                    taskList.remove(taskNumber - 1);
                    break;
                }
                case MARK: {
                    System.out.println("____________________________________________________________");
                    String taskNumberStr = input.substring(5);
                    int taskNumber = Integer.parseInt(taskNumberStr);

                    taskList.get(taskNumber - 1).markDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(taskList.get(taskNumber - 1));
                    break;
                }
                case UNMARK: {
                    System.out.println("____________________________________________________________");
                    String taskNumberStr = input.substring(7);
                    int taskNumber = Integer.parseInt(taskNumberStr);

                    taskList.get(taskNumber - 1).unmarkDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(taskList.get(taskNumber - 1));
                    break;
                }
                case TODO: {
                    System.out.println("____________________________________________________________");
                    String description = input.substring(4);

                    if (description.trim().isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    taskList.add(new Todo(description));
                    System.out.println("Got it. I've added this task:");
                    System.out.println(taskList.get(taskList.size() - 1));
                    System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                    break;
                }
                case DEADLINE: {
                    System.out.println("____________________________________________________________");
                    String rest = input.substring(8);

                    if (!rest.contains(" /by ")) {
                        throw new ChuckException("Ensure you have a /by date for deadline tasks!");
                    }

                    String description = rest.substring(0, rest.indexOf(" /by "));

                    if (description.trim().isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    String by = rest.substring(rest.indexOf("/by ") + 4);

                    if (by.trim().isEmpty()) {
                        throw new ChuckException("Oops! Your by date can't be empty :(");
                    }

                    taskList.add(new Deadline(description, by));
                    System.out.println("Got it. I've added this task:");
                    System.out.println(taskList.get(taskList.size() - 1));
                    System.out.println("Now you have " + (taskList.size()) + " tasks in the list.");
                    break;
                }
                case EVENT: {
                    System.out.println("____________________________________________________________");
                    String rest = input.substring(5);

                    if (!rest.contains(" /from ")) {
                        throw new ChuckException("Ensure you have a /from date for event tasks!");
                    }
                    if (!rest.contains(" /to ")) {
                        throw new ChuckException("Ensure you have a /to date for event tasks!");
                    }

                    String description = rest.substring(0, rest.indexOf(" /from "));


                    if (description.trim().isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    String from = rest.substring(rest.indexOf("/from ") + 6, rest.indexOf(" /to "));
                    String to = rest.substring(rest.indexOf("/to ") + 4);

                    if (from.trim().isEmpty()) {
                        throw new ChuckException("Oops! Your from date can't be empty :(");
                    }
                    if (to.trim().isEmpty()) {
                        throw new ChuckException("Oops! Your to date can't be empty :(");
                    }

                    taskList.add(new Event(description, from, to));
                    System.out.println("Got it. I've added this task:");
                    System.out.println(taskList.get(taskList.size() - 1));
                    System.out.println("Now you have " + (taskList.size()) + " tasks in the list.");
                    break;
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
