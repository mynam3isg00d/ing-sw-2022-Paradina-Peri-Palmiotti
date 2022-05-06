package Model;
import Exceptions.EmptyCloudException;
import Exceptions.CloudNotEmptyException;
import Exceptions.InvalidStudentListException;

import java.util.*;

public class Cloud {
    private List<Student> students;
    int numOfStudents;

    /**
     * Creates an empty cloud
     * @param numOfStudents max number of students allowed on the cloud
     */
    public Cloud(int numOfStudents){
        students = new ArrayList<>();
        this.numOfStudents = numOfStudents;
    }

    /**
     * Fills the cloud with the student list if it's empty
     * @param students the student list to add
     * @throws CloudNotEmptyException if the cloud is not empty
     * @throws InvalidStudentListException if the student list is not of the correct size
     */
    public void fill(List<Student> students) throws CloudNotEmptyException, InvalidStudentListException {
        if (!this.isEmpty()) throw new CloudNotEmptyException();
        if (students.size() != numOfStudents) throw new InvalidStudentListException();
        this.students.addAll(students);
    }

    /**
     * Empties a cloud returning the students that were on it
     * @return the list on the cloud
     * @throws EmptyCloudException if the cloud is already empty
     */
    public List<Student> empty() throws EmptyCloudException {
        if(this.isEmpty()) throw new EmptyCloudException();
        List<Student> studentsOnCloud = new ArrayList<>(this.students);
        this.students.clear();
        return studentsOnCloud;
    }

    //i'm not gonna jdoc this

    public List<Student> getStudents() {
        return new ArrayList<>(this.students);
    }

    public boolean isEmpty() {
        if (students.size() == 0) return true;
        return false;
    }

    public List<Student> peek() {
        return new ArrayList<>(students);
    }
}
