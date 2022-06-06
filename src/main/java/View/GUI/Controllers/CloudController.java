package View.GUI.Controllers;

import Model.Cloud;
import Model.Student;
import View.GUI.Nodes.GUILeaf;
import View.GUI.Nodes.StudentTile;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;

public class CloudController extends GUILeaf {

    private int cloudId;
    @FXML
    private GridPane cloudGrid;
    @FXML
    private Pane main;

    public void mouseEntered(MouseEvent mouseEvent) {
        main.setStyle(main.getStyle() + "; -fx-effect: dropshadow(gaussian, cyan, 3, 2, 0, 0)");
    }

    public void mouseExited(MouseEvent mouseEvent) {
        main.setStyle(main.getStyle().replace("; -fx-effect: dropshadow(gaussian, cyan, 3, 2, 0, 0)", ""));
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        sendEvent("pick cloud " + cloudId);
    }

    public void update(Cloud cloud, int id) {
        this.cloudId = id;
        List<Student> cstud = cloud.peek();

        //Update grid
        int prevSize = cloudGrid.getChildren().size();
        cloudGrid.getChildren().remove(0, prevSize);
        for(int i=0; i<cstud.size(); i++) {
            cloudGrid.add(new StudentTile(cstud.get(i).getColorId(), false, -1, 35), i%2, i/2);
        }
    }

    public void show(boolean b) {main.setVisible(b);}
}
