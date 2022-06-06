package View.GUI;

import Exceptions.FullEntranceException;
import Model.Board;
import Model.IslandsWrapper;
import Model.Student;
import View.GUI.Controllers.BoardController;
import View.GUI.Controllers.IslandsWrapperController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GUIBoardTest extends Application {

    @Test
    public void BoardTest() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/fxml/Board.fxml"));
        Scene scene = new Scene(root.load());
        BoardController boardController = root.getController();

        Board b = new Board("aa","aa", 0, 8, 7);
        try {
            b.addToEntrance(Student.RED);
            b.addToEntrance(Student.BLUE);
            b.addToEntrance(Student.YELLOW);
            b.addToEntrance(Student.PINK);
            b.removeFromEntrance(1);
            for(int i=0; i<10; i++) b.addToDining(Student.YELLOW);
            for(int i=0; i<10; i++) b.addToDining(Student.RED);
            for(int i=0; i<10; i++) b.addToDining(Student.BLUE);
            for(int i=0; i<10; i++) b.addToDining(Student.PINK);
            for(int i=0; i<10; i++) b.addToDining(Student.GREEN);
            b.removeTower(3);
            b.addProfessor(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boardController.update(b);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
