package chuck;

import chuck.ui.Gui;
import javafx.application.Application;

public class Launcher {
    /**
     * Entry point of the Chuck application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Gui.class, args);
    }
}
