package Controller;

import Model.CharacterCard;
import Model.Player;
import Model.Shop;
import Model.StudentCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CharacterControllerTest {

    Random r = new Random();

    @Test
    void constructor() {
        ExpertGame eg = new ExpertGame(getPlayerList(2));
        CharacterController cc = new CharacterController(eg);

        assertNotNull(cc.getGameReference());
        assertNotNull(cc.getShopReference());

        Shop s = cc.getShopReference();
        for(int i=0; i<3; i++) {
            assertTrue(s.getShop()[i] instanceof CharacterCard);
        }
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