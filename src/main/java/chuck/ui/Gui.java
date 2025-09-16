package chuck.ui;

import java.io.IOException;

import chuck.Chuck;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Graphical User Interface class using JavaFX
 */
public class Gui extends Application {
    private Chuck chuck = new Chuck("./data/chuck.txt");


    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Chuck!");
            stage.setScene(scene);
            stage.setMinWidth(450);
            stage.setMinHeight(400);
            stage.setResizable(true);
            fxmlLoader.<MainWindow>getController().setChuck(chuck); // inject the Chuck instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
