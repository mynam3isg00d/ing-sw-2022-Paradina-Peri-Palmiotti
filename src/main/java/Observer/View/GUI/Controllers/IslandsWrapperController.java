package Observer.View.GUI.Controllers;

import Model.IslandsWrapper;
import Observer.View.GUI.GUI;
import javafx.fxml.FXML;

public class IslandsWrapperController {
    @FXML
    private IslandController island0Controller, island1Controller, island2Controller, island3Controller,
                             island4Controller, island5Controller, island6Controller, island7Controller,
                             island8Controller, island9Controller, island10Controller, island11Controller;

    public void update(IslandsWrapper islandsWrapper) {
        int num = islandsWrapper.getIslandLength();
        int mpos = islandsWrapper.getMotherNaturePos();

        //Can't be done with a loop since controllers have to be accessed with [id]Controller

        //island 0
        if (num <= 0) {
            //There is no island!
            island0Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island0Controller.update(islandsWrapper.getIsland(0), 0);
            island0Controller.setDistanceFromMother(mpos, num);
        }

        //island 1
        if (num <= 1) {
            //There is no island!
            island1Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island1Controller.update(islandsWrapper.getIsland(1), 1);
            island1Controller.setDistanceFromMother(mpos, num);
        }

        //island 2
        if (num <= 2) {
            //There is no island!
            island2Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island2Controller.update(islandsWrapper.getIsland(2), 2);
            island2Controller.setDistanceFromMother(mpos, num);
        }

        //island 3
        if (num <= 3) {
            //There is no island!
            island3Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island3Controller.update(islandsWrapper.getIsland(3), 3);
            island3Controller.setDistanceFromMother(mpos, num);
        }

        //island 4
        if (num <= 4) {
            //There is no island!
            island4Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island4Controller.update(islandsWrapper.getIsland(4), 4);
            island4Controller.setDistanceFromMother(mpos, num);
        }

        //island 5
        if (num <= 5) {
            //There is no island!
            island5Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island5Controller.update(islandsWrapper.getIsland(5), 5);
            island5Controller.setDistanceFromMother(mpos, num);
        }

        //island 6
        if (num <= 6) {
            //There is no island!
            island6Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island6Controller.update(islandsWrapper.getIsland(6), 6);
            island6Controller.setDistanceFromMother(mpos, num);
        }

        //island 7
        if (num <= 7) {
            //There is no island!
            island7Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island7Controller.update(islandsWrapper.getIsland(7), 7);
            island7Controller.setDistanceFromMother(mpos, num);
        }

        //island 8
        if (num <= 8) {
            //There is no island!
            island8Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island8Controller.update(islandsWrapper.getIsland(8), 8);
            island8Controller.setDistanceFromMother(mpos, num);
        }

        //island 9
        if (num <= 9) {
            //There is no island!
            island9Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island9Controller.update(islandsWrapper.getIsland(9), 9);
            island9Controller.setDistanceFromMother(mpos, num);
        }

        //island 10
        if (num <= 10) {
            //There is no island!
            island10Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island10Controller.update(islandsWrapper.getIsland(10), 10);
            island10Controller.setDistanceFromMother(mpos, num);
        }

        //island 11
        if (num <= 11) {
            //There is no island!
            island11Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island11Controller.update(islandsWrapper.getIsland(11), 11);
            island11Controller.setDistanceFromMother(mpos, num);
        }

    }

    public void connectGUI(GUI gui) {
        //I generated this in python, if it's worth anything
        island0Controller.connectGUI(gui);
        island1Controller.connectGUI(gui);
        island2Controller.connectGUI(gui);
        island3Controller.connectGUI(gui);
        island4Controller.connectGUI(gui);
        island5Controller.connectGUI(gui);
        island6Controller.connectGUI(gui);
        island7Controller.connectGUI(gui);
        island8Controller.connectGUI(gui);
        island9Controller.connectGUI(gui);
        island10Controller.connectGUI(gui);
        island11Controller.connectGUI(gui);
    }
}
