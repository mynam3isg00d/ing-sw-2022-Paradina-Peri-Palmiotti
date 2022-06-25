package View.CLI;

import Model.NoTileCard;
import Model.Student;
import Model.StudentCard;

import java.util.List;

/**
 * Displays a card with a No-entry tile on it in the CLI
 */
public class CLINoTileCard extends CLICharaterCard {

    public CLINoTileCard(NoTileCard sc) {
        super(sc);
    }

    @Override
    public void updateLines() {
        NoTileCard notileCard = (NoTileCard) characterCard;
        String i = getNumString(notileCard.getCardID());
        String[] nt = getCorrespondingNoTileArray(notileCard.getNoTile());
        int c = notileCard.getCost();

        lines.clear();
        lines.add("| char " + i + " |");
        lines.add("| [" + nt[0] + "] [" + nt[1] + "] |");
        lines.add("| [" + nt[2] + "] [" + nt[3] + "] |");
        lines.add("| cost: " + c + " |");
        if (notileCard.isIncremented()) {
            lines.add("+-----( )-+");
        } else {
            lines.add("+---------+");
        }
    }

    private String getNumString(Integer i) {
        String s = "";
        if (i < 10) s = "0" + i.toString();
        if (i >= 10) s = i.toString();
        return s;
    }

    private String[] getCorrespondingNoTileArray(int noTile) {
        String[] ret = new String[]{" ", " ", " ", " "};
        for(int i=0; i<noTile; i++) {
            ret[i] = "X";
        }
        return ret;
    }
}