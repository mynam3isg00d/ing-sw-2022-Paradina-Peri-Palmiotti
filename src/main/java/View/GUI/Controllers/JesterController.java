package View.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class JesterController extends CharacterController {
    @FXML
    Pane main;
    @FXML
    ChoiceBox<Integer> cardStudent0;
    @FXML
    ChoiceBox<Integer> cardStudent1;
    @FXML
    ChoiceBox<Integer> cardStudent2;
    @FXML
    ChoiceBox<Integer> entranceStudent0;
    @FXML
    ChoiceBox<Integer> entranceStudent1;
    @FXML
    ChoiceBox<Integer> entranceStudent2;

    @FXML
    ChoiceBox<Integer> island;

    public void initialize() {
        cardStudent0.setItems(cardIdList);
        cardStudent1.setItems(cardIdList);
        cardStudent2.setItems(cardIdList);
        entranceStudent0.setItems(cardIdList);
        entranceStudent1.setItems(cardIdList);
        entranceStudent2.setItems(cardIdList);
    }

    public void sendOk(ActionEvent actionEvent) {
        super.sendOk(actionEvent, main);
        Stage stage = (Stage)((Node)actionEvent.getTarget()).getScene().getWindow();
        stage.close();
    }
}
