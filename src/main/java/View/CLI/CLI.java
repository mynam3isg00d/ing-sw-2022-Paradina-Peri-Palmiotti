package View.CLI;

import Model.*;
import org.fusesource.jansi.AnsiConsole;

import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class CLI {

    private CLIBoard[] boards;
    private CLIGameModel gameModel;
    private CLIIslandWrapper islandWrapper;
    private CLICloudWrapper cloudWrapper;
    private CLIShop shop;                       //Maybe expert cli?
    private CLIPlayer[] players;
    private CLISack sack;

    public CLI(Board[] boards,
               GameModel gameModel,
               IslandsWrapper islandsWrapper,
               CloudWrapper cloudWrapper,
               Shop shop,
               List<Player> players,
               Sack sack) {

        this.boards = new CLIBoard[boards.length];
        for(int i=0; i<this.boards.length; i++) this.boards[i] = new CLIBoard(boards[i]);
        this.gameModel = new CLIGameModel(gameModel);
        this.islandWrapper = new CLIIslandWrapper(islandsWrapper);
        this.cloudWrapper = new CLICloudWrapper(cloudWrapper);
        this.shop = new CLIShop(shop);
        this.players = new CLIPlayer[players.size()];
        for(int i=0; i<this.players.length; i++) this.players[i] = new CLIPlayer(players.get(i));
        this.sack = new CLISack(sack);
    }

    //Rough display :p
    public void display() {
        AnsiConsole.systemInstall();
        System.out.print( ansi().eraseScreen() );
        for(int i=0; i<boards.length; i++) boards[i].displayLines(i * (2+boards[0].getX()), 0);
        gameModel.displayLines(4 * (2+boards[0].getX()), 0);
        islandWrapper.display(10, boards[0].getY() + 1);
        cloudWrapper.display(10 + islandWrapper.getX() + 10, boards[0].getY() + 1);
        shop.displayLines(0, boards[0].getY() + 1 + islandWrapper.getY() + 2);
        for(int i=0; i<players.length; i++)
            players[i].displayLines(shop.getX() + 1,
                    boards[0].getY() + 1 + islandWrapper.getY() + 2 + i*(players[0].getY() - 1));
        sack.displayLines(shop.getX() + players[0].getX() + 1, boards[0].getY() + 1 + islandWrapper.getY() + 5);

        System.out.print( ansi().cursor(0,0).cursorDown(boards[0].getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 1));
        System.out.println("-".repeat(4 * (2+boards[0].getX()) + gameModel.getX()));
    }

    //Update methods
    public void updateModel(Board b, int i) {
        //Update the model
        boards[i].updateBoard(b);
        //Overwrite the new model
        boards[i].displayLines(i * (2+boards[0].getX()), 0);
        //Reset cursor position
        System.out.print( ansi().cursor(0,0).cursorDown(boards[0].getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(GameModel g) {
        gameModel.updateGameModel(g);
        gameModel.displayLines(4 * (2+boards[0].getX()), 0);
        System.out.print( ansi().cursor(0,0).cursorDown(boards[0].getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(IslandsWrapper iw) {
        islandWrapper.updateIslandWrapper(iw);
        islandWrapper.display(10, boards[0].getY() + 1);
        System.out.print( ansi().cursor(0,0).cursorDown(boards[0].getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(CloudWrapper cw) {
        cloudWrapper.updateCloudWrapper(cw);
        cloudWrapper.display(10 + islandWrapper.getX() + 10, boards[0].getY() + 1);
        System.out.print( ansi().cursor(0,0).cursorDown(boards[0].getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(Shop s) {
        shop.updateShop(s);
        shop.displayLines(0, boards[0].getY() + 1 + islandWrapper.getY() + 2);
        System.out.print( ansi().cursor(0,0).cursorDown(boards[0].getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(Player p, int i) {
        players[i].updatePlayer(p);
        players[i].displayLines(shop.getX() + 1,
                boards[0].getY() + 1 + islandWrapper.getY() + 2 + i*(players[0].getY() - 1));
        System.out.print( ansi().cursor(0,0).cursorDown(boards[0].getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }

    public void updateModel(Sack s) {
        sack.updateSack(s);
        sack.displayLines(shop.getX() + players[0].getX() + 1, boards[0].getY() + 1 + islandWrapper.getY() + 5);
        System.out.print( ansi().cursor(0,0).cursorDown(boards[0].getY() + 1 + islandWrapper.getY() + 2 + shop.getY() + 2));
    }
}
