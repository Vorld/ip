import java.util.Scanner;

public class Chuck {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________\n");
        System.out.println("Hey! I'm Chuck, short for Charlie.\n");
        System.out.println("How can I help you?\n");
        System.out.println("____________________________________________________________");

        String[] list = new String[100];
        Scanner scanner = new Scanner(System.in);
        int curr_index = 0;

        while (true) {

            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("See you around!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                for (int i = 0; i < 100; i++) {
                    if (list[i] == null) {
                        break;
                    }
                    System.out.println((i+1) + ". " + list[i]);
                }
            } else {
                list[curr_index] = input;
                System.out.println("____________________________________________________________");
                System.out.println("added: " + input);
                curr_index++;
            }

            System.out.println("____________________________________________________________");
        }


    }
}
