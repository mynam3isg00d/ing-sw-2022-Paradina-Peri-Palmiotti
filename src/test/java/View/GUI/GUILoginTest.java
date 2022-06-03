package View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GUILoginTest extends Application {
    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Eriantys");
        stg = primaryStage;
        //primaryStage.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ipChoice.fxml"));
        primaryStage.setScene(new Scene(root, 1500, 800));
        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }
}
