package Observer.View.GUI.Controllers;

import Model.CloudWrapper;
import Observer.View.GUI.GUI;
import javafx.fxml.FXML;

public class CloudWrapperController {

    @FXML
    private CloudController cloud0Controller, cloud1Controller, cloud2Controller, cloud3Controller;

    public void update(CloudWrapper cw) {
        int n = cw.getNumOfClouds();

        if (n <= 0) {
            cloud0Controller.show(false);
        } else {
            cloud0Controller.update(cw.getCloud(0), 0);
        }

        if (n <= 1) {
            cloud1Controller.show(false);
        } else {
            cloud1Controller.update(cw.getCloud(1), 1);
        }

        if (n <= 2) {
            cloud2Controller.show(false);
        } else {
            cloud2Controller.update(cw.getCloud(2), 2);
        }

        if (n <= 3) {
            cloud3Controller.show(false);
        } else {
            cloud3Controller.update(cw.getCloud(3), 3);
        }
    }

    public void connectGUI(GUI gui) {
        cloud0Controller.connectGUI(gui);
        cloud1Controller.connectGUI(gui);
        cloud2Controller.connectGUI(gui);
        cloud3Controller.connectGUI(gui);
    }
}
