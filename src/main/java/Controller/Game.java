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

    private List<Player> players;
    private Sack sack;
    private IslandController islandController;
    private CloudController cloudController;
    private BoardsController boardsController;

    private GameModel gameModel;


    /**
     * Wraps all info about the game
     */


    /**
     * Once the lobby is filled, the Game can be initialized
     * @param players The list of players accepted by the lobby.
     */
    public Game(List<Player> players) {
        this.players = players;
        int n = players.size();


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
        try {
            //handleEvent((GameEvent) o);
        } catch (Exception e) {
            //obs.send(e);
        }
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

    /**
     * Handles a request to play an assistant
     * @param event
     * @throws AssistantMissingException //TODO
     * @throws IllegalAssistantException //TODO
     * @throws NotYourTurnException The request is coming from the wrong player
     * @throws InvalidMoveException The request is coming from the right player but in the wrong game phase
     */
    public void handleEvent(PlayAssistantEvent event) throws AssistantMissingException, IllegalAssistantException, NotYourTurnException, InvalidMoveException {
        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //not the right phase
        if (!gameModel.getGamePhase().equals(Phase.PLANNING)) throw new InvalidMoveException("Assistants can only be played in the planning phase");

        //gets reference to the player requesting the move
        Player requestingPlayer = findRequestingPlayer(event.getPlayerId());

        //you have already played an assistant
        if (requestingPlayer.getAssistantInPlay() != null) throw new InvalidMoveException("You already have played an assistant");

        if(event.getPlayedAssistant() > requestingPlayer.getHand().getHand().size() - 1){ throw new AssistantMissingException(); }

        //Checks if someone else as already played that card. If so, it can't be played unless it's the only available
        //card in the player's hand.
        for(Player p : players){
            if(p.getAssistantInPlay().equals(event.getPlayedAssistant()) && requestingPlayer.getHand().getHand().size()>1){
                throw new IllegalAssistantException();
            }
        }

        requestingPlayer.playAssistant(event.getPlayedAssistant());

        if (allPlayersPlayedAssistant()) {
            updatePlayerOrder();
            //set current player
            gameModel.setGamePhase(Phase.ACTION_STUDENTS);
        }
    }

    /**
     * Handles a request of moving a student to the dining room
     * @param event
     * @throws NoSuchStudentsException You don't have the student you want to move
     * @throws NotYourTurnException The request is coming from the wrong player
     * @throws InvalidMoveException The request is coming from the right player but in the wrong game phase
     */
    public void handleEvent(MoveStudentToDiningEvent event) throws NoSuchStudentsException, NotYourTurnException, InvalidMoveException {
        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //not the right phase
        if (!gameModel.getGamePhase().equals(Phase.ACTION_STUDENTS)) throw new InvalidMoveException("You can't move any student now");

        //already moved three students
        if (gameModel.getNumStudentsMoved() >= 3) throw new InvalidMoveException("You can't move any more students");

        boardsController.moveToDiner(event.getPlayerId(), event.getStudentIndex());

        //add one student to the turn info
        gameModel.studentMoved();

        //if the player has moved 3 students the ACTION_STUDENTS phase has ended
        if (gameModel.getNumStudentsMoved() == 3) {
            gameModel.setGamePhase(Phase.ACTION_MOTHERNATURE);
        }
    }


    public void handleEvent(MoveStudentToIslandEvent event) throws InvalidMoveException, NotYourTurnException, NoSuchIslandException, NoSuchStudentsException {
        int islandIndex = event.getIslandID();

        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //not the right phase
        if (!gameModel.getGamePhase().equals(Phase.ACTION_STUDENTS)) throw new InvalidMoveException("You can't move any student now");

        //already moved three students
        if (gameModel.getNumStudentsMoved() >= 3) throw new InvalidMoveException("You can't move any more students");

        //wrong island index
        if (islandController.getIslandsQuantity() <= islandIndex || islandIndex <= 0) throw new NoSuchIslandException();

        try {
            Student removed = boardsController.removeFromEntrance(event.getPlayerId(), event.getStudentBoardIndex());

            islandController.moveStudent(islandIndex, removed);
        } catch (NoSuchStudentsException e) {
            throw e;
        }


        //add one student to the turn info
        gameModel.studentMoved();

        //if the player has moved 3 students the ACTION_STUDENTS phase has ended
        if (gameModel.getNumStudentsMoved() == 3) {
            gameModel.setGamePhase(Phase.ACTION_MOTHERNATURE);
        }
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

        //you already have moved mother nature
        if (gameModel.hasMotherNatureMoved()) throw new InvalidMoveException("You can't move mother nature again");

        //illegal number of steps requested
        int maximumSteps = gameModel.getCurrentPlayer().getAssistantInPlay().getMotherNumber();
        int numberOfSteps = event.getNumberOfSteps();
        if (numberOfSteps > maximumSteps || numberOfSteps <= 0) throw new InvalidMoveException("You must move mother nature a number of steps which is between 0 and the number indicated on the Assistant card you played");

        islandController.moveMother(numberOfSteps);

        //set mother nature moved
        gameModel.motherNatureMoved();

        //once mother nature has moved we get to the cloud phase
        gameModel.setGamePhase(Phase.ACTION_CLOUDS);
    }

    /**
     * Handles a request of students from a cloud by a player
     * @param event
     * @throws NotYourTurnException The request is coming from the wrong player
     * @throws InvalidMoveException The request is coming from the right player but in the wrong game phase
     * @throws NoSuchCloudException The cloud index does not exist
     */
    public void handleEvent(PickStudentsFromCloudEvent event) throws NotYourTurnException, InvalidMoveException, NoSuchCloudException, CloudEmptyException{
        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //wrong game phase
        if (!gameModel.getGamePhase().equals(Phase.ACTION_CLOUDS)) throw new InvalidMoveException("It's not time to take students from a cloud");

        //already picked a cloud
        if (gameModel.isCloudChosen()) throw new InvalidMoveException("You can't pick another cloud");

        //wrong index
        int cloudIndex = event.getCloudIndex();
        if (cloudIndex < 0 || cloudIndex >= cloudController.getNumOfClouds()) throw new NoSuchCloudException();

        try {
            //gets from cloud
            List<Student> fromCloud = cloudController.getFromCloud(cloudIndex);
            //adds the students to the board
            boardsController.fillEntrance(event.getPlayerId(), fromCloud);

            gameModel.cloudChosen();

            //if the player requesting the move was the last one THEN the round ends
            if (players.get(players.size() - 1).getPlayerID() == event.getPlayerId()) {     //the player is the last one if it's the last in the players list
                endRound();
                initNewRound();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //TODO davide!!!!!!!!
    public void handleEvent(BuyPlayCharacterEvent event) throws NotYourTurnException, InvalidMoveException {
        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //not the right phase
        if (!gameModel.getGamePhase().equals(Phase.ACTION_STUDENTS) && !gameModel.getGamePhase().equals(Phase.ACTION_MOTHERNATURE) && !gameModel.getGamePhase().equals(Phase.ACTION_CLOUDS)) throw new InvalidMoveException("Assistants can only be played in the planning phase");
    }

    public void handleEvent(ChooseWizardEvent event) throws NotYourTurnException, InvalidMoveException, WizardAlreadyChosenException{
        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //not the right phase
        if (!gameModel.getGamePhase().equals(Phase.SETUP)) throw new InvalidMoveException("You can't choose a wizard now");

        Player requestingPlayer = findRequestingPlayer(event.getPlayerId());
        //already chosen a wizard
        if (requestingPlayer.getHand() != null) throw new InvalidMoveException("Already have chosen a wizard");

        //checks if wizard has already been chosen
        for (Player p : players) {
            if (p.getHand().getWizardID() == event.getWizardID()) throw new WizardAlreadyChosenException();
        }

        //sets wizard and initializes hand
        requestingPlayer.chooseWizard(event.getWizardID());

        //if all players have chosen a wizard the first round can begin
        if (allWizardsChosen()) {
            gameModel.newRound();
            gameModel.setGamePhase(Phase.PLANNING);
        }
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

    /**
     * Returns the reference to the player with the provided id
     * @param pid The provided id
     * @return The reference to the player with the provided id
     */
    private Player findRequestingPlayer(String pid) {
        for (Player p : players) {
            if (pid == p.getPlayerID()) return p;
        }
        return null;
    }

    /**
     * Tells if all players have chosen a wizard
     * @return TRUE/FALSE
     */
    private boolean allWizardsChosen() {
        for (Player p : players) {
            if (p.getHand() == null) return false;
        }
        return true;
    }

    private boolean allPlayersPlayedAssistant() {
        for (Player p : players) {
            if (p.getAssistantInPlay() == null) return false;
        }
        return true;
    }

    private void initNewRound() {
        //riempie nuvole eccetera
    }

    private void endRound() {
        //resetta
    }
}