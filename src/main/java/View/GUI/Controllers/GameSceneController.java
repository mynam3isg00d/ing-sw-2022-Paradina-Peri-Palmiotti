package View.GUI.Controllers;

import Model.*;
import Network.Messages.Message;
import View.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class GameSceneController {

    @FXML
    BoardController boardMainController, board1Controller, board2Controller, board3Controller;
    @FXML
    IslandsWrapperController islandsWrapperController;
    @FXML
    CloudWrapperController cloudWrapperController;
    @FXML
    PlayerController playerMainController, player1Controller, player2Controller, player3Controller;
    @FXML
    PickWizardController wizardsController;
    @FXML
    ShopController shopController;

    @FXML
    public void initialize() {
        boardMainController.setInteractable(true);
        playerMainController.setInteractable(true);

        player1Controller.setMouseTransparent(true);
        player2Controller.setMouseTransparent(true);
        player3Controller.setMouseTransparent(true);
        board1Controller.show(false);
        board2Controller.show(false);
        board3Controller.show(false);
    }

    public void updateMainBoard(Board b) {
        boardMainController.update(b);
    }

    public void updateBoard(Board b) {
        if(board1Controller.getName() != null && board1Controller.getName().equals(b.getPlayerName())) {
            //It's board 1, update
            board1Controller.update(b);
            return;
        } else if (board2Controller.getName() != null && board2Controller.getName().equals(b.getPlayerName())) {
            board2Controller.update(b);
            return;
        } else if (board3Controller.getName() != null && board3Controller.getName().equals(b.getPlayerName())) {
            board3Controller.update(b);
            return;
        }

        //Name not found, update the first empty board (new player)
        if(board1Controller.getName() == null) {
            board1Controller.update(b);
            board1Controller.show(true);
            return;
        }

        if(board2Controller.getName() == null) {
            board2Controller.update(b);
            board2Controller.show(true);
            return;
        }

        if(board3Controller.getName() == null) {
            board3Controller.update(b);
            board3Controller.show(true);
        }
    }

    public void updateMainPlayer(Player p) {
        playerMainController.update(p);
    }
    
    public void updatePlayer(Player p) {
        if(player1Controller.getName() != null && player1Controller.getName().equals(p.getName())) {
            //It's player 1, update
            player1Controller.update(p);
            return;
        } else if (player2Controller.getName() != null && player2Controller.getName().equals(p.getName())) {
            player2Controller.update(p);
            return;
        } else if (player3Controller.getName() != null && player3Controller.getName().equals(p.getName())) {
            player3Controller.update(p);
            return;
        }

        //Name not found, update the first empty player (new player)
        if(player1Controller.getName() == null) {
            player1Controller.update(p);
            return;
        }

        if(player2Controller.getName() == null) {
            player2Controller.update(p);
            return;
        }

        if(player3Controller.getName() == null) {
            player3Controller.update(p);
            return;
        }
    }
    public void updateIslandsWrapper(IslandsWrapper iw) {islandsWrapperController.update(iw);}
    public void updateCloudWrapper(CloudWrapper cw) {cloudWrapperController.update(cw);}
    public void updateShop(Shop s) {shopController.update(s);
    }

    public void connectGUI(GUI gui) {
        boardMainController.connectGUI(gui);
        board1Controller.connectGUI(gui);
        board2Controller.connectGUI(gui);
        board3Controller.connectGUI(gui);

        islandsWrapperController.connectGUI(gui);
        cloudWrapperController.connectGUI(gui);
        playerMainController.connectGUI(gui);
        wizardsController.connectGUI(gui);
        shopController.connectGUI(gui);
    }

    public void updateGameModel(GameModel gm) {
        wizardsController.update(gm);
    }

    public void renderError(String error) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, error, ButtonType.OK);
        alert.show();
    }
}
