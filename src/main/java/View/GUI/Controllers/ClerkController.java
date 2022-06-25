package View.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClerkController extends CharacterController {
    @FXML
    Pane main;

    @FXML
    ChoiceBox<Integer> student;
    @FXML
    ChoiceBox<Integer> island;

    public void initialize() {
        student.setItems(cardIdList);
        island.setItems(islandIdList);
    }

    public void sendOk(ActionEvent actionEvent) {
        super.sendOk(actionEvent, main);
        Stage stage = (Stage)((Node)actionEvent.getTarget()).getScene().getWindow();
        stage.close();
    }
}
