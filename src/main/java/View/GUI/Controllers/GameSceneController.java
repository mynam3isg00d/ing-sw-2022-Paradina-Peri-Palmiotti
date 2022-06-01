package View.GUI.Controllers;

import Model.Board;
import javafx.fxml.FXML;

public class GameSceneController {

    @FXML
    BoardController boardMainController;

    public void updateBoard(Board b) {
        boardMainController.update(b);
    }
}
