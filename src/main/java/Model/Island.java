package Model;
import java.util.*;

public class Island {
    private int dimension;
    private int[] students;
    private Integer influenceTeam;
    private boolean motherNature;

    //builds an empty island, dimension is initialized 1
    public Island() {
        dimension = 1;
        students = new int[5];
        influenceTeam = null;
        motherNature = false;
    }

    //merges two islands: creates the resulting island. The dimension of the resulting island is sum of the dimensions of the other two
    public Island(Island i1, Island i2) {
        dimension = i1.getDimension() + i2.getDimension();

        students = new int[5];
        int[] students1;
        int[] students2;
        students1 = i1.getStudents();
        students2 = i2.getStudents();

        for (int i = 0; i < 5; i++) {
            students[i] = students1[i] + students2[i];
        }

        influenceTeam = i1.getInfluence();
        motherNature = true;
    }

    public int getDimension() {
        return dimension;
    }

    //methods regarding students attribute

    public int[] getStudents() {
        return Arrays.copyOf(students, 5);
    }

    public void addStudents(List<Student> newStudents) {
        for(Student s : newStudents) {
            students[s.getColorId()]++;
        }
    }

    //methods regarding influence attribute

    public Integer getInfluence() {
        return influenceTeam;
    }

    public void setInfluence(int team) {
        influenceTeam = Integer.valueOf(team);
        //TODO qualcosa per modificare torri
    }

    //methods regarding motherNature attribute

    public void setMotherNature(boolean n) {
        motherNature = n;
    }

    public boolean isMotherNature() {
        return motherNature;
    }

    @Override
    public String toString() {
        String out = (
                "|  Dimension: " + dimension + "  |\n" +
                "|  Team Influence: " + influenceTeam + "  |\n" +
                "|  MotherNature: " + motherNature + "  |\n" +
                "|  Students: "
        );

        out = out.concat(" Y= " + students[0]);
        out = out.concat(" B= " + students[1]);
        out = out.concat(" G= " + students[2]);
        out = out.concat(" R= " + students[3]);
        out = out.concat(" P= " + students[4]);

        out = out.concat(" |\n");
        return out;
    }
}
