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

import Events.*;
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

    private GameModel gameModel;


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

        //initializes the game model
        gameModel = new GameModel();

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
        //handleEvent((GameEvent)o);
    }

    /**
     * Adds rv as observer of all model components
     * @param rv The RemoteView
     */
    public void addObserversToModelComponents(RemoteView rv) {
        sack.addObserver(rv);
        islandController.addObserverToModel(rv);
        cloudController.addObserverToModel(rv);
        boardsController.addObserverToModel(rv);
        gameModel.addObserver(rv);
    }

    //----------------------------------------------------------------------------------------------------------------
    //handleEvent methods, overload on different event types. Event objects come from a factory of events parsing a json  (are you sure?)
    //handleEvent will call the right methods on the right controller
    //----------------------------------------------------------------------------------------------------------------
    public void handleEvent(PlayAssistantEvent event) {
        //I need a way to access who played this move. For now I'll use a playerID variable that I'm initializing here
        Player player = null;

    }

    public void handleEvent(MoveStudentToDiningEvent event) {

    }

    public void handleEvent(MoveStudentToIslandEvent event) {

    }

    public void handleEvent(MoveMotherNatureEvent event) {

    }

    public void handleEvent(PickStudentsFromCloudEvent event) {

    }

    public void handleEvent(BuyPlayCharacterEvent event) {

    }

    public void handleEvent(ChooseWizardEvent event) {

    }


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