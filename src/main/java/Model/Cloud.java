package Model;
import Exceptions.CloudEmptyException;
import Exceptions.CloudNotEmptyException;
import Exceptions.InvalidStudentListException;

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
    public void fill(List<Student> students) throws CloudNotEmptyException, InvalidStudentListException {
        if (!this.isEmpty()) throw new CloudNotEmptyException();
        if (students.size() != numOfStudents) throw new InvalidStudentListException();
        this.students.addAll(students);
    }

    //empties a cloud returning the students that were on it
    public List<Student> empty() throws CloudEmptyException {
        if(this.isEmpty()) throw new CloudEmptyException();
        List<Student> studentsOnCloud = new ArrayList<>(this.students);
        this.students.clear();
        return studentsOnCloud;
    }

    public List<Student> getStudents() {
        return new ArrayList<>(this.students);
    }

    public boolean isEmpty() {
        if (students.size() == 0) return true;
        return false;
    }
}
