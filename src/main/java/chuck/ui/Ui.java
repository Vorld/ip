package chuck.ui;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }

    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println("Hey! I'm Chuck, short for Charlie.\n");
        System.out.println("How can I help you?");
        System.out.println("____________________________________________________________");
    }
}