//Notes:
/*
Possible implementation for Game constructors:
    Take from App the names of the players and build different constructors like:
    Game(String name1, String name2)
    Game(String name1, String name2, String name3)
    Game(String name1, String name2, String name3, String name4)
    then create the proper number
*/
package Controller;

import Events.GameEvent;
import Model.*;
import View.*;
import java.util.*;

/**
 * Game is the master controller.
 * Has references to IslandController, CloudController and BoardsController that handle respectively Island, Cloud and Boards elements.
 * Handles events coming from the view and calls the right methods on the right controller.
 */
public class Game implements Observer{

    private Model model;
    private View view;

    //GameInfo is an internal class that contains info about the game
    private GameInfo info;

    private List<Player> players;
    private Sack sack;
    private IslandController islandController;
    private CloudController cloudController;
    private BoardsController boardsController;

    /**
     * Wraps all info about the game
     */

    private class GameInfo {
        int roundCount;
        public GameInfo() {
            roundCount = 0;
        }
    }

    /**
     * Once the lobby is filled, the Game can be initialized
     * @param players The list of players accepted by the lobby.
     */
    public Game(List<Player> players) {
        this.players = players;
        int n = players.size();

        //initializes the game information
        info = new GameInfo();

        //generates the Students sack
        sack = new Sack(120);

        //initializes all controllers
        //the controllers will initialize the respective models
        islandController = new IslandController();
        cloudController = new CloudController(n);
        boardsController = new BoardsController(players, sack);

        //islandController requires access to the boards
        islandController.connectBoards(boardsController);


    }

    /**
     * Receives updates from the remoteView and calls handleEvent
     * @param obs
     * @param o The Event coming from the RemoteView
     */
    @Override
    public void update(Observable obs, Object o) {
        handleEvent((GameEvent)o);
    }

    //----------------------------------------------------------------------------------------------------------------
    //handleEvent methods, overload on different event types. Event objects come from a factory of events parsing a json  (are you sure?)
    //handleEvent will call the right methods on the right controller
    //----------------------------------------------------------------------------------------------------------------
    public void handleEvent() {

    }

    public void handleEvent(GameEvent gameEvent) {
        switch (gameEvent.getMove()){
            case A:
                //Check if the assistant played is in the player's hand.
                //Check if the assistant is legal, i.e. has not yet been played by someone else

                //Update model
                break;
            case B:
                //Check if arguments are legal.
                //Check if studentID is available to be moved
                //Move student to dining room

                //Update model moving student to dining room
                break;
            case C:
                //Check if arguments are legal.
                //Check if studentID is available to be moved

                //Update model moving student to island
                break;
            case D:
                //Check if number of steps is legal

                //Update model by relocating mother nature
                break;
            case E:
                //Check if cloudID is legal
                //Check if cloud is not empty

                //Update model by adding the students that are on the cloud to the entrance of the player
                break;
            case F:
                //handle event
                break;
            case G:
                //Check if wizard is available

                //Update model by assigning wizard's deck to player
                break;

        }
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

    public IslandController getIslandController() {
        return islandController;
    }

    /**
     * Update player order will be called whenever, just before the start of the action phase of a turn, all players will have chosen an assistant
     */
    public void updatePlayerOrder() {
        //TODO: doesn't consider same assistant play for now
        ArrayList<Player> temp = new ArrayList<>();
        ArrayList<Player> playersCopy = new ArrayList<>(players);
        while(!playersCopy.isEmpty()) {
            Player minPlayer = playersCopy.get(0);
            int minOrderNumber = 11;
            for(Player p : playersCopy) {
                int t = p.getAssistantInPlay().getOrderNumber();
                if(t < minOrderNumber) {
                    minPlayer = p;
                    minOrderNumber = t;
                }
            }
            playersCopy.remove(minPlayer);
            temp.add(minPlayer);
        }
        players = temp;
    }
}