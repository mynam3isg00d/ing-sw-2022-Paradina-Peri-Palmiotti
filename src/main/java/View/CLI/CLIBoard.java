//uh oh, how do i connect it to the player lol

package View.CLI;

import Model.Board;
import Model.Student;

import java.util.ArrayList;
import java.util.List;

public class CLIBoard extends CLIElement {

    private Board board;

    public CLIBoard(Board b) {
        this.board = b;
        lines = new ArrayList<>();

        updateLines();
    }

    public void updateBoard(Board board) {
        this.board = board;
        updateLines();
    }

    public List<String> getLines() {
        return lines;
    }

    public void displayLines() {
        for(String l : lines) {
            System.out.println(l);
        }
    }

    //---------

    private void updateLines() {
        char[] e = getCorrespondingCharArray(board.getEntrance());
        int[] d = board.getDinings();
        char[] p = getCorrespondingCharArray(board.getProfessors());
        int t = board.getTowersNum();

        lines.clear();
        lines.add(  "+-------+----------+---+"  );
        lines.add(  "| XXXXX |  D    P  |   |"  );
        lines.add(  "|[" + e[0] + "] [" + e[1] + "]| [" + d[0] +"]  [" + p[0] + "] |   |"  );
        lines.add(  "|[" + e[2] + "] [" + e[3] + "]| [" + d[1] +"]  [" + p[1] + "] |[" + t +  "]|"  );
        lines.add(  "|[" + e[4] + "] [" + e[5] + "]| [" + d[2] +"]  [" + p[2] + "] |   |"  );
        lines.add(  "|[" + e[6] + "] [" + e[7] + "]| [" + d[3] +"]  [" + p[3] + "] |   |"  );
        lines.add(  "|[" + e[8] + "] [" + e[9] + "]| [" + d[4] +"]  [" + p[4] + "] |   |"  );
        lines.add(  "+-------+----------+---+"  );
    }

    private char[] getCorrespondingCharArray(Student[] stud) {
        char[] ret = new char[10];

        for(int i=0; i<ret.length; i++) {
            try {
                if(stud[i] != null) {
                    ret[i] = stud[i].getChar();
                } else {
                    ret[i] = ' ';
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                ret[i] = '-';
            }
        }
        return ret;
    }

    private char[] getCorrespondingCharArray(boolean[] bool) {
        char[] ret = new char[bool.length];

        for(int i=0; i<ret.length; i++) {
            ret[i] = bool[i] ? 'X' : ' ';
        }
        return ret;
    }
}
