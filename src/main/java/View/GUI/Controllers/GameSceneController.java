package View.GUI.Controllers;

import Model.Board;
import Model.CloudWrapper;
import Model.IslandsWrapper;
import javafx.fxml.FXML;

public class GameSceneController {

    @FXML
    BoardController boardMainController, board1Controller, board2Controller, board3Controller;
    @FXML
    IslandsWrapperController islandsWrapperController;
    @FXML
    CloudWrapperController cloudWrapperController;

    @FXML
    public void initialize() {
        boardMainController.setInteractable(true);
        board1Controller.setInteractable(false); board1Controller.show(false);
        board2Controller.setInteractable(false); board2Controller.show(false);
        board3Controller.setInteractable(false); board3Controller.show(false);
    }

    public void updateMainBoard(Board b) {
        boardMainController.update(b);
    }

    public void updateBoard(Board b) {
        if(board1Controller.getName() != null && board1Controller.getName().equals(b.getPlayerName())) {
            //It's board 1, update
            board1Controller.update(b);
        } else if (board2Controller.getName() != null && board1Controller.getName().equals(b.getPlayerName())) {
            board2Controller.update(b);
        } else if (board3Controller.getName() != null && board3Controller.getName().equals(b.getPlayerName())) {
            board3Controller.update(b);
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
    public void updateIslandsWrapper(IslandsWrapper iw) {islandsWrapperController.update(iw);}
    public void updateCloudWrapper(CloudWrapper cw) {cloudWrapperController.update(cw);}
}
