//Notes:
/*
Possible implementation for Game constructors:
    Take from App the names of the players and build different constructors like:
    Game(String name1, String name2)
    Game(String name1, String name2, String name3)
    Game(String name1, String name2, String name3, String name4)
    then create the proper number
*/
//TODO: Add BoardsHandler implementation
package Controller;

import Model.*;
import View.*;
import java.util.*;

/**
 * Game is the master controller.
 * Has references to IslandController, CloudController and BoardsController that handle respectively Island, Cloud and Boards elements.
 * Handles events coming from the view and calls the right methods on the right controller.
 */
public class Game {
    private Model model;

    //GameInfo is an internal class that contains info about the game
    private GameInfo info;

    private List<Player> players;
    private Sack sack;
    private IslandController islandController;
    private CloudController cloudController;
    private BoardsController boardsController;

    private GameModel gameModel;

    /**
     * Once the lobby is filled, the Game can be initialized
     * @param players The list of players accepted by the lobby.
     */
    public Game(List<Player> players) {
        this.players = players;
        int n = players.size();

        //initializes the game information
        info = new GameInfo();

        //initializes all controllers
        //the controllers will initialize the respective models
        islandController = new IslandController();
        cloudController = new CloudController(n);
        boardsController = new BoardsController(players);

        //islandController requires access to the boards
        islandController.connectBoards(boardsController);

        //generates the Students sack
        sack = new Sack(120);
    }

    //----------------------------------------------------------------------------------------------------------------
    //handleEvent methods, overload on different event types. Event objects come from a factory of events parsing a json
    //handleEvent will call the right methods on the right controller
    //----------------------------------------------------------------------------------------------------------------
    public void handleEvent() {

    }

    public void handleEvent(Object o) {

    }
    //----------------------------------------------------------------------------------------------------------------
    //end events handling
    //----------------------------------------------------------------------------------------------------------------

    public int getRoundCount() {
        return info.roundCount;
    }

    private boolean checkEnd() {
        //BoardsHandler end conditions: a player (or team) has no towers left;
        //IslandHandler end conditions: 3 islands left;
        //Sack end conditions:          the sack is empty, but the game goes on for one last round!
        //Hand end conditions:          if the hand is empty, one last round is played.

        //NOTE: this might need some tinkering, the game should end IMMEDIATELY after the last tower is placed
        //      or IMMEDIATELY after the islands go from 4 to 3

        return false;
    }


    public Sack getSack() {
        return sack;
    }

    public List<Player> getPlayers() {
        return new ArrayList<Player>(players);
    }

    public IslandController getIslandHandler() {
        return islandController;
    }
}

/**
 * Wraps all info about the game
 */
class GameInfo {
    int roundCount;

    public GameInfo() {
        roundCount = 0;
    }
}