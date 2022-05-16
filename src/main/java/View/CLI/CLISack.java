package View.CLI;

import Model.Sack;

import java.util.ArrayList;

public class CLISack extends CLIElement {

    private Sack sack;

    public CLISack(Sack sack) {
        this.sack = sack;
        lines = new ArrayList<>();

        updateLines();
    }

    private void updateLines() {
        String n = getNumString(sack.getSackSize());

        lines.clear();
        lines.add( "   \\___/   " );
        lines.add( "   /   \\   " );
        lines.add( "  /     \\  " );
        lines.add( " ( [" + n + "] ) " );
        lines.add( "  \\_____/  " );
    }

    private String getNumString(Integer i) {
        String s = "";
        if (i < 10) s = "00" + i.toString();
        if (i >= 10 && i < 100) s = "0" + i.toString();
        if (i >= 100) s = i.toString();
        return s;
    }

}
