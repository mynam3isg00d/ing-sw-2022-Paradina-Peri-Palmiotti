package View.GUI.Controllers;


import Observer.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class GameConfigController {

    ObservableList<String> playersNumberList = FXCollections.observableArrayList("Two", "Three", "Four");
    @FXML
    private TextField name;

    @FXML
    private ChoiceBox playerNumberBox;

    @FXML
    private CheckBox expertBox;
    private boolean expert = false;

    @FXML
    private void initialize() {
        playerNumberBox.setValue("Two");
        playerNumberBox.setItems(playersNumberList);
    }

    public void playButton(ActionEvent actionEvent) {
        System.out.println(name.getText());
        System.out.println(playerNumberBox.getValue());
        System.out.println(expert);
    }

    @FXML
    public void toggleExpert() {
        if (expert) expert = false;
        else expert = true;
    }
}
