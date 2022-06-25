package View.CLI;

import Model.Island;


import java.util.ArrayList;

/**
 * Displays an island in the CLI
 */
public class CLIIsland extends CLIElement {

    private Island island;
    private int index;

    public CLIIsland(Island i, int id) {
        island = i;
        index = id;
        lines = new ArrayList<>();

        updateLines();
    }

    public void updateIsland(Island i) {
        island = i;
        updateLines();
    }

    private void updateLines() {
        String i = getNumString(index);
        String no = island.isNoEntry() ? "X" : " ";
        int s = island.getDimension();
        String m = getMotherNatureString();
        String n = getInfluenceString(island.getInfluence());
        int[] c = island.getStudents();

        lines.clear();
        lines.add( "   _________    "  );
        lines.add( "  [" + i + "]     [" + no + "]  " );
        lines.add( " /  size: "+ s +"  \\  " );
        lines.add( "/   ["+ m +"] [" + n + "]   \\ " );
        lines.add( "\\ [@|yellow " + c[0] + "|@] [@|blue " + c[1] + "|@] [@|green " + c[2] + "|@] / " );
        lines.add( " \\  [@|red " + c[3] + "|@] [@|magenta " + c[4] + "|@]  /  " );
        lines.add( "  \\_________/   " );
    }

    private String getNumString(Integer i) {
        String s = "";
        if (i < 10) s = "0" + i.toString();
        if (i >= 10) s = i.toString();
        return s;
    }

    private String getMotherNatureString() {
        if(island.isMotherNature()) return "M";
        return " ";
    }

    private String getInfluenceString(Integer i) {
        if(i == null) return " ";
        if(i == 0) return "W";
        if(i == 1) return "B";
        return "G";
    }
}
