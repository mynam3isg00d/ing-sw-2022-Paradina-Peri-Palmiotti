package Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    @Test
    void testConstructor() {
       for(int n=2; n<=4; n++) {
           List<Player> players = getPlayerList(n);
           Shop s = new Shop(players);
           s.initShop(new Sack(120));
           assertEquals(3, s.getShop().length);
           for(int i=0; i<s.getShop().length; i++) {
               assertNotNull(s.getShop()[i]);
               assertTrue(s.getShop()[i] instanceof CharacterCard);
           }
           for(int i=0; i<2; i++) {
               assertEquals(1, s.getPlayerCoins(players.get(i).getPlayerID()));
           }
       }
    }

    /*
    @Test
    void testCustomCharacterPool() {
        for(int n=2; n<=4; n++) {
            List<Player> players = getPlayerList(n);
            Sack sack = new Sack(120);
            Shop s = new Shop(players, new Integer[]{0, 2, 5});
            s.initShop(sack);
            List<CharacterCard> carr = Arrays.asList(s.getShop());
            assertTrue(carr.contains(new CharacterCard(0)));
            assertTrue(carr.contains(new CharacterCard(2)));
            assertTrue(carr.contains(new CharacterCard(5)));
            assertFalse(carr.contains(new CharacterCard(10)));
            assertEquals(116, sack.getSackSize());
        }
    }

    @Test
    void testStudentCardPool() {
        Shop s = new Shop(getPlayerList(2), new Integer[]{0, 10, 6});
        s.initShop(new Sack(120));
        for(int i=0; i<s.getShop().length; i++) {
            CharacterCard cc = s.getShop()[i];
            assertTrue(cc instanceof StudentCard);
        }
    }

    @Test
    void testBuyCard() {
        for(int i=0; i<3; i++) {
            testBuyIndex(i);
        }
    }

    private void testBuyIndex(int i) {
        Shop s = new Shop(getPlayerList(2));
        CharacterCard[] carr = s.getShop();
        int oldCost = carr[i].getCost();
        assertFalse(carr[i].isIncremented());

        s.incrementCost(i);
        assertTrue(carr[i].isIncremented());
        assertEquals(oldCost + 1, carr[i].getCost());

        oldCost = carr[i].getCost();
        s.incrementCost(i);
        assertTrue(carr[i].isIncremented());
        assertEquals(oldCost, carr[i].getCost());
    }

    @Test
    void testCoinMap() {
        List<Player> players = getPlayerList(3);
        Shop s = new Shop(players);
        for(int i=0; i<3; i++) {
            assertEquals(1, s.getPlayerCoins(players.get(i).getPlayerID()));
        }

        s.addCoins(players.get(0).getPlayerID(), 1);
        s.addCoins(players.get(1).getPlayerID(), 1);

        assertEquals(2, s.getPlayerCoins(players.get(0).getPlayerID()));
        assertEquals(2, s.getPlayerCoins(players.get(1).getPlayerID()));
        assertEquals(1, s.getPlayerCoins(players.get(2).getPlayerID()));
    }
    */
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