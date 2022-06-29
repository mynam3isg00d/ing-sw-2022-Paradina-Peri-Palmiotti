package View.GUI.Controllers;

import Model.Island;
import Model.Student;
import View.GUI.Nodes.GUILeaf;
import View.GUI.Nodes.StudentTile;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class IslandController extends GUILeaf {

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
    @FXML
    private ImageView noentry;

    public void mouseEntered(MouseEvent mouseEvent) {
        main.setStyle(main.getStyle() + "; -fx-effect: dropshadow(gaussian, red, 3, 2, 0, 0)");
    }

    public void mouseExited(MouseEvent mouseEvent) {
        main.setStyle(main.getStyle().replace("; -fx-effect: dropshadow(gaussian, red, 3, 2, 0, 0)", ""));
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        sendEvent("move mother nature " + distanceFromMother);
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
            tower.setVisible(true);
            switch (influenceTeam) {
                case 0:
                    tower.setImage(new Image("/graphics/wooden_pieces/white_tower.png"));
                    break;
                case 1:
                    tower.setImage(new Image("/graphics/wooden_pieces/black_tower.png"));
                    break;
                case 2:
                    tower.setImage(new Image("/graphics/wooden_pieces/grey_tower.png"));
                    break;
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

        //NoEntry
        noentry.setVisible(island.isNoEntry());
    }

    public void show(boolean b) {
        main.setVisible(b);
    }

    public void dragDropped(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        boolean success = db.getString().contains("id");
        int id = ((StudentTile) dragEvent.getGestureSource()).getIndex();

        sendEvent("move student " + id + " to island " + islandId);
        dragEvent.setDropCompleted(success);

        dragEvent.consume();
    }

    public void dragOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource().getClass().equals(StudentTile.class) &&
                dragEvent.getDragboard().hasString()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }

        dragEvent.consume();
    }

    public void setDistanceFromMother(int dis, int num) {
        distanceFromMother = islandId - dis;
        if (distanceFromMother < 0) distanceFromMother = num + distanceFromMother;
    }
}
