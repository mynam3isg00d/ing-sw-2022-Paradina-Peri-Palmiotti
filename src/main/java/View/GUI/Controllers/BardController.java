package View.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BardController extends CharacterController {
    @FXML
    Pane main;
    @FXML
    ChoiceBox<Integer> entranceStudent0;
    @FXML
    ChoiceBox<Integer> entranceStudent1;
    @FXML
    ChoiceBox<Integer> entranceStudent2;
    @FXML
    ChoiceBox<Integer> diningStudent0;
    @FXML
    ChoiceBox<Integer> diningStudent1;
    @FXML
    ChoiceBox<Integer> diningStudent2;


    public void initialize() {
        entranceStudent0.setItems(entranceIdList);
        entranceStudent1.setItems(entranceIdList);
        entranceStudent2.setItems(entranceIdList);
        diningStudent0.setItems(diningIdList);
        diningStudent1.setItems(diningIdList);
        diningStudent2.setItems(diningIdList);
    }

    public void sendOk(ActionEvent actionEvent) {
        super.sendOk(actionEvent, main);
        Stage stage = (Stage)((Node)actionEvent.getTarget()).getScene().getWindow();
        stage.close();
    }
}
