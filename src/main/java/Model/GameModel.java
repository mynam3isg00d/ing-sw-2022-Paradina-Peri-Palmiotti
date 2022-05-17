package Model;

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

        //----Copy constructor??-----
        private TurnInfo(int numOfStudentsMoved, boolean motherNatureMoved, boolean cloudChosen) {
            this.numOfStudentsMoved = numOfStudentsMoved;
            this.motherNatureMoved = motherNatureMoved;
            this.cloudChosen = cloudChosen;
        }

        public int getNumOfStudentsMoved() {
            return numOfStudentsMoved;
        }

        public boolean getMotherNatureMoved() {
            return motherNatureMoved;
        }

        public boolean getCloudChosen() {
            return cloudChosen;
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

    //-------Copy constructor----------
    public GameModel(int roundCount, boolean isLastRound, int STUDENTS_PER_TURN, Phase gamePhase, Player currentPlayer, TurnInfo turnInfo) {
        this.roundCount = roundCount;
        this.isLastRound = isLastRound;
        this.STUDENTS_PER_TURN = STUDENTS_PER_TURN;
        this.gamePhase = gamePhase;
        this.currentPlayer = currentPlayer;
        this.turnInfo = turnInfo;
    }

    public void sendGameModel() {
        Gson b = new GsonBuilder().create();
        notify(b.toJson(getCopy()));
    }

    public void setGamePhase(Phase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public Phase getGamePhase() {
        return gamePhase;
    }

    public int getSTUDENTS_PER_TURN() {
        return STUDENTS_PER_TURN;
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

    private GameModel getCopy() {
        return new GameModel(roundCount, isLastRound, STUDENTS_PER_TURN, gamePhase, currentPlayer, new TurnInfo());
    }
}
