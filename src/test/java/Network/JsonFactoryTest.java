package Network;

import Events.EventFactory;
import Events.GameEvent;
import Events.PlayAssistantEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonFactoryTest {

    @Test
    void toJson() {
        GameEvent gameEvent = new PlayAssistantEvent();
        gameEvent.setPlayerId("4");
        gameEvent.parseInput("3");

        String newJson = JsonFactory.toJson(gameEvent);

        System.out.println(newJson);

        GameEvent received = EventFactory.eventFromJson(newJson);
        switch (received.getEventId()) {
            case "0001": System.out.println("PlayAssistantEvent received");
                PlayAssistantEvent casted = (PlayAssistantEvent) received;
                System.out.println("Adesso posso mostrare il played assistant: " + casted.getPlayedAssistant());
                assertEquals("4", casted.getPlayerId());
                assertEquals(3, casted.getPlayedAssistant());
                break;
        }
    }
}