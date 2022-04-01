package Model;
import java.util.*;

public class Cloud {
    private List<Student> students;
    int numOfStudents;

    //builds an empty cloud
    public Cloud(int numOfStudents){
        students = new ArrayList<>();
        this.numOfStudents = numOfStudents;
    }

    //fills the cloud with the students contained in the "students" list
    //TODO exception if students.size()!=numOfStudents
    public void fill(List<Student> students){
        if (students.size() != numOfStudents) {
            //exception
        }
        this.students=students;
    }

    //empties a cloud returning the students that were on it
    public List<Student> empty(){
        List<Student> studentsOnCloud = new ArrayList<Student>(this.students);
        this.students.clear();
        return studentsOnCloud;
    }

    public List<Student> getStudents() {
        return students;
    }

    public boolean isEmpty() {
        if (students.size() == 0) return true;
        return false;
    }
}
