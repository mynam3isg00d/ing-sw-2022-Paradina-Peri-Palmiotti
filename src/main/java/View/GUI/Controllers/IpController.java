package View.GUI.Controllers;

import View.GUI.GUIClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;


public class IpController {
    @FXML
    private TextField ip;
    private GUIClient gui;

    public void sendIpAddress(ActionEvent actionEvent) {
       gui.connectIP(ip.getText());
    }

    public void connectGUI(GUIClient gui) {this.gui = gui;}
}
