package View.CLI;

import Model.CharacterCard;
import Model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays a character card in the CLI
 */
public class CLICharaterCard extends CLIElement {

    protected CharacterCard characterCard;

    public CLICharaterCard(CharacterCard cc) {
        characterCard = cc;
        lines = new ArrayList<>();

        updateLines();
    }

    public void updateLines() {
        String i = getNumString(characterCard.getCardID());
        int c = characterCard.getCost();


        lines.clear();
        lines.add( "| char " + i +" |" );
        lines.add( "|         |" );
        lines.add( "|         |" );
        lines.add( "| cost: " + c + " |" );
        if (characterCard.isIncremented()) {
            lines.add( "+-----( )-+" );
        } else {
            lines.add( "+---------+" );
        }
    }

    private String getNumString(Integer i) {
        String s = "";
        if (i < 10) s = "0" + i.toString();
        if (i >= 10) s = i.toString();
        return s;
    }

    private String[] getCorrespondingASCIArray(List<Student> stud) {
        if (stud.isEmpty()) return null;

        String[] ret = new String[stud.size()];
        for(int i=0; i<ret.length; i++) {
            switch (stud.get(i)) {
                case RED:
                    ret[i] = "@|red R|@";
                    break;
                case GREEN:
                    ret[i] = "@|green G|@";
                    break;
                case BLUE:
                    ret[i] = "@|blue B|@";
                    break;
                case YELLOW:
                    ret[i] = "@|yellow Y|@";
                    break;
                case PINK:
                    ret[i] = "@|magenta P|@";
                    break;
            }
        }
        return ret;
    }
}
