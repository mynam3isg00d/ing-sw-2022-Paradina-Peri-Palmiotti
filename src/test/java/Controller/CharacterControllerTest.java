package Controller;

import Controller.CharacterEffects.CentaurEffect;
import Controller.CharacterEffects.Strategies.CentaurStrategy;
import Exceptions.InsufficientCoinsException;
import Exceptions.InvalidPlayerInputException;
import Model.CharacterCard;
import Model.Player;
import Model.Shop;
import Model.StudentCard;
import org.junit.jupiter.api.Test;

import javax.naming.event.ObjectChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CharacterControllerTest {

    @Test
    void constructorTest() {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        CharacterController cc = new CharacterController(g, new Integer[]{5, 6, 7});
    }

    @Test
    void clerkEffectTest() throws InvalidPlayerInputException, InsufficientCoinsException {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{0, 0, 0}));
        CharacterController cc = g.getCharacterController();

        List<Object> playerInput = List.of(1, 2);
        cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);

        //use debugger but it works lol
    }

    //No idea how to test this so i trust it works
    @Test
    void heraldEffectTest() throws InvalidPlayerInputException, InsufficientCoinsException {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{2, 2, 2}));
        CharacterController cc = g.getCharacterController();

        List<Object> playerInput = List.of(1);
        assertThrows(InsufficientCoinsException.class, () -> { cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);});
        cc.giveCoins(pl.get(0).getPlayerID(), 9999);
        cc.buyCard(0, pl.get(0).getPlayerID(), playerInput);
    }

    @Test
    void centaurEffectTest() throws InvalidPlayerInputException, InsufficientCoinsException {
        List<Player> pl = getPlayerList(2);
        ExpertGame g = new ExpertGame(pl);
        g.setCharacterController(new CharacterController(g, new Integer[]{5, 5, 5}));
        CharacterController cc = g.getCharacterController();

        assertThrows(InsufficientCoinsException.class, () -> { cc.buyCard(0, pl.get(0).getPlayerID(), null);});
        cc.giveCoins(pl.get(0).getPlayerID(), 9999);
        cc.buyCard(0, pl.get(0).getPlayerID(), null);
        assertTrue(g.getIslandController().getInfluenceStrategy() instanceof CentaurStrategy);
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