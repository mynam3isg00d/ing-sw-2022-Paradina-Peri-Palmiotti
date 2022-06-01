package View.GUI.Controllers;

import Model.Island;
import Model.Student;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class IslandController {

    @FXML
    private AnchorPane main;
    @FXML
    private Text size;
    @FXML
    private ImageView tower;
    @FXML
    private ImageView motherNature;
    @FXML
    private Text blueCount, yellowCount, redCount, greenCount, pinkCount;

    public void mouseEntered(MouseEvent mouseEvent) {
        main.setStyle(main.getStyle() + "; -fx-background-color: red");
    }

    public void mouseExited(MouseEvent mouseEvent) {
        main.setStyle(main.getStyle().replace("; -fx-background-color: red", ""));
    }

    public void update(Island island) {

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
}
