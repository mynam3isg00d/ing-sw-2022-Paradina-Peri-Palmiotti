package View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIWizardTest extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/fxml/Wizards.fxml"));
        Scene scene = new Scene(root.load());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
