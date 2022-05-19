package Model;

import Network.JsonFactory;
import Observer.Observable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameModel extends Observable {
    
    private int roundCount;
    private boolean isLastRound;
    private final int STUDENTS_PER_TURN;
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

    public GameModel(int playerNumber) {
        //round 0 is the setup phase
        roundCount = 0;
        isLastRound = false;

        currentPlayer = null;
        gamePhase = Phase.SETUP;
        turnInfo = new TurnInfo();

        if (playerNumber == 2 || playerNumber == 4) {
            STUDENTS_PER_TURN = 3;
        } else {
            STUDENTS_PER_TURN = 4;
        }
    }

    public void sendGameModel() {
        String s = new JsonFactory().modelToJson(this);
        notify(s);
    }

    public void setGamePhase(Phase gamePhase) {
        this.gamePhase = gamePhase;

        sendGameModel();
    }
    public Phase getGamePhase() {
        return gamePhase;
    }
    public int getSTUDENTS_PER_TURN() {
        return STUDENTS_PER_TURN;
    }
    public void setLastRound(boolean lastRound) {
        isLastRound = lastRound;

        sendGameModel();
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(Player newCurrentPlayer) {
        currentPlayer = newCurrentPlayer;
        sendGameModel();
    }

    public void resetTurnInfo() {
        turnInfo.numOfStudentsMoved = 0;
        turnInfo.motherNatureMoved = false;
        turnInfo.cloudChosen = false;

        sendGameModel();
    }

    public void studentMoved() {
        turnInfo.numOfStudentsMoved++;
        sendGameModel();
    }

    public int getNumStudentsMoved() {
        return turnInfo.numOfStudentsMoved;
    }

    public void motherNatureMoved() {
        turnInfo.motherNatureMoved = true;
        sendGameModel();
    }

    public boolean hasMotherNatureMoved() {
        return turnInfo.motherNatureMoved;
    }

    public void cloudChosen() {
        turnInfo.cloudChosen = true;
        sendGameModel();
    }

    public boolean isCloudChosen() {
        return turnInfo.cloudChosen;
    }

    public void newRound() {
        roundCount++;
        sendGameModel();
    }

    public int getRoundCount() {
        return roundCount;
    }

    public boolean isLastRound() {
        return isLastRound;
    }
}
