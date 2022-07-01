package View.GUI.Controllers;


import Network.Messages.PlayerInfoMessage;
import View.GUI.GUIClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class GameConfigController {

    private GUIClient gui;

    ObservableList<String> playersNumberList = FXCollections.observableArrayList("2", "3", "4");
    @FXML
    private TextField name;

    @FXML
    private ChoiceBox playerNumberBox;

    @FXML
    private CheckBox expertBox;
    private boolean expert = false;

    @FXML
    private void initialize() {
        playerNumberBox.setValue("2");
        playerNumberBox.setItems(playersNumberList);
    }

    public void playButton(ActionEvent actionEvent) {
        //Adapt values
        String toSend_name = name.getText();
        Integer toSend_playerNum = Integer.parseInt((String)playerNumberBox.getValue());
        String toSend_expert = (expert) ? "Y" : "N";

        PlayerInfoMessage toSend = new PlayerInfoMessage(toSend_name, toSend_playerNum, toSend_expert);
        gui.sendPlayerInfoMessage(toSend);
    }

    @FXML
    public void toggleExpert() {
        if (expert) expert = false;
        else expert = true;
    }

    public void connectGUI(GUIClient gui) {this.gui = gui;}
}
