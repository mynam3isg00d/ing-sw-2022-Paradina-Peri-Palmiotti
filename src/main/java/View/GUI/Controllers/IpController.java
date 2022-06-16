package View.GUI.Controllers;

import View.GUI.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;


public class IpController {
    @FXML
    private TextField ip;
    private GUI gui;

    public void sendIpAddress(ActionEvent actionEvent) {
       gui.connectIP(ip.getText());
    }

    public void connectGUI(GUI gui) {this.gui = gui;}
}
