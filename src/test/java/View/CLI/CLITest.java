package View.CLI;

import Controller.Game;
import Model.*;
import org.fusesource.jansi.AnsiConsole;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

class CLITest {

    /*
    @Test
    void old_displayTest() {

        //--Model---------

        //Players
        List<Player> p = getPlayerList(2);
        for(int i=0; i<p.size(); i++) p.get(i).chooseWizard(i);

        //Board
        Board[] b = new Board[p.size()];
        for(int i=0; i<b.length; i++) b[i] = new Board(8, 7);

        //GameModel
        GameModel g = new GameModel(p.size());
        g.setCurrentPlayer(p.get(0));
        g.setGamePhase(Phase.ACTION_MOTHERNATURE);

        //Sack
        Sack sc = new Sack(120);

        //CloudWrapper
        CloudWrapper cw = new CloudWrapper(4, 3);

        //IslandsWrapper
        IslandsWrapper iw = new IslandsWrapper();

        //Shop
        Shop s = new Shop(p);
        s.initShop(sc);

        //View Elements
        CLIBoard[] cb = new CLIBoard[b.length];
        for(int i=0; i<b.length; i++) cb[i] = new CLIBoard(b[i]);
        CLICloudWrapper ccw = new CLICloudWrapper(cw);
        CLIIslandWrapper ciw = new CLIIslandWrapper(iw);
        CLIShop cs = new CLIShop(s);
        CLISack csc = new CLISack(sc);
        List<CLIPlayer> cp = new ArrayList<>();
        for(Player p0 : p) cp.add(new CLIPlayer(p0));
        CLIGameModel cg = new CLIGameModel(g);

        //Draw
        AnsiConsole.systemInstall();
        System.out.print( ansi().eraseScreen() );
        for(int i=0; i<cb.length; i++) cb[i].displayLines(i * (2+cb[0].getX()), 0);
        cg.displayLines(4 * (2+cb[0].getX()), 0);
        ciw.display(10, cb[0].getY() + 1);
        ccw.display(10 + ciw.getX() + 10, cb[0].getY() + 1);
        cs.displayLines(0, cb[0].getY() + 1 + ciw.getY() + 2);
        for(int i=0; i<cp.size(); i++)
            cp.get(i).displayLines(cs.getX() + 1,
                    cb[0].getY() + 1 + ciw.getY() + 2 + i*(cp.get(0).getY() - 1));
        csc.displayLines(cs.getX() + cp.get(0).getX() + 1, cb[0].getY() + 1 + ciw.getY() + 5);

        System.out.print( ansi().cursor(0,0).cursorDown(cb[0].getY() + 1 + ciw.getY() + 2 + cs.getY() + 1));
        System.out.println("-".repeat(4 * (2+cb[0].getX()) + cg.getX()));

        while(true);
    }
     */

    @Test
    void displayTest() {
        //Players
        List<Player> p = getPlayerList(2);
        for(int i=0; i<p.size(); i++) p.get(i).chooseWizard(i);

        //Board
        Board[] b = new Board[p.size()];
        for(int i=0; i<b.length; i++) b[i] = new Board(8, 7);

        //GameModel
        GameModel g = new GameModel(p.size());
        g.setCurrentPlayer(p.get(0));
        g.setGamePhase(Phase.ACTION_MOTHERNATURE);

        //Sack
        Sack sc = new Sack(120);

        //CloudWrapper
        CloudWrapper cw = new CloudWrapper(4, 3);

        //IslandsWrapper
        IslandsWrapper iw = new IslandsWrapper();

        //Shop
        Shop s = new Shop(p);
        s.initShop(sc);

        CLI c = new CLI(b, g, iw, cw, s, p, sc);
        c.display();
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