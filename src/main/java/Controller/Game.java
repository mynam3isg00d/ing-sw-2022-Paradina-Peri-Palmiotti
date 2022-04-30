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
import Exceptions.*;
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
    public void handleEvent(PlayAssistantEvent event) throws AssistantMissingException, IllegalAssistantException {
        //TODO: I need a way to access who played this move. For now, I'll use a dummy player variable that I'm initializing here
        Player player = new Player("dummyPlayer", 0);
        if(event.getPlayedAssistant() > player.getHand().getHand().size() - 1){ throw new AssistantMissingException(); }

        //Checks if someone else as already played that card. If so, it can't be played unless it's the only available
        //card in the player's hand.
        for(Player p : players){
            if(p.getAssistantInPlay().equals(event.getPlayedAssistant()) && player.getHand().getHand().size()>1){
                throw new IllegalAssistantException();
            }
        }

        player.playAssistant(event.getPlayedAssistant());
    }

    public void handleEvent(MoveStudentToDiningEvent event) throws NoSuchStudentsException {
        //TODO: I need a way to access who played this move. For now, I'll use a dummy player variable that I'm initializing here
        Player player = new Player("dummyPlayer", 0);
        boardsController.moveToDiner(player.getPlayerID(), event.getStudent());
    }


    public void handleEvent(MoveStudentToIslandEvent event) throws InvalidMoveException, NotYourTurnException, NoSuchIslandException {
        int islandIndex = event.getIslandID();

        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //not the right phase
        if (!gameModel.getGamePhase().equals(Phase.ACTION_STUDENTS)) throw new InvalidMoveException("You can't move any student now");

        //wrong island index
        if (islandController.getIslandsQuantity() <= islandIndex || islandIndex <= 0) throw new NoSuchIslandException();

        Student removed = null;
        try {
            removed = boardsController.removeFromEntrance(event.getPlayerId(), event.getStudentBoardIndex());
        } catch (NoSuchStudentsException e) {
            e.printStackTrace();
            return;
        }

        islandController.moveStudent(islandIndex, removed);
    }

    /**
     * Moves mother nature
     * @param event The event generated by the player interacting with the view
     * @throws InvalidMoveException The request is coming from the right player but in the wrong game phase
     * @throws NotYourTurnException The request is coming from the wrong player
     */
    public void handleEvent(MoveMotherNatureEvent event) throws InvalidMoveException, NotYourTurnException {
        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //wrong game phase
        if (!gameModel.getGamePhase().equals(Phase.ACTION_MOTHERNATURE)) throw new InvalidMoveException("Mother Nature cannot be moved now");

        //illegal number of steps requested
        int maximumSteps = gameModel.getCurrentPlayer().getAssistantInPlay().getMotherNumber();
        int numberOfSteps = event.getNumberOfSteps();
        if (numberOfSteps > maximumSteps || numberOfSteps <= 0) throw new InvalidMoveException("You must move mother nature a number of steps which is between 0 and the number indicated on the Assistant card you played");


        islandController.moveMother(numberOfSteps);
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

    public BoardsController getBoardsController() {
        return boardsController;
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