package Model;

import Exceptions.FullElementException;

import java.util.ArrayList;
import java.util.List;

public class StudentCard extends CharacterCard {

    private final int MAX_STUDENTS;
    private List<Student> students;

    /**
     * Overload of the StudentCard class constructor.
     * @param cardID ID of the card. Each card has a unique ID.
     * @param maxStudents Maximum number of students that can go on top of the card
     */
    public StudentCard(int cardID, int maxStudents) {
        super(cardID);
        students = new ArrayList<>();
        MAX_STUDENTS = maxStudents;
    }

    /**
     * Main overload of the StudentCard class constructor
     * @param cardID ID of the card. Each card has a unique ID.
     * @param students A list of students that go on top of the student card
     */
    public StudentCard(int cardID, List<Student> students) {
        super(cardID);
        this.students = students;
        MAX_STUDENTS = students.size();
    }


    /**
     * Adds students to the student card
     * @param s The student to add
     * @throws FullElementException The student card is already full
     */
    public void addStudent(Student s) throws FullElementException {
        if(students.size() >= MAX_STUDENTS) {
            throw new FullElementException();
        } else {
            students.add(s);
        }
    }


    /**
     * Returns the student in the selected position and removes it from the card
     * @param index Position of the student
     * @return The student in the selected position
     * @throws IndexOutOfBoundsException The selected position does not exist
     */
    public Student getStudent(int index) throws IndexOutOfBoundsException {
        Student ret = students.get(index);
        students.remove(index);
        return ret;
    }


    /**
     * Returns the students at the selected positions and removes them from the student card
     * @param indexes Positions of the students to be removes
     * @return A list containing the students in the selected positions
     * @throws IndexOutOfBoundsException One of more of the selected positions do not exist
     */
    public List<Student> getStudents(List<Integer> indexes) throws IndexOutOfBoundsException {
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
