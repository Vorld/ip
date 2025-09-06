package chuck.ui;

import java.util.Scanner;

/**
 * Handles user interface operations for input/output.
 * Manages reading user commands and displaying messages with consistent formatting.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Initializes UI with a Scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**+
     * Reads and returns the next line of user input.
     *
     * @return the user input as a string
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a message to user with borders.
     *
     * @param message the message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays welcome message when application starts.
     */
    public void showWelcome() {
        this.showLine();
        System.out.println("Hey! I'm Chuck, short for Charlie.\n");
        System.out.println("How can I help you?");
        this.showLine();
    }

    /**
     * Displays a divider line.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays an error message to user.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println(message);
    }
}
