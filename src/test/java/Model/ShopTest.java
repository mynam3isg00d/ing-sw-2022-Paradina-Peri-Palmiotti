package Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    @Test
    void testConstructor() {
        Shop s = new Shop();
        assertEquals(3, s.getShop().length);
        for(int i=0; i<s.getShop().length; i++) {
            assertNotNull(s.getShop()[i]);
            assertTrue(s.getShop()[i] instanceof CharacterCard);
        }
    }

    @Test
    void testCustomCharacterPool() {
        Shop s = new Shop(new Integer[]{0, 2, 5});
        List<CharacterCard> carr = Arrays.asList(s.getShop());
        assertTrue(carr.contains(new CharacterCard(0)));
        assertTrue(carr.contains(new CharacterCard(2)));
        assertTrue(carr.contains(new CharacterCard(5)));
        assertFalse(carr.contains(new CharacterCard(10)));
    }

    @Test
    void testStudentCardPool() {
        Shop s = new Shop(new Integer[]{0, 10, 6});
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
        Shop s = new Shop();
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
}