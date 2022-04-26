package Controller;

import Model.Player;
import Model.Sack;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardsControllerTest {

    @Test
    public void createTwoBoardsTest() {
        List<Player> p = getPlayerList(2);
        BoardsController bc = new BoardsController(p, new Sack(120));
    }

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