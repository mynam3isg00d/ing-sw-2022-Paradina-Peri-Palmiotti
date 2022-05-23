package View.CLI;

import Model.GameModel;
import Model.Phase;

import java.util.ArrayList;

import static org.fusesource.jansi.Ansi.ansi;

public class CLIGameModel extends CLIElement {

    private GameModel gameModel;

    public CLIGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
        lines = new ArrayList<>();

        updateLines();
    }

    public void updateGameModel(GameModel g) {
        this.gameModel = g;
        updateLines();
    }

    @Override
    public void displayLines(int x0, int y0) {
        super.displayLines(x0, y0);
        String pl = getCurrPlayer(gameModel.getGamePhase());
        String ph = getPhaseMessage(gameModel.getGamePhase());

        System.out.print(ansi().cursor(0,0).cursorRight(x0 + 2).cursorDown(y0 + 4).render(pl));
        System.out.print(ansi().cursorDown(1).cursorLeft(pl.length()).render(ph));

    }

    private void updateLines() {
        String r = getNumString(gameModel.getRoundCount());

        lines.clear();
        if (gameModel.isLastRound()) {
            lines.add( "+--@|red /!\\|@---@|red Last round|@---@|red /!\\|@--+" );
        } else {
            lines.add( "+--------------------------+");
        }
        lines.add( "| Game info                |" );
        lines.add( "| Round: " + r + "                |" );
        lines.add( "|                          |");
        lines.add( "|                          |");
        lines.add( "|                          |");
        lines.add( "|                          |");
        lines.add( "+--------------------------+" );
    }

    private String getNumString(Integer i) {
        String s = "";
        if (i < 10) s = "0" + i.toString();
        if (i >= 10) s = i.toString();
        return s;
    }

    private String formatName(String name) {
        if(name.length() == 5) return name;
        if(name.length() > 5) return name.substring(0, 5);
        return name + " ".repeat(5 - name.length());
    }

    private String getCurrPlayer(Phase phase) {
        if (phase == null) return "";
        return formatName(gameModel.getCurrentPlayer().getName()) + ",";
    }

    private String getPhaseMessage(Phase phase) {
        if (phase == null) return "";
        switch (phase) {
            case SETUP:
                return "choose a wizard!";
            case PLANNING:
                return "choose an assistant!";
            case ACTION_STUDENTS:
                return "move your students! (" + (3 - gameModel.getNumStudentsMoved()) + ")";
            case ACTION_MOTHERNATURE:
                return "move Mother Nature!";
            case ACTION_CLOUDS:
                return "pick a cloud!";
        }
        return "ERROR";
    }

}
