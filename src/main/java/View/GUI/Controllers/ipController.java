package View.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;


public class ipController {
    @FXML
    private TextField ip;
    public void sendIpAddress(ActionEvent actionEvent) {
        System.out.println(ip.getText());
    }
}
