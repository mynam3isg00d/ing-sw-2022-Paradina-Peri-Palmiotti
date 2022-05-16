//TODO: Jdocs

package Model;

import Exceptions.FullElementException;

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

    public StudentCard(int cardID, List<Student> students) {
        super(cardID);
        this.students = students;
        MAX_STUDENTS = students.size();
    }

    public void addStudent(Student s) throws FullElementException {
        if(students.size() >= MAX_STUDENTS) {
            throw new FullElementException();
        } else {
            students.add(s);
        }
    }

    public Student getStudent(int index) throws IndexOutOfBoundsException {
        Student ret = students.get(index);
        students.remove(index);
        return ret;
    }

    public List<Student> getStudents(List<Integer> indexes) {
        List<Student> retVal = new ArrayList<>();
        for(Integer i : indexes) {
            retVal.add(students.get(i));
        }
        for(Integer i : indexes) {
            students.remove((int)i);
        }
        return retVal;
    }

    public int getMAX_STUDENTS() {
        return MAX_STUDENTS;
    }

    public List<Student> peekStudents() {
        return students;
    }
}
