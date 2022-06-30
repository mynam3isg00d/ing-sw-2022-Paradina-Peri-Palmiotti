package Controller;

import Controller.CharacterEffects.*;
import Controller.CharacterEffects.Strategies.*;
import Events.*;
import Exceptions.EmptyElementException;
import Exceptions.InsufficientCoinsException;
import Exceptions.InvalidNumberOfPlayersException;
import Exceptions.InvalidPlayerInputException;
import Model.*;
import org.junit.jupiter.api.Test;

import javax.naming.event.ObjectChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CharacterControllerTest {

    @Test
    void constructorTest() throws InvalidNumberOfPlayersException {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        CharacterController cc = new CharacterController(g, new Integer[]{5, 6, 7});
    }

    @Test
    void clerkEffectTest() throws Exception, InvalidNumberOfPlayersException {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{0, 0, 0}));
        CharacterController cc = g.getCharacterController();

        List<String> playerInput = List.of("1", "2");
        cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);

        //use debugger but it works lol
    }

    @Test
    void knightEffectTest() throws Exception, InvalidNumberOfPlayersException {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{7, 7, 7}));
        CharacterController cc = g.getCharacterController();

        //assigns red professor to p1
        g.boardsController.setProfessor("R", pl.get(1));

        //puts two red students on island 0
        g.islandController.moveStudent(0, Student.RED);
        g.islandController.moveStudent(0, Student.RED);

        //for now the most influent team is p1's team (p0 -> 0, p1 -> 2)
        int mostInfluentTeam = g.islandController.getInfluenceStrategy().calcInfluence(0, g.islandController.getIslandModel(), g.boardsController);
        assertEquals(pl.get(1).getTeamID(), mostInfluentTeam);

        List<String> playerInput = List.of();
        cc.giveCoins(pl.get(0).getPlayerID(), 9999);
        cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);

        //now should result in a tie (p0 -> 0+2, p1 -> 2)
         mostInfluentTeam = g.islandController.getInfluenceStrategy().calcInfluence(0, g.islandController.getIslandModel(), g.boardsController);
        assertEquals(-1, mostInfluentTeam);
    }

    //No idea how to test this so i trust it works
    @Test
    void heraldEffectTest() throws Exception, InvalidNumberOfPlayersException {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{2, 2, 2}));
        CharacterController cc = g.getCharacterController();

        List<String> playerInput = List.of("1");
        assertThrows(InsufficientCoinsException.class, () -> { cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);});
        cc.giveCoins(pl.get(0).getPlayerID(), 9999);
        cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);
    }
    @Test
    void thiefEffectTest() throws Exception, InvalidNumberOfPlayersException {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{11, 11, 11}));
        CharacterController cc = g.getCharacterController();

        BoardsController bc = g.getBoardsController();

        //adds 6 red students to p0's board
        for (int i = 0; i < 6; i++) {
            bc.addToDining(pl.get(0).getPlayerID(), Student.RED);
        }

        //adds 2 red students to p1's board
        for (int i = 0; i < 2; i++) {
            bc.addToDining(pl.get(1).getPlayerID(), Student.RED);
        }

        //checks the students have actually been added to the right boards
        assertEquals(6, bc.getBoard(pl.get(0).getPlayerID()).getDinings()[3]);
        assertEquals(2, bc.getBoard(pl.get(1).getPlayerID()).getDinings()[3]);

        //p0 buys thief character providing "red" as the selected color
        List<String> playerInput = List.of("3");
        assertThrows(InsufficientCoinsException.class, () -> { cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);});
        cc.giveCoins(pl.get(0).getPlayerID(), 9999);
        assertDoesNotThrow( () -> {
            cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);
        });

        //3 students have been removed both from p0's and p1's board
        assertEquals(3, bc.getBoard(pl.get(0).getPlayerID()).getDinings()[3]);
        assertEquals(0, bc.getBoard(pl.get(1).getPlayerID()).getDinings()[3]);
    }

    @Test
    void centaurEffectTest() throws Exception, InvalidNumberOfPlayersException {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{5, 5, 5}));
        CharacterController cc = g.getCharacterController();

        //puts one blue student on island 0
        g.islandController.moveStudent(0, Student.BLUE);

        //assigns blue professor to p1
        g.boardsController.setProfessor("B", pl.get(1));
        //sets the influence on island 0 to p0's team
        g.islandController.getIslandModel().setInfluence(0, pl.get(0).getTeamID());

        //calculates influence on island 0 - since the tower does count, should return -1 it's a tie
        assertTrue(g.getIslandController().getInfluenceStrategy() instanceof DefaultInfluenceStrategy);
        assertEquals(-1, g.islandController.getInfluenceStrategy().calcInfluence(0, g.islandController.getIslandModel(), g.boardsController));

        //p0 plays the centaur
        assertThrows(InsufficientCoinsException.class, () -> { cc.buyCard(0, pl.get(0).getPlayerID(), new ArrayList<String>());});
        cc.giveCoins(pl.get(0).getPlayerID(), 9999);
        cc.buyCard(0, pl.get(0).getPlayerID(), new ArrayList<String>());
        assertTrue(g.getIslandController().getInfluenceStrategy() instanceof CentaurStrategy);

        //calculates influence on island 0 - since the tower doesn't count, should return p1's team id
        assertEquals(pl.get(1).getTeamID(), g.islandController.getInfluenceStrategy().calcInfluence(0, g.islandController.getIslandModel(), g.boardsController));
    }

    @Test
    void centaurStrategyTest() throws InvalidNumberOfPlayersException{
        List<Player> pl = getPlayerList(3);
        Game g = new Game(pl);

        g.islandController.setInfluenceStrategy(new CentaurStrategy());

        //no students on island 0, so it should return -1
        assertEquals(-1, g.islandController.getInfluenceStrategy().calcInfluence(0, g.islandController.getIslandModel(), g.boardsController));
    }

    @Test
    void MushroomEffectTest() throws Exception, InvalidNumberOfPlayersException {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{8, 8, 8}));
        CharacterController cc = g.getCharacterController();

        //puts one blue student on island 0
        g.islandController.moveStudent(0, Student.BLUE);

        //assigns blue professor to p1
        g.boardsController.setProfessor("B", pl.get(1));

        //calculates influence on island 0 - since every color caount, should return p1's team id
        assertTrue(g.getIslandController().getInfluenceStrategy() instanceof DefaultInfluenceStrategy);
        assertEquals(pl.get(1).getTeamID(), g.islandController.getInfluenceStrategy().calcInfluence(0, g.islandController.getIslandModel(), g.boardsController));

        //p0 plays mushroom
        assertThrows(InsufficientCoinsException.class, () -> { cc.buyCard(0, pl.get(0).getPlayerID(), new ArrayList<String>());});
        cc.giveCoins(pl.get(0).getPlayerID(), 9999);

        List<String> playerInput = new ArrayList<String>();
        playerInput.add("1");
        cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);
        assertTrue(g.getIslandController().getInfluenceStrategy() instanceof MushroomStrategy);

        //calculates influence on island 0 - since blue students don't count should return -1
        assertEquals(-1, g.islandController.getInfluenceStrategy().calcInfluence(0, g.islandController.getIslandModel(), g.boardsController));
    }


    @Test
    void bardEffectTest() throws Exception, InvalidNumberOfPlayersException {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{9, 9, 9}));
        CharacterController cc = g.getCharacterController();

        //puts a green and a blue student in p1's dining
        g.boardsController.addToDining(pl.get(0).getPlayerID(), Student.BLUE);
        g.boardsController.addToDining(pl.get(0).getPlayerID(), Student.GREEN);

        //makes room for two red students in p1's entrance
        g.boardsController.removeFromEntrance(pl.get(0).getPlayerID(), 0);
        g.boardsController.removeFromEntrance(pl.get(0).getPlayerID(), 1);
        List<Student> toAdd = new ArrayList<>();
        toAdd.add(Student.RED);
        toAdd.add(Student.RED);
        g.boardsController.addToEntrance(pl.get(0).getPlayerID(), toAdd);

        //checks the green and blue student actually are in the dinings
        assertEquals(1, g.boardsController.getBoard(pl.get(0).getPlayerID()).getDinings()[Student.BLUE.getColorId()]) ;
        assertEquals(1, g.boardsController.getBoard(pl.get(0).getPlayerID()).getDinings()[Student.GREEN.getColorId()]) ;
        assertEquals(0, g.boardsController.getBoard(pl.get(0).getPlayerID()).getDinings()[Student.RED.getColorId()]) ;

        List<String> playerInput = List.of("0", "1", "1", "2");
        cc.giveCoins(pl.get(0).getPlayerID(), 9999);
        cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);

        //red dining should now have two students
        assertEquals(0, g.boardsController.getBoard(pl.get(0).getPlayerID()).getDinings()[Student.GREEN.getColorId()]) ;
        assertEquals(0, g.boardsController.getBoard(pl.get(0).getPlayerID()).getDinings()[Student.BLUE.getColorId()]) ;
        assertEquals(2, g.boardsController.getBoard(pl.get(0).getPlayerID()).getDinings()[Student.RED.getColorId()]) ; // FALSE

    }


    @Test
    void FarmerEffectTest() throws Exception {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{1, 1, 1}));
        CharacterController cc = g.getCharacterController();

        //before any character being played, the professor strategy is the default one
        assertTrue(g.boardsController.getProfessorStrategy() instanceof DefaultProfessorStrategy);

        //assigning the red professor to player 0
        g.boardsController.setProfessor("R", pl.get(0));
        //puts one red student in player 0's dining
        g.boardsController.addToDining(pl.get(0).getPlayerID(), Student.RED);
        //professor red is now owned by player 0
        assertEquals(pl.get(0), g.boardsController.getProfessorOwner(3));

        //player 1 activates the farmer effect
        List<String> playerInput = List.of();
        cc.giveCoins(pl.get(1).getPlayerID(), 90);
        cc.buyCard(0, pl.get(1).getPlayerID(), playerInput);

        //now boardscontroller has set a farmer strategy
        assertTrue(g.boardsController.getProfessorStrategy() instanceof FarmerStrategy);

        //puts one red student in player 1's dining
        g.boardsController.addToDining(pl.get(1).getPlayerID(), Student.RED);

        //updates professors
        g.boardsController.updateProfessors();

        //red professor should be owned by player 1, despite the same number of red students in the dining room
        assertEquals(pl.get(1), g.boardsController.getProfessorOwner(3));

        //resets to default
        g.boardsController.setProfessorStrategy(new DefaultProfessorStrategy());
        assertTrue(g.boardsController.getProfessorStrategy() instanceof DefaultProfessorStrategy);

    }

    @Test
    void GrandmaEffectTest() throws Exception {
        //Warning: the methods take into account that there will be only 1 grandma card, if any. This test may be buggy
        //         since the shop is filled with 3 grandmas

        ExpertGame g = doPlanningPhase();

        //p2 (the player in turn) moves 4 students, in order to get to the phase in which to move mother nature
        MoveStudentToDiningEvent e1 = new MoveStudentToDiningEvent();
        e1.setPlayerId("id2");
        e1.parseInput("0");
        g.handleEvent(e1);

        e1.parseInput("1");
        g.handleEvent(e1);

        e1.parseInput("2");
        g.handleEvent(e1);

        e1.parseInput("3");
        g.handleEvent(e1);

        //we in move mother nature phase
        assertEquals(Phase.ACTION_MOTHERNATURE , g.gameModel.getGamePhase());

        //puts grandma in the shop
        g.setCharacterController(new CharacterController(g, new Integer[]{4, 4, 4}));
        CharacterController cc = g.getCharacterController();

        assertEquals(4, ((NoTileCard) cc.getShopReference().getShop()[0]).getNoTile());

        //p2 plays grandma
        List<String> playerInput = List.of("1");
        cc.giveCoins("id2", 9999);
        cc.buyCard(0, "id2", playerInput);

        //island 1 has a no entry tile
        assertTrue(g.getIslandController().getIslandModel().isNoEntry(1));
        assertEquals(3, ((NoTileCard) cc.getShopReference().getShop()[0]).getNoTile());

        //now motherNature moves
        MoveMotherNatureEvent event = new MoveMotherNatureEvent("id2", 1);
        g.handleEvent(event);

        //..and now it doesn't! no entry back on the card
        assertFalse(g.getIslandController().getIslandModel().isNoEntry(1));
        assertEquals(4, ((NoTileCard) cc.getShopReference().getShop()[0]).getNoTile());
    }

    @Test
    void GrandmaEffectTest2() throws Exception {

        //This second test focuses on emptying the no entry card

        //Warning: the methods take into account that there will be only 1 grandma card, if any. This test may be buggy
        //         since the shop is filled with 3 grandmas

        ExpertGame g = doPlanningPhase();

        //p2 (the player in turn) moves 4 students, in order to get to the phase in which to move mother nature
        MoveStudentToDiningEvent e1 = new MoveStudentToDiningEvent();
        e1.setPlayerId("id2");
        e1.parseInput("0");
        g.handleEvent(e1);

        e1.parseInput("1");
        g.handleEvent(e1);

        e1.parseInput("2");
        g.handleEvent(e1);

        e1.parseInput("3");
        g.handleEvent(e1);

        //we in move mother nature phase
        assertEquals(Phase.ACTION_MOTHERNATURE , g.gameModel.getGamePhase());

        //puts grandma in the shop
        g.setCharacterController(new CharacterController(g, new Integer[]{4, 4, 4}));
        CharacterController cc = g.getCharacterController();

        assertEquals(4, ((NoTileCard) cc.getShopReference().getShop()[0]).getNoTile());

        //---begin mass buy---
        //p2 plays grandma
        cc.giveCoins("id2", 9);
        cc.buyCard(0, "id2", List.of("1"));
        //island 1 has a no entry tile
        assertTrue(g.getIslandController().getIslandModel().isNoEntry(1));
        assertEquals(3, ((NoTileCard) cc.getShopReference().getShop()[0]).getNoTile());

        //p2 plays grandma
        cc.giveCoins("id2", 9);
        cc.buyCard(0, "id2", List.of("2"));
        //island 2 has a no entry tile
        assertTrue(g.getIslandController().getIslandModel().isNoEntry(2));
        assertEquals(2, ((NoTileCard) cc.getShopReference().getShop()[0]).getNoTile());

        //p2 plays grandma
        cc.giveCoins("id2", 9);
        cc.buyCard(0, "id2", List.of("3"));
        //island 2 has a no entry tile
        assertTrue(g.getIslandController().getIslandModel().isNoEntry(3));
        assertEquals(1, ((NoTileCard) cc.getShopReference().getShop()[0]).getNoTile());

        //p2 plays grandma
        cc.giveCoins("id2", 9);
        cc.buyCard(0, "id2", List.of("4"));
        //island 2 has a no entry tile
        assertTrue(g.getIslandController().getIslandModel().isNoEntry(4));
        assertEquals(0, ((NoTileCard) cc.getShopReference().getShop()[0]).getNoTile());

        //p2 plays grandma
        cc.giveCoins("id2", 9);
        assertThrows(EmptyElementException.class, () -> {cc.buyCard(0, "id2", List.of("5"));});

        //-----

        //now motherNature moves
        MoveMotherNatureEvent event = new MoveMotherNatureEvent("id2", 1);
        g.handleEvent(event);

        //no entry back on the card
        assertFalse(g.getIslandController().getIslandModel().isNoEntry(1));
        assertEquals(1, ((NoTileCard) cc.getShopReference().getShop()[0]).getNoTile());
    }

    @Test
    void PrincessEffectTest() throws Exception {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{10, 10, 10}));
        CharacterController cc = g.getCharacterController();

        //player 1 activates the princess effect, in order to add the student 0 on the card to his dining
        List<String> playerInput = List.of("0");
        cc.giveCoins(pl.get(1).getPlayerID(), 90);

        cc.buyCard(0, pl.get(1).getPlayerID(), playerInput);

        //one student has been added to one of p1's dinings
        assertEquals(1, countStudents(g.boardsController.getBoard(pl.get(1).getPlayerID()).getDinings()));
    }

    @Test
    void JesterEffectTest() throws Exception {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{6, 6, 6}));
        CharacterController cc = g.getCharacterController();


        //player 0 activates the jester effect - wants to swap the first three students in his entrance with the three on the card
        List<String> playerInput = List.of("0", "1", "2", "0", "1", "2");
        cc.giveCoins(pl.get(0).getPlayerID(), 90);

        assertDoesNotThrow( () -> {
            cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);
        });

    }
    private List<Player> getPlayerList(int n) {
        List<Player> ret = new ArrayList<>();
        String name = "a";
        for(int i=0; i<n; i++) {
            Player p = new Player(name, i);
            p.setPlayerID(name);
            ret.add(p);
            name = name + "a";
        }
        return ret;
    }

    private int countStudents(int[] s) {
        return s[0] + s[1] + s[2] + s[3] +s[4];
    }

    public ExpertGame doPlanningPhase() throws Exception{
        ExpertGame game = doSetupPhase();

        List<Player> initialPlayers = game.getPlayers();


        //p1 wants to play the 3rd assistant in his hand
        PlayAssistantEvent ev1 = new PlayAssistantEvent();
        ev1.setPlayerId("id1");
        ev1.parseInput("3");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(ev1);
        });


        //p2 wants to play the 1st assistant in his hand (no one has played that one yet)
        PlayAssistantEvent ev3 = new PlayAssistantEvent();
        ev3.setPlayerId("id2");
        ev3.parseInput("0");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(ev3);
        });

        //p3 wants to play the 10th assistant in his hand (no one has played that one yet)
        PlayAssistantEvent ev4 = new PlayAssistantEvent();
        ev4.setPlayerId("id3");
        ev4.parseInput("9");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(ev4);
        });

        //all players have played an assistant:
        //- the player order should have been updated to p2 -> p1 -> p3
        //- we should be in action phase
        //- p2 should be the next player to play

        return game;
    }

    public ExpertGame doSetupPhase() throws Exception{
        //init a game with 3 players
        List<Player> players = new ArrayList<>();
        Player p1 = new Player("id1", "1", 0);
        Player p2 = new Player("id2", "2", 1);
        Player p3 = new Player("id3", "3", 2);
        players.add(p1);
        players.add(p2);
        players.add(p3);

        ExpertGame game = new ExpertGame(players);

        ChooseWizardEvent wizardEvent1 = new ChooseWizardEvent();
        wizardEvent1.setPlayerId("id1");
        wizardEvent1.parseInput("0");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(wizardEvent1);
        });

        //creates an event: p2 wants to choose wizard 3
        ChooseWizardEvent wizardEvent3 = new ChooseWizardEvent();
        wizardEvent3.setPlayerId("id2");
        wizardEvent3.parseInput("3");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(wizardEvent3);
        });

        //creates an event: p3 wants to choose wizard 2
        ChooseWizardEvent wizardEvent4 = new ChooseWizardEvent();
        wizardEvent4.setPlayerId("id3");
        wizardEvent4.parseInput("2");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(wizardEvent4);
        });

        return game;
    }
}