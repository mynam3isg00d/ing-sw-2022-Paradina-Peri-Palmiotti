package Model;

import java.util.Observable;

public class GameModel extends Observable {
    private int roundCount;

    private boolean isLastRound;

    private Phase gamePhase;
    private Player currentPlayer;

    private TurnInfo turnInfo;
    private class TurnInfo {
        private int numOfStudentsMoved;
        private boolean motherNatureMoved;
        private boolean cloudChosen;

        private TurnInfo() {
            numOfStudentsMoved = 0;
            motherNatureMoved = false;
            cloudChosen = false;
        }
    }

    public GameModel() {
        //round 0 is the setup phase
        roundCount = 0;
        isLastRound = false;

        currentPlayer = null;
        gamePhase = Phase.SETUP;
        turnInfo = new TurnInfo();
    }

    public void setGamePhase(Phase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public Phase getGamePhase() {
        return gamePhase;
    }

    public void setLastRound(boolean lastRound) {
        isLastRound = lastRound;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player newCurrentPlayer) {
        currentPlayer = newCurrentPlayer;
    }

    public void resetTurnInfo() {
        turnInfo.numOfStudentsMoved = 0;
        turnInfo.motherNatureMoved = false;
        turnInfo.cloudChosen = false;
    }

    public void studentMoved() {
        turnInfo.numOfStudentsMoved++;
    }

    public int getNumStudentsMoved() {
        return turnInfo.numOfStudentsMoved;
    }

    public void motherNatureMoved() {
        turnInfo.motherNatureMoved = true;
    }

    public boolean hasMotherNatureMoved() {
        return turnInfo.motherNatureMoved;
    }

    public void cloudChosen() {
        turnInfo.cloudChosen = true;
    }

    public boolean isCloudChosen() {
        return turnInfo.cloudChosen;
    }

    public void newRound() {
        roundCount++;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public boolean isLastRound() {
        return isLastRound;
    }
}
