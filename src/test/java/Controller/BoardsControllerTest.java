package Controller;

import Model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardsControllerTest {

    @Test
    public void createTwoBoardsTest() {

    }

    private List<Player> getPlayerList(int n) {
        List<Player> ret = new ArrayList<>();
        String name = "a";
        for(int i=0; i<n; i++) {
            ret.add(new Player(name, i));
            name = name + "a";
        }
        return ret;
    }
}