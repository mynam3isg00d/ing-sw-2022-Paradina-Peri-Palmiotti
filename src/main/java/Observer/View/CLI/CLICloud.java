package Observer.View.CLI;

import Model.Cloud;
import Model.Student;

import java.util.ArrayList;
import java.util.List;

public class CLICloud extends CLIElement {

    private Cloud cloud;
    private int index;

    public CLICloud(Cloud cloud, int i) {
        this.cloud = cloud;
        index = i;
        lines = new ArrayList<>();

        updateLines();
    }

    public void updateCloud(Cloud cloud) {
        this.cloud = cloud;
        updateLines();
    }

    private void updateLines() {
        if(cloud.getNumOfStudents() == 3) {
            String[] s = getCorrespondingASCIArray(cloud.peek());
            if (s == null) s = new String[]{" ", " ", " "};

            lines.clear();
            lines.add( "               " );
            lines.add( "   ___   _     " );
            lines.add( "  ( @|bold " + index + "|@ )_( ))_  " );
            lines.add( " (           ) " );
            lines.add( "( [" + s[0] + "] [" + s[1] + "] ["+ s[2] +"] )" );
            lines.add( " (   _       ) " );
            lines.add( "  (_) (___)))  " );
        } else {
            String[] s = getCorrespondingASCIArray(cloud.peek());
            if (s == null) s = new String[]{" ", " ", " ", " "};

            lines.clear();
            lines.add( "               " );
            lines.add( "   ___   _     " );
            lines.add( "  ( @|bold " + index + "|@ )_( ))_  " );
            lines.add( " (    [" + s[0] + "]    ) " );
            lines.add( "(   [" + s[1] + "] [" + s[2] + "]   )" );
            lines.add( " (   _[" + s[3] + "]    ) ");
            lines.add( "  (_) (___)))  ");
        }


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
