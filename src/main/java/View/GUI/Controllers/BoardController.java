package View.GUI.Controllers;

import Model.Board;
import Model.Student;
import View.GUI.Nodes.TowerTile;
import View.GUI.Nodes.GUILeaf;
import View.GUI.Nodes.StudentTile;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class BoardController extends GUILeaf {

    private boolean interactable = false;
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
                if (dragEvent.getGestureSource().getClass().equals(StudentTile.class) &&
                        dragEvent.getDragboard().hasString()) {
                    dragEvent.acceptTransferModes(TransferMode.ANY);
                }

                dragEvent.consume();
            });

            dinings.setOnDragDropped(dragEvent -> {
                Dragboard db = dragEvent.getDragboard();
                boolean success = db.getString().contains("id");
                int id = ((StudentTile) dragEvent.getGestureSource()).getIndex();

                sendEvent("move student " + id + " to dining");
                dragEvent.setDropCompleted(success);

                dragEvent.consume();}
            );
        }
    }

    public void update(Board board) {

        this.name = board.getPlayerName();
        int prevSize;

        //Entrance
        Student[] bentr = board.getEntrance();

        prevSize = entrance.getChildren().size();
        entrance.getChildren().remove(0, prevSize);
        for(int i=0; i<bentr.length; i++) {
            if(bentr[i] != null) entrance.add(new StudentTile(bentr[i].getColorId(), interactable, interactable, i, 35), i%2, i/2);
        }

        //Dinings
        int[] bdins = board.getDinings();

        prevSize = yellowTable.getChildren().size();
        yellowTable.getChildren().remove(0, prevSize);
        prevSize = blueTable.getChildren().size();
        blueTable.getChildren().remove(0, prevSize);
        prevSize = greenTable.getChildren().size();
        greenTable.getChildren().remove(0, prevSize);
        prevSize = redTable.getChildren().size();
        redTable.getChildren().remove(0, prevSize);
        prevSize = pinkTable.getChildren().size();
        pinkTable.getChildren().remove(0, prevSize);
            for(int j=0; j<bdins[0]; j++) yellowTable.getChildren().add(new StudentTile(Student.YELLOW.getColorId(), false, interactable, -1, 35));
            for(int j=0; j<bdins[1]; j++) blueTable.getChildren().add(new StudentTile(Student.BLUE.getColorId(), false, interactable, -1, 35));
            for(int j=0; j<bdins[2]; j++) greenTable.getChildren().add(new StudentTile(Student.GREEN.getColorId(), false, interactable,-1, 35));
            for(int j=0; j<bdins[3]; j++) redTable.getChildren().add(new StudentTile(Student.RED.getColorId(), false,interactable, -1, 35));
            for(int j=0; j<bdins[4]; j++) pinkTable.getChildren().add(new StudentTile(Student.PINK.getColorId(), false, interactable,-1, 35));

        //Professors
        boolean[] bprof = board.getProfessors();
        yellowProf.setVisible(bprof[0]);
        if (interactable) yellowProf.setRotate(-90);
        blueProf.setVisible(bprof[1]);
        if (interactable) blueProf.setRotate(-90);
        greenProf.setVisible(bprof[2]);
        if (interactable) greenProf.setRotate(-90);
        redProf.setVisible(bprof[3]);
        if (interactable) redProf.setRotate(-90);
        pinkProf.setVisible(bprof[4]);
        if (interactable) pinkProf.setRotate(-90);

        //Towers
        int btow = board.getTowersNum();
        prevSize = towers.getChildren().size();
        towers.getChildren().remove(0, prevSize);
        for(int i=0; i<btow; i++) towers.add(new TowerTile(board.getTeamID(), 70, interactable), i%2, i/2);
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
