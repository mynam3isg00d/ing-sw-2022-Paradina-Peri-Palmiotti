package View.CLI;

import Model.*;
import View.UI;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class CLI extends UI {

    private List<CLIBoard> boards;
    private CLIGameModel gameModel;
    private CLIIslandWrapper islandWrapper;
    private CLICloudWrapper cloudWrapper;
    private CLIShop shop;                       //Maybe expert cli?
    private List<CLIPlayer> players;
    private CLISack sack;

    @Deprecated
    public CLI(Board[] boards,
               GameModel gameModel,
               IslandsWrapper islandsWrapper,
               CloudWrapper cloudWrapper,
               Shop shop,
               List<Player> players,
               Sack sack) {

        this.boards = new ArrayList<>();
        for (Board board : boards) this.boards.add(new CLIBoard(board));
        this.gameModel = new CLIGameModel(gameModel);
        this.islandWrapper = new CLIIslandWrapper(islandsWrapper);
        this.cloudWrapper = new CLICloudWrapper(cloudWrapper);
        this.shop = new CLIShop(shop);
        this.players = new ArrayList<>();
        for (Player player : players) this.players.add(new CLIPlayer(player));
        this.sack = new CLISack(sack);
    }

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

    //Rough display :p
    @Override
    public void display() {
        AnsiConsole.systemInstall();
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
        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(Board b) {
        int i;

        //Update the model
        for(CLIBoard clib : boards) {
            String name = clib.getBoard().getPlayerName();
            String name1 = b.getPlayerName();

            //Update the player's board!
            if(name.equals(name1)) {
                clib.updateBoard(b);
            }
        }
        //Uh, no corresponding board has been found
        //add a new one (if possible), maybe comes from init!
        if(boards.size() == 4) {
            System.out.println("no new boards addable ERROR");
            return;
        } else {
            CLIBoard newB = new CLIBoard(b);
            boards.add(newB);
            i = boards.indexOf(newB);
        }

        //Overwrite the new model
        boards.get(i).displayLines(i * (2+boards.get(0).getX()), 0);
        //Reset cursor position
        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(GameModel g) {
        gameModel.updateGameModel(g);
        gameModel.displayLines(4 * (2+boards.get(0).getX()), 0);
        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(IslandsWrapper iw) {
        islandWrapper.updateIslandWrapper(iw);
        islandWrapper.display(10, boards.get(0).getY() + 1);
        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(CloudWrapper cw) {
        cloudWrapper.updateCloudWrapper(cw);
        cloudWrapper.display(10 + islandWrapper.getX() + 10, boards.get(0).getY() + 1);
        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(Shop s) {
        shop.updateShop(s);
        shop.displayLines(0, boards.get(0).getY() + 1 + islandWrapper.getY() + 2);
        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    @Deprecated
    public void updateModel(Player p, int i) {
        players.get(i).updatePlayer(p);
        players.get(i).displayLines(shop.getX() + 1,
                boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + i*(players.get(0).getY() - 1));
        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(Player p) {
        int i;

        for(CLIPlayer clip : players) {
            String name = clip.getPlayer().getName();
            String name1 = p.getPlayerID();

            //Update the player!
            if(name.equals(name1)) {
                clip.updatePlayer(p);
            }
        }
        //Uh, no corresponding player has been found
        //add a new one (if possible), maybe comes from init!
        if(players.size() == 4) {
            System.out.println("no new players addable ERROR");
            return;
        } else {
            CLIPlayer newP = new CLIPlayer(p);
            players.add(newP);
            i = players.indexOf(newP);
        }

        players.get(i).displayLines(shop.getX() + 1,
                boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + i*(players.get(0).getY() - 1));
        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(Sack s) {
        sack.updateSack(s);
        sack.displayLines(shop.getX() + 80, boards.get(0).getY() + 1 + islandWrapper.getY() + 5);
        System.out.print( ansi().cursor(0,0).cursorDown(boards.get(0).getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }
}
