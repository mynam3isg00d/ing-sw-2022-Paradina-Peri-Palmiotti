package View.CLI;

import Model.*;
import Network.Messages.Message;
import Util.HelpInterpreter;
import View.UI;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class CLI extends UI {

    private final List<CLIBoard> boards;
    private final CLIGameModel gameModel;
    private final CLIIslandWrapper islandWrapper;
    private final CLICloudWrapper cloudWrapper;
    private final CLIShop shop;
    private final List<CLIPlayer> players;
    private final CLISack sack;

    public CLI() {
        //Dummy values that will be replaced by updates
        this.boards = new ArrayList<>();
        this.gameModel = new CLIGameModel(new GameModel(2));
        this.islandWrapper = new CLIIslandWrapper(new IslandsWrapper());
        this.cloudWrapper = new CLICloudWrapper(new CloudWrapper(3, 3));
        this.shop = new CLIShop();
        this.players = new ArrayList<>();
        this.sack = new CLISack(new Sack(999));
    }

    @Override
    public void init() {
        AnsiConsole.systemInstall();
        System.out.print(ansi().eraseScreen());
    }

    //Rough display :p
    @Override
    public void display() {
        System.out.print( ansi().eraseScreen() );
        for(int i=0; i<boards.size(); i++) boards.get(i).displayLines(i * (2+boards.get(0).getX()), 0);
        gameModel.displayLines(4 * (2+boards.get(0).getX()), 0);
        islandWrapper.display(10, boards.get(0).getY() + 1);
        cloudWrapper.display(10 + islandWrapper.getX() + 10, boards.get(0).getY() + 1);
        shop.displayLines(0, boards.get(0).getY() + 1 + islandWrapper.getY() + 2);
        for(int i=0; i<players.size(); i++)
            players.get(i).displayLines(shop.getX() + 1,
                    boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + i*(players.get(0).getY() - 1));
        sack.displayLines(shop.getX() + players.get(0).getX() + 1, boards.get(0).getY() + 1 + islandWrapper.getY() + 5);

        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 1));
        System.out.println("-".repeat(4 * (2+boards.get(0).getX()) + gameModel.getX()));
    }

    //Update methods
    @Deprecated
    public void updateModel(Board b, int i) {
        //Update the model
        boards.get(i).updateBoard(b);
        //Overwrite the new model
        boards.get(i).displayLines(i * (2+boards.get(0).getX()), 0);
        //Reset cursor position
        resetCursor();
    }

    public void updateModel(Board b) {
        boolean found = false;
        int i;

        //Update the model
        for(i=0; i<boards.size(); i++) {
            CLIBoard clib = boards.get(i);
            String name = clib.getBoard().getPlayerName();
            String name1 = b.getPlayerName();

            //Update the player's board!
            if(name.equals(name1)) {
                clib.updateBoard(b);
                found = true;
                break;
            }
        }
        //Uh, no corresponding board has been found
        //add a new one (if possible), maybe comes from init!
        if (!found) {
            if (boards.size() == 4) {
                System.out.println("no new boards addable ERROR");
                return;
            } else {
                CLIBoard newB = new CLIBoard(b);
                boards.add(newB);
                i = boards.indexOf(newB);
            }
        }

        //Overwrite the new model
        boards.get(i).displayLines(i * (2+boards.get(0).getX()), 0);
        //Reset cursor position
        resetCursor();
    }

    @Override
    public void renderError(Message m) {
        System.out.print(ansi().eraseLine().render(m.toString()));
        resetDeleteCursor();
    }

    public void updateModel(GameModel g) {
        gameModel.updateGameModel(g);
        gameModel.displayLines(4 * (2+boards.get(0).getX()), 0);
        resetCursor();
    }

    public void updateModel(IslandsWrapper iw) {
        islandWrapper.updateIslandWrapper(iw);
        islandWrapper.display(10, boards.get(0).getY() + 1);
        resetCursor();
    }

    public void updateModel(CloudWrapper cw) {
        cloudWrapper.updateCloudWrapper(cw);
        cloudWrapper.display(10 + islandWrapper.getX() + 10, boards.get(0).getY() + 1);
        resetCursor();
    }

    public void updateModel(Shop s) {
        shop.updateShop(s);
        shop.displayLines(0, boards.get(0).getY() + 1 + islandWrapper.getY() + 2);
        resetCursor();
    }

    @Deprecated
    public void updateModel(Player p, int i) {
        players.get(i).updatePlayer(p);
        players.get(i).displayLines(shop.getX() + 1,
                boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + i * (players.get(0).getY() - 1));
        resetCursor();
    }

    public void updateModel(Player p) {
        boolean found = false;
        int i;

        for(i=0; i<players.size(); i++) {
            CLIPlayer clip = players.get(i);
            String name = clip.getPlayer().getPlayerID();
            String name1 = p.getPlayerID();

            //Update the player!
            if(name.equals(name1)) {
                clip.updatePlayer(p);
                found = true;
                break;
            }
        }
        //Uh, no corresponding player has been found
        //add a new one (if possible), maybe comes from init!
        if (!found) {
            if (players.size() == 4) {
                System.out.println("no new players addable ERROR");
                return;
            } else {
                CLIPlayer newP;
                if (p.getPlayerID().equals(this.getPlayerID())) {
                    //This is you!!
                    newP = new CLIPlayer(p, false);
                } else {
                    //this is not you, hide his hand
                    newP = new CLIPlayer(p, true);
                }
                players.add(newP);
                i = players.indexOf(newP);
            }
        }

        players.get(i).displayLines(shop.getX() + 1,
                boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + i*(players.get(0).getY() - 1));
        resetCursor();
    }

    public void updateModel(Sack s) {
        sack.updateSack(s);
        sack.displayLines(shop.getX() + 80, boards.get(0).getY() + 1 + islandWrapper.getY() + 5);
        resetCursor();
    }

    private void resetCursor() {
        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 1)
                .render("-----------------------Enter a command---------------------"));
        for(int i=12; i>=2; i--) {
            System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + i).eraseLine());
        }
    }

    private void resetDeleteCursor() {
        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 1)
                .render("-----------------------Enter a command---------------------"));
        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2).eraseLine());
    }

    public void displayHelp(String line) {
        resetCursor();
        System.out.print(HelpInterpreter.getHelpMessage(line) + "\n");
    }
}
