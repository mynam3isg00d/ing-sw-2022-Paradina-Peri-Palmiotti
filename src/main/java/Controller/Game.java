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
import Observer.Observer;
import Model.*;
import View.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.*;

/**
 * Game is the master controller.
 * Has references to IslandController, CloudController and BoardsController that handle respectively Island, Cloud and Boards elements.
 * Handles events coming from the view and calls the right methods on the right controller.
 */
public class Game implements Observer {
    protected View view;

    protected List<Player> players;
    protected Sack sack;
    protected IslandController islandController;
    protected CloudController cloudController;
    protected BoardsController boardsController;

    protected GameModel gameModel;

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
        islandController.initIslands();

        cloudController = new CloudController(n);
        boardsController = new BoardsController(players, sack);

        //initializes the game model
        gameModel = new GameModel(players.size());
        gameModel.setCurrentPlayer(players.get(0));  //for the first round the order of game is the order by which the players have connected

        //islandController requires access to the boards
        islandController.connectBoards(boardsController);
    }


    public void sendEntireModel() {
        for (Player p : players) {
            Board b = boardsController.getBoard(p.getPlayerID());
            b.sendBoard();
        }
        cloudController.getCloudModel().sendClouds();
        islandController.getIslandModel().sendIslands();
        sack.sendSack();
        gameModel.sendGameModel();
        for(Player p : players) {
            p.sendPlayer();
        }
    }
    /**
     * Receives updates from the remoteView and calls handleEvent
     * @param o The Event coming from the RemoteView
     */
    @Override
    public void update(Object o) {
        String json = (String) o;
        try {
            jsonToEvent(json);
        } catch (Exception e) {
            e.printStackTrace();
            //obs.send("400" + e);
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

        for (Player p : players) {
            p.addObserver(rv);
        }
    }

    //----------------------------------------------------------------------------------------------------------------
    //handleEvent methods, overload on different event types. Event objects come from a factory of events parsing a json
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

        //Checks if someone else has already played that card. If so, it can't be played unless it's the only available
        //card in the player's hand.
        Assistant selectedAssistant = requestingPlayer.getHand().getAssistantByIndex(event.getPlayedAssistant());
        for(Player p : players){
            if(requestingPlayer.getHand().getHandSize() > 1) {
                if (p.getAssistantInPlay() != null && p.getAssistantInPlay().equals(selectedAssistant)) throw new IllegalAssistantException();
            }
        }

        //plays the selected assistant. Throws an exception if the provided index is not valid
        requestingPlayer.playAssistant(selectedAssistant);

        checkHandEnd();

        //ends the turn (this changes the player order too)
        endTurn(event.getPlayerId());
    }

    /**
     * Handles a request of moving a student to the dining room
     * @param event
     * @throws NoSuchStudentsException You don't have the student you want to move
     * @throws NotYourTurnException The request is coming from the wrong player
     * @throws InvalidMoveException The request is coming from the right player but in the wrong game phase
     */
    public void handleEvent(MoveStudentToDiningEvent event) throws NoSuchStudentsException, NotYourTurnException, InvalidMoveException, FullTableException {
        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //not the right phase
        if (!gameModel.getGamePhase().equals(Phase.ACTION_STUDENTS)) throw new InvalidMoveException("You can't move any student now");

        //already moved three students
        if (gameModel.getNumStudentsMoved() >= gameModel.getSTUDENTS_PER_TURN()) throw new InvalidMoveException("You can't move any more students");

        boardsController.moveFromEntranceToDining(event.getPlayerId(), event.getStudentIndex());
        boardsController.updateProfessors();

        //add one student to the turn info
        gameModel.studentMoved();

        //if the player has moved STUDENTS_PER_TURN students the turn changes
        if (gameModel.getNumStudentsMoved() == gameModel.getSTUDENTS_PER_TURN()) {
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
        if (gameModel.getNumStudentsMoved() >= gameModel.getSTUDENTS_PER_TURN()) throw new InvalidMoveException("You can't move any more students");

        //wrong island index
        if (islandController.getIslandsQuantity() <= islandIndex || islandIndex < 0) throw new NoSuchIslandException();

        try {
            Student removed = boardsController.removeFromEntrance(event.getPlayerId(), event.getStudentBoardIndex());

            islandController.moveStudent(islandIndex, removed);
        } catch (NoSuchStudentsException e) {
            throw e;
        }


        //add one student to the turn info
        gameModel.studentMoved();

        //if the player has moved STUDENTS_PER_TURN students the turn changes
        if (gameModel.getNumStudentsMoved() == gameModel.getSTUDENTS_PER_TURN()) {
            gameModel.setGamePhase(Phase.ACTION_MOTHERNATURE);
        }
    }

    /**
     * Moves mother nature
     * @param event The event generated by the player interacting with the view
     * @throws InvalidMoveException The request is coming from the right player but in the wrong game phase
     * @throws NotYourTurnException The request is coming from the wrong player
     */
    public void handleEvent(MoveMotherNatureEvent event) throws InvalidMoveException, NotYourTurnException, EmptyElementException, FullElementException {
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

        //Check end conditions
        int islandEnd = checkIslandEnd();
        int towerEnd = checkTowerEnd();

        if (islandEnd != -1) {
            gameModel.setWinnerTeam(islandEnd);
            gameModel.setGamePhase(Phase.END);
            return;
        }

        if (towerEnd != -1) {
            gameModel.setWinnerTeam(towerEnd);
            gameModel.setGamePhase(Phase.END);
            return;
        }

        //once mother nature has moved we get to the cloud phase, if it's last round we skip it
        if (gameModel.isLastRound()) {
            endTurn(event.getPlayerId());
        } else {
            gameModel.setGamePhase(Phase.ACTION_CLOUDS);
        }
    }

    /**
     * Handles a request of students from a cloud by a player
     * @param event
     * @throws NotYourTurnException The request is coming from the wrong player
     * @throws InvalidMoveException The request is coming from the right player but in the wrong game phase
     * @throws NoSuchCloudException The cloud index does not exist
     */
    public void handleEvent(PickStudentsFromCloudEvent event) throws NotYourTurnException, InvalidMoveException, NoSuchCloudException, EmptyCloudException, FullEntranceException{
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
            boardsController.addToEntrance(event.getPlayerId(), fromCloud);

            gameModel.cloudChosen();

            endTurn(event.getPlayerId());
        } catch (Exception e) {
            System.out.println("PROBLEMA");
            e.printStackTrace();
            throw e;
        }

    }

    //TODO: Jdoc
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
            if (p.getHand() != null && p.getHand().getWizardID() == event.getWizardID()) throw new WizardAlreadyChosenException();
        }

        //sets wizard and initializes hand
        requestingPlayer.chooseWizard(event.getWizardID());


        endTurn(event.getPlayerId());
        //if all players have chosen a wizard the first round can begin
        if (allWizardsChosen()) {
            gameModel.newRound();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    //      END CONDITIONS
    //-----------------------------------------------------------------------------------------------------------------

    //Returns the teamID of the winning team,
    //                      otherwise returns -1 if there are no winners
    //                      otherwise returns -2 if it's a draw
    private int checkTowerEnd() {
        if (players.size() == 4) {
            int emptyWhite = 0;
            int emptyBlack = 0;
            for(Player p : players) {
                Board b = boardsController.getBoard(p.getPlayerID());
                if (b.getTowersNum() <= 0) {
                    if (p.getTeamID() == 0) emptyWhite++;
                    if (p.getTeamID() == 1) emptyBlack++;
                }
            }
            if (emptyWhite == 2) return 0;
            if (emptyBlack == 2) return 1;
        } else {
            for(Player p : players) {
                Board b = boardsController.getBoard(p.getPlayerID());
                if (b.getTowersNum() <= 0) return p.getTeamID();
            }
        }
        return -1;
    }

    private int checkIslandEnd() {
        if (islandController.getIslandsQuantity() == 3) return getWinningTeam();
        return -1;
    }

    private void checkSackEnd() {
        if (sack.isEmpty()) gameModel.setLastRound(true);
    }

    //This could be handled with the roundCount, 10 rounds is max number of playable rounds
    private void checkHandEnd() {
        for (Player p : players) {
            if(p.getHand().getHandSize() == 0) gameModel.setLastRound(true);
        }
    }

    public int getWinningTeam() {
        //white counters
        int whiteSum = 0;
        int whiteProf = 0;

        //black counters
        int blackSum = 0;
        int blackProf = 0;

        //grey counters
        int greySum = 0;
        int greyProf = 0;

        //Check every board and count #towers and #prof per team
        for(Player p : players) {
            Board b = boardsController.getBoard(p.getPlayerID());
            if (p.getTeamID() == 0) {
                whiteSum += b.getTowersNum();
                whiteProf += b.getProfNum();
            }
            if (p.getTeamID() == 1) {
                blackSum += b.getTowersNum();
                blackProf += b.getProfNum();
            }
            if (p.getTeamID() == 2) {
                greySum += b.getTowersNum();
                greyProf += b.getProfNum();
            }
        }

        //Check towers
        int minTowers = Math.min(whiteSum, Math.min(blackSum, greySum));
        List<Integer> possibleWinners = new ArrayList<>();

        if (whiteSum == minTowers) possibleWinners.add(0);
        if (blackSum == minTowers) possibleWinners.add(1);
        if (greySum == minTowers) possibleWinners.add(2);
        if (possibleWinners.size() == 1) return possibleWinners.get(0);

        //Tower draw!! Check professors
        int minProfessors = Math.min(whiteProf, Math.min(blackProf, greyProf));
        List<Integer> newPossibleWinners = new ArrayList<>();

        if (whiteProf == minProfessors && possibleWinners.contains(0)) newPossibleWinners.add(0);
        if (blackProf == minProfessors && possibleWinners.contains(1)) newPossibleWinners.add(1);
        if (greyProf == minProfessors && possibleWinners.contains(2)) newPossibleWinners.add(2);
        if (newPossibleWinners.size() == 1) return newPossibleWinners.get(0);

        //DRAW!
        return -2;
    }

    //-----------------------------------------------------------------------------------------------------------------
    //      Getters
    //-----------------------------------------------------------------------------------------------------------------

    public Sack getSack() {
        return sack;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public IslandController getIslandController() {
        return islandController;
    }

    public BoardsController getBoardsController() {
        return boardsController;
    }

    public CloudController getCloudController() {
        return cloudController;
    }

    public GameModel getGameModel() {
        return gameModel;
    }


    //-----------------------------------------------------------------------------------------------------------------
    //      Other methods
    //-----------------------------------------------------------------------------------------------------------------

    /**
     * Update player order will be called whenever, just before the start of the action phase of a turn,
     * all players will have chosen an assistant
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
        players = new ArrayList<>(temp);
    }

    /**
     * Returns the reference to the player with the provided id
     * @param pid The provided id
     * @return The reference to the player with the provided id
     */
    private Player findRequestingPlayer(String pid) {
        for (Player p : players) {
            if (pid.equals(p.getPlayerID())) return p;
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

    /**
     * Tells if all players have chosen an assistant
     * @return TRUE/FALSE
     */
    private boolean allPlayersPlayedAssistant() {
        for (Player p : players) {
            if (p.getAssistantInPlay() == null) return false;
        }
        return true;
    }

    /**
     * A player has finished his turn in one of the phases of the game
     * If the player was the last one to move, then the phase can change
     * @param pid The id of the player who has just moved
     */
    private void endTurn(String pid) {
        //If the planning phase just ended, the next player is not the "next" but the first of the new order list
        boolean planningJustEnded = false;

        //if the player requesting the move was the last one THEN the (macro)phase ends
        if (players.get(players.size() - 1).getPlayerID().equals(pid)) {     //the player is the last one if it's the last in the players list
            System.out.println("phase to end");
            if (gameModel.getGamePhase().equals(Phase.PLANNING)) planningJustEnded = true;
            endPhase();
        }

        try {
            //changes current player to the next one
            if (planningJustEnded) {
                gameModel.setCurrentPlayer(players.get(0));
            } else {
                Player nextPlayer = getNextPlayer();
                gameModel.setCurrentPlayer(nextPlayer);
            }

            //resets turn info in player turn
            gameModel.resetTurnInfo();

            if (gameModel.getGamePhase().equals(Phase.ACTION_CLOUDS) ||
                    (gameModel.getGamePhase().equals(Phase.ACTION_MOTHERNATURE) && gameModel.isLastRound())) {
                gameModel.setGamePhase(Phase.ACTION_STUDENTS);
            }
        } catch (Exception e) {
            System.out.println("Who is this player");
            e.printStackTrace();
        }


    }

    /**
     * Returns the next player that should move
     * @return A reference to the player that should move
     * @throws Exception Something went wrong
     */
    private Player getNextPlayer() throws Exception{
        String current = gameModel.getCurrentPlayer().getPlayerID();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerID().equals(current)) return players.get((i+1)% players.size());
        }

        throw new Exception();
    }

    /**
     * endPhase is called whenever all players have finished their turn in one of the 3 phases of a game
     * Phases change in this order: SETUP->PLANNING->ACTION->PLANNING->ACTION->...
     */
    private void endPhase() {
        if (gameModel.getGamePhase().equals(Phase.SETUP)) {

            //fills the clouds
            try {
                cloudController.fillClouds(sack);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //sets the phase to planning. Game is now waiting for players to play assistant cards
            gameModel.setGamePhase(Phase.PLANNING);
        } else if (gameModel.getGamePhase().equals(Phase.PLANNING)) {
            //all players have played an assistant -> the player order is updated
            updatePlayerOrder();

            //phase changes to action. Game is now waiting for student-type events
            gameModel.setGamePhase(Phase.ACTION_STUDENTS);
        } else if (gameModel.getGamePhase().equals(Phase.ACTION_CLOUDS) ||
                (gameModel.getGamePhase().equals(Phase.ACTION_MOTHERNATURE) && gameModel.isLastRound())) {
            initNewRound();
        }
    }

    private void initNewRound() {
        if(gameModel.isLastRound()) {
            gameModel.setWinnerTeam(getWinningTeam());
            gameModel.setGamePhase(Phase.END);
            return;
        }

        //phase is now planning phase
        gameModel.setGamePhase(Phase.PLANNING);

        //clear players' assistant
        for(Player p : players) {
            p.clearAssistant();
        }

        //refill clouds
        try {
            if (sack.getSackSize() < (cloudController.getNumOfClouds() * cloudController.getCloudModel().getCloudSize())) {
                gameModel.setLastRound(true);
            } else {
                cloudController.fillClouds(sack);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //round count is updated
        gameModel.newRound();
    }

    public void jsonToEvent(String json) throws Exception {
        Gson b = new GsonBuilder().serializeNulls().create();

        JsonObject messageAsJsonObject = b.fromJson(json, JsonObject.class);
        String code = messageAsJsonObject.get("eventId").getAsString();

        switch (code) {
            case "0000":
                handleEvent(b.fromJson(json, ChooseWizardEvent.class));
                break;
            case "0001":
                handleEvent(b.fromJson(json, PlayAssistantEvent.class));
                break;
            case "0002":
                handleEvent(b.fromJson(json, MoveStudentToDiningEvent.class));
                break;
            case "0003":
                handleEvent(b.fromJson(json, MoveStudentToIslandEvent.class));
                break;
            case "0004":
                handleEvent(b.fromJson(json, MoveMotherNatureEvent.class));
                break;
            case "0005":
                handleEvent(b.fromJson(json, PickStudentsFromCloudEvent.class));
                break;
                /*
            case "0006":
                handleEvent(b.fromJson(json, BuyPlayCharacterEvent.class));
                break;
                 */
        }
    }
}