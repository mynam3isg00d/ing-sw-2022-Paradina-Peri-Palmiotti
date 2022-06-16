package View.CLI;

import Controller.CloudController;
import Controller.ExpertGame;
import Controller.Game;
import Events.BuyPlayCharacterEvent;
import Exceptions.EmptySackException;
import Exceptions.InvalidNumberOfPlayersException;
import Model.*;
import org.fusesource.jansi.AnsiConsole;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

class CLITest {

    /*
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

        try {
            iw.moveMotherNature(3);
            iw.addStudents(4, sc.draw(10));
            for(Board b0 : b) b0.addToEntrance(sc.draw(7));
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.updateModel(sc);
        c.updateModel(iw);
        for(int i=0; i<b.length; i++) c.updateModel(b[i], i);
    }
    */

    @Test
    void displayGame() throws Exception, InvalidNumberOfPlayersException {
        List<Player> p = new ArrayList<>();

        p.add(new Player("Davide", 0));
        p.add(new Player("Samuele", 1));
        p.add(new Player("Cugola", 2));
        //p.add(new Player("SPietro", 1));
        for(Player p0 : p) p0.setPlayerID(p0.getName());

        ExpertGame eg = new ExpertGame(p);

        Board[] b = new Board[p.size()];
        for(int i=0; i<p.size(); i++) b[i] = eg.getBoardsController().getBoard(eg.getPlayers().get(i).getPlayerID());
        GameModel g = eg.getGameModel();
        Sack sc = eg.getSack();
        CloudWrapper cw = eg.getCloudController().getCloudModel();
        IslandsWrapper iw = eg.getIslandController().getIslandModel();
        Shop s = eg.getCharacterController().getShopReference();

        eg.getCloudController().fillClouds(sc);


        GameModel model = new GameModel(3);
        model.setWinnerTeam(0);
        model.setGamePhase(Phase.END);
    }

    private List<Player> getPlayerList(int n) {
        List<Player> ret = new ArrayList<>();
        String name = "a";
        for(int i=0; i<n; i++) {
            Player p;
            if(n == 4) p = new Player(name, i%2);
            else p = new Player(name, i);
            p.setPlayerID(name);
            ret.add(p);
            name = name + "a";
        }
        return ret;
    }
}