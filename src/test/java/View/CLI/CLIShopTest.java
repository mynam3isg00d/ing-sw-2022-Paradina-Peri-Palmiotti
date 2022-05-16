package View.CLI;

import Model.Player;
import Model.Sack;
import Model.Shop;
import org.fusesource.jansi.AnsiConsole;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;
import static org.junit.jupiter.api.Assertions.*;

class CLIShopTest {

    @Test
    void displayTest() {

        Shop s = new Shop(getPlayerList(4));
        s.initShop(new Sack(120));
        CLIShop clis = new CLIShop(s);

        AnsiConsole.systemInstall();
        System.out.print( ansi().eraseScreen() );
        clis.displayLines(0, 0);

        //---- steps ---
        s.incrementCost(2);
        clis.updateShop(s);
        clis.displayLines(0, 12);

        while (true);
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