import java.util.Scanner;

public class Chuck {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________\n");
        System.out.println("Hey! I'm Chuck, short for Charlie.\n");
        System.out.println("How can I help you?\n");
        System.out.println("____________________________________________________________");

        Task[] taskList = new Task[100];
        Scanner scanner = new Scanner(System.in);
        int curr_index = 0;

        while (true) {
            try {
                String input = scanner.nextLine();
                if (input.equals("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println("See you around!");
                    System.out.println("____________________________________________________________");
                    break;
                } else if (input.equals("list")) {
                    System.out.println("____________________________________________________________");
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < 100; i++) {
                        if (taskList[i] == null) {
                            break;
                        }
                        System.out.println((i + 1) + "." + taskList[i]);
                    }
                } else if (input.startsWith("mark ")) {
                    System.out.println("____________________________________________________________");
                    String taskNumberStr = input.substring(5);
                    int taskNumber = Integer.parseInt(taskNumberStr);

                    taskList[taskNumber - 1].markDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(taskList[taskNumber - 1]);
                } else if (input.startsWith("unmark ")) {
                    System.out.println("____________________________________________________________");
                    String taskNumberStr = input.substring(7);
                    int taskNumber = Integer.parseInt(taskNumberStr);

                    taskList[taskNumber - 1].unmarkDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(taskList[taskNumber - 1]);
                } else if (input.startsWith("todo ")) {
                    System.out.println("____________________________________________________________");
                    String description = input.substring(5);

                    if (description.trim().isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    taskList[curr_index] = new Todo(description);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(taskList[curr_index]);
                    curr_index++;
                    System.out.println("Now you have " + curr_index + " tasks in the list.");

                } else if (input.startsWith("deadline ")) {
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

                    taskList[curr_index] = new Deadline(description, by);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(taskList[curr_index]);
                    curr_index++;
                    System.out.println("Now you have " + curr_index + " tasks in the list.");

                } else if (input.startsWith("event ")) {
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

                    taskList[curr_index] = new Event(description, from, to);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(taskList[curr_index]);
                    curr_index++;
                    System.out.println("Now you have " + curr_index + " tasks in the list.");

                } else {
                    throw new ChuckException("Oops! That's not a real Chuck command!");
                }

                System.out.println("____________________________________________________________");
            } catch (ChuckException ce) {
                System.out.println(ce.getMessage());
                System.out.println("____________________________________________________________");
            }
        }


    }
}
