package Model;
import java.util.*;

public class Island {
    private int dimension;
    private List<Student> students;
    private Player influence;
    private boolean motherNature;

    //builds an empty island, dimension is initialized 1
    public Island() {
        dimension = 1;
        students = new ArrayList<Student>();
        influence = null;
        motherNature = false;
    }

    //merges two islands: creates the resulting island. The dimension of the resulting island is sum of the dimensions of the other two
    public Island(Island i1, Island i2) {
        dimension = i1.getDimension() + i2.getDimension();
        students = new Arraylist<Student>();
        students.addAll(i1.getStudents());
        students.addAll(i2.getStudents());
        influence = i1.getInfluence();
        motherNature = true;
    }

    public int getDimension() {
        return dimension;
    }

    //methods regarding students attribute

    public List<Student> getStudents() {
        List<> studentsCopy = new ArrayList();
        studentsCopy.addAll(students);
        return studentsCopy;
    }
    public void addStudents(List<Student> newStudents) {
        students.addAll(newStudents);
    }

    //methods regarding influence attribute

    public Player getInfluence() {
        return influence;
    }

    //methods regarding motherNature attribute

    public void setMotherNature() {
        motherNature = true;
    }

    public boolean isMotherNature() {
        return motherNature;
    }
}
