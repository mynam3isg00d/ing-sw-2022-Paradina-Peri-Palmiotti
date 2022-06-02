package View.GUI.Controllers;

import Model.Board;
import Model.Student;
import View.GUI.Nodes.StudentTile;
import View.GUI.Nodes.TowerTile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class BoardController {

    private boolean interactable;
    private String name = null;
    @FXML
    private Pane main;
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

    private void init() {
        if(interactable) {
            dinings.setOnDragOver(dragEvent -> {
                /* data is dragged over the target */
                /* accept it only if it is not dragged from the same node
                 * and if it has a string data */
                if (dragEvent.getGestureSource().getClass().equals(StudentTile.class) &&
                        dragEvent.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    dragEvent.acceptTransferModes(TransferMode.ANY);
                }

                dragEvent.consume();
            });

            dinings.setOnDragDropped(dragEvent -> {
                Dragboard db = dragEvent.getDragboard();
                boolean success = false;
                if (db.getString().contains("id")) {
                    String s = ((Node)dragEvent.getGestureTarget()).getId();
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                int id = ((StudentTile) dragEvent.getGestureSource()).getIndex();
                System.out.println("move student " + id + " to dining");
                dragEvent.setDropCompleted(success);

                dragEvent.consume();}
            );
        }
    }

    public void update(Board board) {
        this.name = board.getPlayerName();

        //Entrance
        Student[] bentr = board.getEntrance();
        for(int i=0; i<bentr.length; i++) {
            if(bentr[i] != null) entrance.add(new StudentTile(bentr[i].getColorId(), interactable, i), i%2, i/2);
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

    public void setInteractable(boolean b) {
        this.interactable = b;
        init();
    }

    public void show(boolean b) {
        main.setVisible(b);
    }

    public String getName() {
        return name;
    }
}
