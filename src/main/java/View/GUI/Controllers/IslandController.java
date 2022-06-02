package View.GUI.Controllers;

import Model.Island;
import Model.Student;
import View.GUI.Nodes.StudentTile;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class IslandController {

    private int islandId;
    private int distanceFromMother;
    @FXML
    private Pane main;
    @FXML
    private Text size;
    @FXML
    private ImageView tower;
    @FXML
    private ImageView motherNature;
    @FXML
    private Text blueCount, yellowCount, redCount, greenCount, pinkCount;

    public void mouseEntered(MouseEvent mouseEvent) {
        main.setStyle(main.getStyle() + "; -fx-effect: dropshadow(gaussian, red, 3, 2, 0, 0)");
    }

    public void mouseExited(MouseEvent mouseEvent) {
        main.setStyle(main.getStyle().replace("; -fx-effect: dropshadow(gaussian, red, 3, 2, 0, 0)", ""));
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println("move mother nature " + distanceFromMother);
    }

    public void update(Island island, int id) {
        this.islandId = id;
        //Size
        size.setText("SIZE: " + island.getDimension());

        //Tower
        Integer influenceTeam = island.getInfluence();
        if (influenceTeam == null) {
            tower.setVisible(false);
        } else {
            switch (influenceTeam) {
                case 0:
                    tower.setImage(new Image("/graphics/wooden_pieces/white_tower.png"));
                case 1:
                    tower.setImage(new Image("/graphics/wooden_pieces/black_tower.png"));
                case 2:
                    tower.setImage(new Image("/graphics/wooden_pieces/grey_tower.png"));
            }
        }

        //Mother nature
        boolean motherBool = island.isMotherNature();
        motherNature.setVisible(motherBool);

        //Students
        int[] students = island.getStudents();
        yellowCount.setText("x" + students[Student.YELLOW.getColorId()]);
        blueCount.setText("x" + students[Student.BLUE.getColorId()]);
        greenCount.setText("x" + students[Student.GREEN.getColorId()]);
        redCount.setText("x" + students[Student.RED.getColorId()]);
        pinkCount.setText("x" + students[Student.PINK.getColorId()]);
    }

    public void show(boolean b) {
        main.setVisible(b);
    }

    public void dragDropped(DragEvent dragEvent) {
        /* data dropped */
        /* if there is a string data on dragboard, read it and use it */
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.getString().contains("id")) {
            String s = ((Node)dragEvent.getGestureTarget()).getId();
            success = true;
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        int id = ((StudentTile) dragEvent.getGestureSource()).getIndex();
        System.out.println("move student " + id + " to island " + islandId);
        dragEvent.setDropCompleted(success);

        dragEvent.consume();
    }

    public void dragOver(DragEvent dragEvent) {
        /* data is dragged over the target */
        /* accept it only if it is not dragged from the same node
         * and if it has a string data */
        if (dragEvent.getGestureSource().getClass().equals(StudentTile.class) &&
                dragEvent.getDragboard().hasString()) {
            /* allow for both copying and moving, whatever user chooses */
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }

        dragEvent.consume();
    }

    public void setDistanceFromMother(int dis, int num) {
        distanceFromMother = islandId - dis;
        if (distanceFromMother < 0) distanceFromMother = num + distanceFromMother;
    }
}
