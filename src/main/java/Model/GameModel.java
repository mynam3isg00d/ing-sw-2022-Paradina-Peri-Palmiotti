package Model;

import java.util.Observable;

public class GameModel extends Observable {
    private int roundCount;

    private Phase gamePhase;
    private Player currentPlayer;

    public GameModel() {
        roundCount = 1;
        currentPlayer = null;
        gamePhase = Phase.SETUP;
    }

    public void setGamePhase(Phase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public Phase getGamePhase() {
        return gamePhase;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
