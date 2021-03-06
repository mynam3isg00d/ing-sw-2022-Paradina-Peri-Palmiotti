package Model;

import Network.JsonFactory;
import Observer.Observable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameModel extends Observable {
    
    private int roundCount;
    private boolean isLastRound;
    private int STUDENTS_PER_TURN;
    private Phase gamePhase;
    private Player currentPlayer;
    private int winnerTeam;
    private TurnInfo turnInfo;
    private final boolean[] pickedWizards = new boolean[]{false, false, false, false};

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
        winnerTeam = -1;

        if (playerNumber == 2 || playerNumber == 4) {
            STUDENTS_PER_TURN = 3;
        } else {
            STUDENTS_PER_TURN = 4;
        }
    }

    /**
     * Sends the class to its observers via a json file
     */
    public void sendGameModel() {
        String s = new JsonFactory().modelToJson(this);
        notify(s);
    }


    //-----------------------------------------------------------------------------------------------------------------
    //      Getters
    //-----------------------------------------------------------------------------------------------------------------

    public boolean isCloudChosen() {
        return turnInfo.cloudChosen;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getSTUDENTS_PER_TURN() {
        return STUDENTS_PER_TURN;
    }

    public Phase getGamePhase() {
        return gamePhase;
    }

    public int getNumStudentsMoved() {
        return turnInfo.numOfStudentsMoved;
    }

    public boolean hasMotherNatureMoved() {
        return turnInfo.motherNatureMoved;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public boolean isLastRound() {
        return isLastRound;
    }

    public int getWinnerTeam() {
        return winnerTeam;
    }

    public boolean[] getPickedWizards() {
        return pickedWizards;
    }

    public void setWinnerTeam(int winnerTeam) {
        this.winnerTeam = winnerTeam;
    }


    //-----------------------------------------------------------------------------------------------------------------
    //      Setters
    //-----------------------------------------------------------------------------------------------------------------

    public void setGamePhase(Phase gamePhase) {
        this.gamePhase = gamePhase;

        sendGameModel();
    }

    public void updatePickedWizard(int wizardID) {
        pickedWizards[wizardID] = true;
    }

    public void setLastRound(boolean lastRound) {
        isLastRound = lastRound;

        sendGameModel();
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

    public void motherNatureMoved() {
        turnInfo.motherNatureMoved = true;
        sendGameModel();
    }


    public void cloudChosen() {
        turnInfo.cloudChosen = true;
        sendGameModel();
    }

    public void newRound() {
        roundCount++;
        sendGameModel();
    }

}
