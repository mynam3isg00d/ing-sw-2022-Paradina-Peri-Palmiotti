package Network;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    /*@Test
    void lobby() {
        try {
            //initializes a new server
            Server server = new Server();

            //creates a fake connection and calls the lobby method in order to add it to the lobby
            Connection c1 = new Connection(null, server);
            server.lobby(c1, "Gianni", 2);

            //gets the waitingList map from server
            Map<String, Connection> wl = server.getWaitingList(2);

            //is the newly added connection actually in the waitingList?
            assertEquals(1, wl.size());

            //creates a second and third fake connection
            Connection c2 = new Connection(null, server);
            Connection c3 = new Connection(null, server);

            //calls lobby method: now server should contain two connections on the waitingList for a two-player game
            //server should then initialize a two player game
            server.lobby(c2, "Gigi", 2);
            server.lobby(c3, "Checco", 2);

            //gets the current waitingList and playingList for the 2-player match
            wl = server.getWaitingList(2);
            Map<String, Connection> pl = server.getPlayingList(2);

            //checks if the waitingList has been emptied and the playingList filled
            assertEquals(2, pl.size());
            assertEquals(1, wl.size());

            //checks if the two players in the playing list are the first two connected players
            List<String> playingNames = new ArrayList<>(pl.keySet());
            assertTrue(playingNames.contains("Gigi"));
            assertTrue(playingNames.contains("Gianni"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}