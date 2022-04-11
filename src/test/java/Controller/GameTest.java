package Controller;

import Model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void gameInfoTest() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Jay-Z", 1));

        Game g = new Game(players);
        System.out.println(g.getRoundCount());
    }
}