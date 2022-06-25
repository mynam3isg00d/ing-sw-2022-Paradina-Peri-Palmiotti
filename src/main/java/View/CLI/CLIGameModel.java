package View.CLI;

import Model.GameModel;
import Model.Phase;

import java.util.ArrayList;
import java.util.Random;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Displays game information in the CLI
 */
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
        if(gameModel.getGamePhase().equals(Phase.END)) {
            System.out.print( ansi().eraseScreen() );
            try {
                String win = getWinningString();

                Random r = new Random();
                for(int i=0; i<150; i++) {
                    CLIFirework firework = new CLIFirework(i%7);
                    firework.displayLines(r.nextInt(100), r.nextInt(40));
                }
                System.out.print(ansi().cursor(0, 0).cursorRight(50).cursorDown(21));
                System.out.print("Team " + win + " won!!!!");
                if (win.equals("LOVE")) System.out.print( " (it's a draw)");
                if (win.equals("404")) System.out.print(" (Game interrupted)");
            } catch (NullPointerException ignored) {}
        } else {
            super.displayLines(x0, y0);
            if (gameModel.isLastRound()) System.out.print( ansi().cursor(0,0).cursorRight(x0).cursorDown(y0)
                    .render("+--@|red /!\\---Last round---/!\\|@--+"));
            String pl = getCurrPlayer(gameModel.getGamePhase());
            String ph = getPhaseMessage(gameModel.getGamePhase());

            System.out.print(ansi().cursor(0,0).cursorRight(x0 + 2).cursorDown(y0 + 4).render(pl));
            System.out.print(ansi().cursorDown(1).cursorLeft(pl.length()).render(ph));
        }
    }

    private void updateLines() {
        String r = getNumString(gameModel.getRoundCount());

        lines.clear();
        lines.add( "+--------------------------+");
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
                String s = "choose a wizard!";
                for(int i=0; i<4; i++) {
                    if(!gameModel.getPickedWizards()[i]) {
                        s += "" + i;
                    }
                }
                return s;
            case PLANNING:
                return "play an assistant!";
            case ACTION_STUDENTS:
                return "move your students! (" + (gameModel.getSTUDENTS_PER_TURN() - gameModel.getNumStudentsMoved()) + ")";
            case ACTION_MOTHERNATURE:
                return "move Mother Nature!";
            case ACTION_CLOUDS:
                return "pick a cloud!";
        }
        return "ERROR";
    }

    private String getWinningString() {
        if (gameModel.getWinnerTeam() == 0) return "WHITE";
        if (gameModel.getWinnerTeam() == 1) return "BLACK";
        if (gameModel.getWinnerTeam() == 2) return "GREY";
        if (gameModel.getWinnerTeam() == -2) return "LOVE";
        return "404";
    }

}
