package View.GUI;

import Model.CloudWrapper;
import Model.IslandsWrapper;
import Model.Sack;
import View.GUI.Controllers.CloudController;
import View.GUI.Controllers.CloudWrapperController;
import View.GUI.Controllers.IslandsWrapperController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GUICloudTest extends Application {

    @Test
    public void CloudTest() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/fxml/CloudWrapper.fxml"));
        Scene scene = new Scene(root.load());
        CloudWrapperController cloudWrapperController = root.getController();

        CloudWrapper cw = new CloudWrapper(4, 3);
        try {
            cw.fillCloud(new Sack(12).draw(3), 0);
            cw.fillCloud(new Sack(12).draw(3), 1);
            cw.fillCloud(new Sack(12).draw(3), 2);
            cw.fillCloud(new Sack(12).draw(3), 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cloudWrapperController.update(cw);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
