package Network;

import Events.EventFactory;
import Events.GameEvent;
import Events.PlayAssistantEvent;
import Model.CloudWrapper;
import Model.Sack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonFactoryTest {

    @Test
    void toJson() {
        GameEvent gameEvent = new PlayAssistantEvent();
        gameEvent.setPlayerId("4");
        gameEvent.parseInput("3");

        String newJson = JsonFactory.eventToJson(gameEvent);

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

    @Test
    void faccioVedereASamuelePeriComeFunzionaJSON() {
        CloudWrapper cw = new CloudWrapper(3, 4);
        try {
            cw.fillCloud(new Sack(120).draw(4), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String jsonString = gson.toJson(cw);

        System.out.println(jsonString);

        CloudWrapper cw1 = gson.fromJson(jsonString, CloudWrapper.class);
        int a = 0;
    }
}