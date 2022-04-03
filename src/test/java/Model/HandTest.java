package Model;

import Exceptions.AssistantMissingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void handConstructorTest() {
        Hand h = new Hand(4);
        assertNotNull(h.getHand());
        assertEquals(10, h.getHand().size());
    }

    @Test
    void getAssistantTest() {
        Hand h = new Hand(4);
        assertAll( () -> {for(int i=1; i<=10; i++) {assertNotNull(h.getAssistantFromOrderNumber(i));}});
    }

    @Test
    void throwAssistantExceptionTest() throws AssistantMissingException {
        Hand h = new Hand(4);
        for(int i=1; i<=10; i++) h.getAssistantFromOrderNumber(i);
        Exception e = assertThrows(AssistantMissingException.class, () -> h.getAssistantFromOrderNumber(1));
    }

    @Test
    void addAssistantTest() throws AssistantMissingException {
        Hand h = new Hand(4);
        for(int i=1; i<=10; i++) h.getAssistantFromOrderNumber(i);
        h.addAssistant(new Assistant(3));
        assertNotNull(h.getAssistantFromOrderNumber(3));
    }

    @Test
    void isAssistantInHandTest() throws AssistantMissingException {
        Hand h = new Hand(4);
        assertFalse(h.isAssistantInHand(12));
        assertFalse(h.isAssistantInHand(-1));
        for(int i=1; i<=10; i++) {
            assertTrue(h.isAssistantInHand(i));
            h.getAssistantFromOrderNumber(i);
            assertFalse(h.isAssistantInHand(i));
        }
    }
}