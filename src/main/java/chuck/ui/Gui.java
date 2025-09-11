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
    private Chuck chuck = new Chuck();


    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setChuck(chuck); // inject the Chuck instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
