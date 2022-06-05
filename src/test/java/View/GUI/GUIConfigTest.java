package View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GUIConfigTest extends Application {
    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Eriantys");
        stg = primaryStage;
        //primaryStage.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/GameConfig.fxml"));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

}
