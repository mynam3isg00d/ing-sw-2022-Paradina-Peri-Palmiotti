package View.GUI.Controllers;

import Model.IslandsWrapper;
import javafx.fxml.FXML;

public class IslandsWrapperController {
    @FXML
    IslandController island0Controller, island1Controller, island2Controller, island3Controller;
    @FXML
    IslandController island4Controller, island5Controller, island6Controller, island7Controller;
    @FXML
    IslandController island8Controller, island9Controller, island10Controller, island11Controller;

    public void update(IslandsWrapper islandsWrapper) {
        int num = islandsWrapper.getIslandLength();

        //Can't be done with a loop since controllers have to be accessed with [id]Controller

        //island 0
        if (num <= 0) {
            //There is no island!
            island0Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island0Controller.update(islandsWrapper.getIsland(0));
        }

        //island 1
        if (num <= 1) {
            //There is no island!
            island1Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island1Controller.update(islandsWrapper.getIsland(1));
        }

        //island 2
        if (num <= 2) {
            //There is no island!
            island2Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island2Controller.update(islandsWrapper.getIsland(2));
        }

        //island 3
        if (num <= 3) {
            //There is no island!
            island3Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island3Controller.update(islandsWrapper.getIsland(3));
        }

        //island 4
        if (num <= 4) {
            //There is no island!
            island4Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island4Controller.update(islandsWrapper.getIsland(4));
        }

        //island 5
        if (num <= 5) {
            //There is no island!
            island5Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island5Controller.update(islandsWrapper.getIsland(5));
        }

        //island 6
        if (num <= 6) {
            //There is no island!
            island6Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island6Controller.update(islandsWrapper.getIsland(6));
        }

        //island 7
        if (num <= 7) {
            //There is no island!
            island7Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island7Controller.update(islandsWrapper.getIsland(7));
        }

        //island 8
        if (num <= 8) {
            //There is no island!
            island8Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island8Controller.update(islandsWrapper.getIsland(8));
        }

        //island 9
        if (num <= 9) {
            //There is no island!
            island9Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island9Controller.update(islandsWrapper.getIsland(9));
        }

        //island 10
        if (num <= 10) {
            //There is no island!
            island10Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island10Controller.update(islandsWrapper.getIsland(10));
        }

        //island 11
        if (num <= 11) {
            //There is no island!
            island11Controller.show(false);
        } else {
            //There is an island, show appropriate info
            island11Controller.update(islandsWrapper.getIsland(11));
        }

    }
}
