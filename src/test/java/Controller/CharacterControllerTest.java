package Controller;

import Controller.CharacterEffects.CentaurEffect;
import Controller.CharacterEffects.Strategies.*;
import Events.EventFactory;
import Events.MoveStudentToDiningEvent;
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
        assertEquals(2, g.boardsController.getBoard(pl.get(0).getPlayerID()).getDinings()[Student.RED.getColorId()]) ; /* FALSE */

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
}