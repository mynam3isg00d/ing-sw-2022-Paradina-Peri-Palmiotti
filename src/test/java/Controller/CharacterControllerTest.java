package Controller;

import Controller.CharacterEffects.CentaurEffect;
import Controller.CharacterEffects.Strategies.CentaurStrategy;
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