//TODO: Jdocs

package Model;

import Exceptions.FullElementException;
import Exceptions.NoSuchStudentsException;

import java.util.ArrayList;
import java.util.List;

public class StudentCard extends CharacterCard {

    private final int MAX_STUDENTS;
    private List<Student> students;
    public StudentCard(int cardID, int maxStudents) {
        super(cardID);
        students = new ArrayList<>();
        MAX_STUDENTS = maxStudents;
    }

    public void addStudent(Student s) throws FullElementException {
        if(students.size() == MAX_STUDENTS) {
            throw new FullElementException();
        } else {
            students.add(s);
        }
    }

    public Student getStudent(Student s) throws NoSuchStudentsException {
        if(!students.contains(s)) {
            throw new NoSuchStudentsException();
        } else {
            students.remove(s);
            return s;
        }
    }

    public int getMAX_STUDENTS() {
        return MAX_STUDENTS;
    }
}
