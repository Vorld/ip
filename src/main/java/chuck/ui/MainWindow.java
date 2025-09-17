package chuck.ui;

import chuck.Chuck;
import chuck.ChuckException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Chuck chuck;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpg"));
    private Image chuckImage = new Image(this.getClass().getResourceAsStream("/images/Chuck.jpg"));

    /**
     * Initialise the GUI
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
    }

    /** Injects the Chuck instance and displays the welcome message */
    public void setChuck(Chuck d) {
        chuck = d;

        //
        dialogContainer.getChildren().add(
            DialogBox.getChuckDialog(chuck.getWelcomeMessage(), chuckImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Chuck's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = chuck.getResponse(input);
        DialogBox responseDialog;

        if (response.startsWith(ChuckException.ERROR_PREFIX)) {
            responseDialog = DialogBox.getErrorDialog(response, chuckImage);
        } else {
            responseDialog = DialogBox.getChuckDialog(response, chuckImage);
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                responseDialog
        );
        userInput.clear();
    }
}
