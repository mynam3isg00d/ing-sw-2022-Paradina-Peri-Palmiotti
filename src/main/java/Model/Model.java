package Model;
import Controller.Game;
import View.*;

import java.util.Observable;

public class Model extends Observable {
    private View view;
    private Game controller;

    private String gamePhase;
    private int roundNumber;
    private Player playerTurn;

    private IslandsWrapper islandModel;

    public Model() {
        //initializes all the models
        islandModel = new IslandsWrapper();
        //cloudModel = new CloudModel();
        //boards will be initialized by boardsController

        gamePhase = "actionPhase";
        roundNumber = 0;
    }

    public void connectViews(View v) {
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

    public IslandsWrapper getIslandModel() {
        return islandModel;
    }
}
