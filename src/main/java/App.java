import Model.*;
import View.View;

import java.util.*;

public class App {

    public static void main(String[] args) {
        Game g = new Game("Davide", "Samu");
        View v = new View(g);
        g.connectView(v);

        g.start();
        //g.playRound();
    }
}
