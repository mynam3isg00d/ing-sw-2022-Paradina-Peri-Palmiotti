package Model;

import java.util.Observable;

public class GameModel extends Observable {
    private int roundCount;

    private Player currentPlayer;

    public GameModel() {
        roundCount = 1;
        currentPlayer = null;
    }


}
