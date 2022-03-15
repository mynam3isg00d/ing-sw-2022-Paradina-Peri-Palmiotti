package Model;
import java.util.*;

public class Cloud {
    private List<Student> students;

    //builds an empty cloud
    public Cloud(){
        students = new ArrayList<Student>(3);
    }

    //fills the cloud with the students contained in the "students" list
    //TODO exception if students.size()!=3
    public void fill(List<Student> students){
        this.students=students;
    }

    //empties a cloud returning the students that were on it
    public List<Student> empty(){
        List<Student> studentsOnCloud = new ArrayList<Student>();
        studentsOnCloud = this.students;
        this.students.clear();
        return studentsOnCloud;
    }

}
