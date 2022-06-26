package Network;

import Controller.BoardsController;
import Controller.Game;
import Controller.IslandController;
import Events.EventFactory;
import Events.GameEvent;
import Events.PlayAssistantEvent;
import Exceptions.InvalidNumberOfPlayersException;
import Model.*;
import Util.ShopDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonFactoryTest {


    @Test
    void addAttributeTest() {
        Board board = new Board("Gigi", "Gigi", 0, 7, 9);

        //board -> json
        JsonFactory fac = new JsonFactory();
        String jsonBoard = fac.modelToJson(board);
        System.out.println(jsonBoard);

        //json -> board
        Gson gson = new GsonBuilder().create();
        JsonObject decodedBoardObject = gson.fromJson(jsonBoard, JsonObject.class);

        //the messageCode will be useful to interpret the message
        System.out.println(decodedBoardObject.get("messageCode").getAsInt());


        //now we can correctly cast the json to a board
        Board decodedBoard = gson.fromJson(jsonBoard, Board.class);

        System.out.println("debug");
    }

    @Test
    void faccioVedereASamuelePeriComeFunzionaJSON() throws InvalidNumberOfPlayersException {

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

    @Test
    void cercoDiCapireComeFunzionaJSON() {

        List<Player> p = new ArrayList<>();
        p.add(new Player("Davide", 0));
        p.add(new Player("Samuele", 1));
        p.add(new Player("Cugola", 2));
        //p.add(new Player("SPietro", 1));
        for(Player p0 : p) p0.setPlayerID(p0.getName());

        Shop s = new Shop(p, new Integer[]{0, 2, 6});
        s.initShop(new Sack(120));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Shop.class, new ShopDeserializer())
                .setPrettyPrinting()
                .create();
        String jsonString = gson.toJson(s);

        System.out.println(jsonString);

        Shop s1 = gson.fromJson(jsonString, Shop.class);
        int a = 0;
    }
}