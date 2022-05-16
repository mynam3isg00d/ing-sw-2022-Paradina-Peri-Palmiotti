package View.CLI;

import Model.Student;
import Model.StudentCard;

import java.util.ArrayList;
import java.util.List;

public class CLIStudentCard extends CLICharaterCard {

    public CLIStudentCard(StudentCard sc) {
        super(sc);
    }

    @Override
    public void updateLines() {
        StudentCard studentCard = (StudentCard) characterCard;
        String i = getNumString(studentCard.getCardID());
        String[] s = getCorrespondingASCIArray(studentCard.peekStudents());
        int c = studentCard.getCost();

        lines.clear();
        lines.add("| char " + i + " |");
        if (studentCard.getMAX_STUDENTS() == 4) {
            if (s == null) s = new String[]{" ", " ", " ", " "};
            lines.add("| [" + s[0] + "] [" + s[1] + "] |");
            lines.add("| [" + s[2] + "] [" + s[3] + "] |");
        } else {
            if (s == null) s = new String[]{" ", " ", " ", " ", " ", " "};
            lines.add("|[" + s[0] + "][" + s[1] + "][" + s[2] + "]|");
            lines.add("|[" + s[3] + "][" + s[4] + "][" + s[5] + "]|");
        }
        lines.add("| cost: " + c + " |");
        if (studentCard.isIncremented()) {
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
