package Model;
import Exceptions.EmptyCloudException;
import Exceptions.CloudNotEmptyException;
import Exceptions.InvalidStudentListException;
import com.google.gson.annotations.Expose;

import java.util.*;

public class Cloud {
    
    private List<Student> students;
    
    private int numOfStudents;

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
        if(this.isEmpty()) {
            throw new EmptyCloudException();
        }
        List<Student> studentsOnCloud = new ArrayList<>(this.students);
        this.students.clear();
        return studentsOnCloud;
    }


    /**
     * Tells it a cloud has any students on it
     * @return True if cloud is empty, False otherwise
     */
    public boolean isEmpty() {
        if (students.size() == 0) return true;
        return false;
    }

    /**
     * Returns the list of students on the cloud without taking the students from it
     * @return an arraylist of the students on the cloud
     */
    public List<Student> peek() {
        return new ArrayList<>(students);
    }

    public int getNumOfStudents() {
        return numOfStudents;
    }
}
