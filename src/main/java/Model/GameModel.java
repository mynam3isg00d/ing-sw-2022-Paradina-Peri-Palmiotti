package Model;

public class GameModel {
    private String gamePhase;
    private int roundNumber;
    private Player playerTurn;

    public GameModel() {
        gamePhase = "actionPhase";
        roundNumber = 0;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    public String getGamePhase() {
        return gamePhase;
    }
}
