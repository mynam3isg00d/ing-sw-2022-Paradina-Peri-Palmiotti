package Controller;

import Controller.CharacterEffects.CentaurEffect;
import Controller.CharacterEffects.Strategies.CentaurStrategy;
import Controller.CharacterEffects.Strategies.DefaultProfessorStrategy;
import Controller.CharacterEffects.Strategies.FarmerStrategy;
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

        assertThrows(InsufficientCoinsException.class, () -> { cc.buyCard(0, pl.get(0).getPlayerID(), new ArrayList<String>());});
        cc.giveCoins(pl.get(0).getPlayerID(), 9999);
        cc.buyCard(0, pl.get(0).getPlayerID(), new ArrayList<String>());
        assertTrue(g.getIslandController().getInfluenceStrategy() instanceof CentaurStrategy);
    }

    @Test
    void bardEffectTest() throws Exception, InvalidNumberOfPlayersException {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{9, 9, 9}));
        CharacterController cc = g.getCharacterController();

        g.boardsController.addToDining(pl.get(0).getPlayerID(), Student.BLUE);
        g.boardsController.addToDining(pl.get(0).getPlayerID(), Student.GREEN);

        List<String> playerInput = List.of("1", "2", "1", "2");
        cc.giveCoins(pl.get(0).getPlayerID(), 9999);
        cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);
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