package View.GUI;

import Model.*;
import View.GUI.Controllers.GameSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class GUIGameSceneTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/fxml/GameScene.fxml"));
        Scene scene = new Scene(root.load());
        GameSceneController gameSceneController = root.getController();

        //IslandWrapper
        IslandsWrapper iw = new IslandsWrapper();
        iw.initIslands();
        iw.moveMotherNature(4);


        //Main Board
        Board b = new Board("aa", "aa", 0, 8, 7);
        try {
            b.addToEntrance(new Sack(120).draw(7));
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

        //More boards
        Board b1 = new Board("b", "b", 1, 8, 7);
        b1.addToEntrance(new Sack(120).draw(7));
        Board b2 = new Board("bb","bb", 1, 8, 7);
        b2.addToEntrance(new Sack(120).draw(7));
        Board b3 = new Board("bbb","bbb", 1, 8, 7);
        b3.addToEntrance(new Sack(120).draw(7));

        //CloudWrapper
        CloudWrapper cw = new CloudWrapper(4, 3);
        try {
            cw.fillCloud(new Sack(12).draw(3), 0);
            cw.fillCloud(new Sack(12).draw(3), 1);
            cw.fillCloud(new Sack(12).draw(3), 2);
            cw.fillCloud(new Sack(12).draw(3), 3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Player (for now 1)
        Player p = new Player("ccc");
        p.chooseWizard(0);

        gameSceneController.updateCloudWrapper(cw);
        gameSceneController.updateMainBoard(b);
        gameSceneController.updateIslandsWrapper(iw);
        gameSceneController.updateBoard(b1);
        gameSceneController.updateBoard(b2);
        gameSceneController.updateBoard(b3);
        gameSceneController.updateMainPlayer(p);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
