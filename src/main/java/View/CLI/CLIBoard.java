package View.CLI;

import Model.Board;
import Model.Student;

import java.util.ArrayList;

/**
 * Displays a board in the CLI
 */
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

    public Board getBoard() {
        return board;
    }

    private void updateLines() {
        String n = formatName(board.getPlayerName(), board.isTeamLeaderBoard());
        String te = getTeamString(board.getTeamID());
        String[] e = getCorrespondingASCIArray(board.getEntrance());
        int[] d = board.getDinings();
        char[] p = getCorrespondingCharArray(board.getProfessors());
        int t = board.getTowersNum();

        lines.clear();
        lines.add(  "+-------+----------+---+"  );
        lines.add(  "| " + n + " |  D    P  |[" + te + "]|"  );
        lines.add(  "|[" + e[0] + "] [" + e[1] + "]| [@|yellow " + d[0] +"|@]  [@|bold,yellow " + p[0] + "|@] |   |"  );
        lines.add(  "|[" + e[2] + "] [" + e[3] + "]| [@|blue " + d[1] +"|@]  [@|bold,blue " + p[1] + "|@] |[" + t +  "]|"  );
        lines.add(  "|[" + e[4] + "] [" + e[5] + "]| [@|green " + d[2] +"|@]  [@|bold,green " + p[2] + "|@] |   |"  );
        lines.add(  "|[" + e[6] + "] [" + e[7] + "]| [@|red " + d[3] +"|@]  [@|bold,red " + p[3] + "|@] |   |"  );
        lines.add(  "|[" + e[8] + "] [" + e[9] + "]| [@|magenta " + d[4] +"|@]  [@|bold,magenta " + p[4] + "|@] |   |"  );
        lines.add(  "+-------+----------+---+"  );
    }

    private String[] getCorrespondingASCIArray(Student[] stud) {
        String[] ret = new String[10];
        for(int i=0; i<ret.length; i++) {
            try {
                if (stud[i] == null) {
                    ret[i] = " ";
                } else {
                    switch (stud[i]) {
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
            } catch (ArrayIndexOutOfBoundsException e) {
                ret[i] = "-";
            }
        }
        return ret;
    }

    private String getTeamString(int i) {
        if (i == 0) return "W";
        if (i == 1) return "B";
        return "G";
    }

    private String formatName(String name, boolean teamLeader) {
        String n = "";
        if(name.length() == 5) n = name;
        if(name.length() > 5) n = name.substring(0, 5);
        if(name.length() < 5) n = name + " ".repeat(5 - name.length());
        if(teamLeader) n = "@|underline " + n + "|@";
        return n;
    }

    private char[] getCorrespondingCharArray(boolean[] bool) {
        char[] ret = new char[bool.length];

        for(int i=0; i<ret.length; i++) {
            ret[i] = bool[i] ? 'X' : ' ';
        }
        return ret;
    }
}
