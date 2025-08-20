public class Chuck {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________\n");
        System.out.println("Hey! I'm Chuck, short for Charlie.\n");
        System.out.println("How can I help you?\n");
        System.out.println("____________________________________________________________\n");

        String input = "";
        while (!input.equals("bye")) {
            Scanner io = new Scanner(System.in);
            input = io.nextLine();
            System.out.println(input);
            System.out.println("____________________________________________________________\n");
        }

        System.out.println("See you around!");
    }
}
