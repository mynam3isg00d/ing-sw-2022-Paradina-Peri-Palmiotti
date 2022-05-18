package View.CLI;

import Model.Assistant;
import Model.Player;

import java.util.ArrayList;
import java.util.List;

public class CLIPlayer extends CLIElement {

    private Player player;

    public CLIPlayer(Player player) {
        this.player = player;
        lines = new ArrayList<>();

        updateLines();
    }

    public void updatePlayer(Player p) {
        this.player = p;
        updateLines();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void displayLines(int x0, int y0) {
        super.displayLines(x0, y0);
        if (player.getHand() != null) {
            List<Assistant> assistants = player.getHand().getHand();
            int offset = 0;

            for (int i=0; i<assistants.size(); i++) {
                CLIAssistant clia = new CLIAssistant(assistants.get(i));
                clia.displayLines(x0 + 14 + offset, y0 + 1);
                offset += clia.getX();
            }
        }
        try {
            CLIAssistant clia = new CLIAssistant(player.getAssistantInPlay());
            clia.displayLines(x0 + 72, y0 + 1);
        } catch (NullPointerException ignored) {}
    }

    private void updateLines() {
        String n = formatName(player.getName());
        String t = getTeamString(player.getTeamID());

        lines.clear();
        lines.add( "+--------------------------------------------------------------------+--------+" );
        lines.add( "| [" + t + "] " + n + ":                                                         |        |" );
        lines.add( "+--------------------------------------------------------------------+--------+" );
    }

    private String formatName(String name) {
        if(name.length() == 5) return name;
        if(name.length() > 5) return name.substring(0, 5);
        return name + " ".repeat(5 - name.length());
    }

    private String getTeamString(int i) {
        if (i == 0) return "W";
        if (i == 1) return "B";
        return "G";
    }
}
