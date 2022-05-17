package Network;

import Controller.BoardsController;
import Controller.Game;
import Controller.IslandController;
import Events.EventFactory;
import Events.GameEvent;
import Events.PlayAssistantEvent;
import Model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

        List<Player> p = new ArrayList<>();
        p.add(new Player("Davide", 0));
        p.add(new Player("Samuele", 1));
        p.add(new Player("Cugola", 2));
        //p.add(new Player("SPietro", 1));
        for(Player p0 : p) p0.setPlayerID(p0.getName());

        Game g = new Game(p);


        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String jsonString = gson.toJson( g.getIslandController().getIslandModel() );

        System.out.println(jsonString);

        CloudWrapper cw1 = gson.fromJson(jsonString, CloudWrapper.class);
        int a = 0;
    }
}