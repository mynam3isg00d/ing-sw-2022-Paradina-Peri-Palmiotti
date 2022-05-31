package Model;

import Exceptions.AssistantMissingException;
import Exceptions.IllegalWizardException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void chooseWizardTest() throws IllegalWizardException {
        Player p = new Player("1234", 0);
        assertNotNull(p);
        assertNull(p.getHand());
        p.chooseWizard(0);
        assertNotNull(p.getHand());
    }

    @Test
    public void playAssistantTest() throws AssistantMissingException, IllegalWizardException {
        Player p = new Player("1234", 0);
        p.chooseWizard(0);
        assertThrows(AssistantMissingException.class, () -> {p.playAssistant(15);});
        Assistant a = p.playAssistant(8);
        assertEquals(8, a.getOrderNumber());
        assertEquals(4, a.getMotherNumber());
    }

}