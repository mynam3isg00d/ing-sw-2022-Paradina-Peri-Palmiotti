package View.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GrandmaController extends CharacterController {
    @FXML
    Pane main;
    @FXML
    Text num;
    @FXML
    ChoiceBox<Integer> island;

    public void initialize() {
        island.setItems(islandIdList);
    }

    public void sendOk(ActionEvent actionEvent) {
        super.sendOk(actionEvent, main);
        Stage stage = (Stage)((Node)actionEvent.getTarget()).getScene().getWindow();
        stage.close();
    }

    public void updateNum(int n) {
        num.setText("There are "+ n +" tiles available");
    }
}
