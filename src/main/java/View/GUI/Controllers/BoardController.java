package View.GUI.Controllers;

import Model.Board;
import Model.Student;
import View.GUI.Nodes.StudentTile;
import View.GUI.Nodes.TowerTile;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class BoardController {
    @FXML
    private GridPane entrance;
    @FXML
    private AnchorPane dinings;
    @FXML
    private FlowPane yellowTable, blueTable, redTable, greenTable, pinkTable;
    @FXML
    private ImageView yellowProf, blueProf, redProf, greenProf, pinkProf;
    @FXML
    private GridPane towers;

    public void update(Board board) {

        //Entrance
        Student[] bentr = board.getEntrance();
        for(int i=0; i<bentr.length; i++) {
            if(bentr[i] != null) entrance.add(new StudentTile(bentr[i].getColorId(), true, i), i%2, i/2);
        }

        //Dinings
        int[] bdins = board.getDinings();

        yellowTable.getChildren().removeAll();
        blueTable.getChildren().removeAll();
        greenTable.getChildren().removeAll();
        redTable.getChildren().removeAll();
        pinkTable.getChildren().removeAll();
            for(int j=0; j<bdins[0]; j++) yellowTable.getChildren().add(new StudentTile(Student.YELLOW.getColorId(), false, -1, 35));
            for(int j=0; j<bdins[1]; j++) blueTable.getChildren().add(new StudentTile(Student.BLUE.getColorId(), false, -1, 35));
            for(int j=0; j<bdins[2]; j++) greenTable.getChildren().add(new StudentTile(Student.GREEN.getColorId(), false, -1, 35));
            for(int j=0; j<bdins[3]; j++) redTable.getChildren().add(new StudentTile(Student.RED.getColorId(), false, -1, 35));
            for(int j=0; j<bdins[4]; j++) pinkTable.getChildren().add(new StudentTile(Student.PINK.getColorId(), false, -1, 35));

        //Professors
        boolean[] bprof = board.getProfessors();
        yellowProf.setVisible(bprof[0]);
        blueProf.setVisible(bprof[1]);
        greenProf.setVisible(bprof[2]);
        redProf.setVisible(bprof[3]);
        pinkProf.setVisible(bprof[4]);

        //Towers
        int btow = board.getTowersNum();
        for(int i=0; i<btow; i++) towers.add(new TowerTile(board.getTeamID(), 70), i%2, i/2);
    }
}
