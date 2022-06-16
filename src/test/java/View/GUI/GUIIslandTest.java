package View.GUI;

import Model.IslandsWrapper;
import Observer.View.GUI.Controllers.IslandsWrapperController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class GUIIslandTest extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/fxml/IslandsWrapper.fxml"));
        Scene scene = new Scene(root.load());
        IslandsWrapperController islandsWrapperController = root.getController();

        IslandsWrapper iw = new IslandsWrapper();
        iw.mergeIslands(3, 3);
        islandsWrapperController.update(iw);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
