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

            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("See you around!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.startsWith("mark ")) {
                System.out.println("____________________________________________________________");
                String taskNumberStr = input.substring(5);
                int taskNumber = Integer.parseInt(taskNumberStr);

                taskList[taskNumber-1].markDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(taskList[taskNumber-1]);
            } else if (input.startsWith("unmark ")) {
                System.out.println("____________________________________________________________");
                String taskNumberStr = input.substring(7);
                int taskNumber = Integer.parseInt(taskNumberStr);

                taskList[taskNumber-1].unmarkDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(taskList[taskNumber-1]);
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list: ");
                for (int i = 0; i < 100; i++) {
                    if (taskList[i] == null) {
                        break;
                    }
                    System.out.println((i+1) + ". " + taskList[i]);
                }
            } else {
                taskList[curr_index] = new Task(input);
                System.out.println("____________________________________________________________");
                System.out.println("added: " + input);
                curr_index++;
            }

            System.out.println("____________________________________________________________");
        }


    }
}
