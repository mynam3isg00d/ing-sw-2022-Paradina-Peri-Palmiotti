package Model;
import Exceptions.EmptyTableException;
import Exceptions.FullEntranceException;
import Exceptions.FullTableException;

import java.util.*;

public class Board {

    private final int MAXTABLESEATS = 10;
    private final int MAXENTRANCEPLACES = 9;
    private final int MAXTOWERS;

    private int[] diners;
    private List<Student> entrance;
    private boolean[] professors;
    private int towersNum;

    /**
     * Creates a new board
     * @param towersNum max number of available towers
     */
    public Board(int towersNum) {
        //TODO: tower colors unhandled, maybe finally time to create a tower enum
        diners = new int[]{0, 0, 0, 0, 0};
        entrance = new ArrayList<>();
        professors = new boolean[]{false, false, false, false, false};
        this.MAXTOWERS = this.towersNum = towersNum;
    }

    /**
     * Adds a student to dining
     * @param s single student to add
     * @throws FullTableException if the table is full
     */
    public void addToDining(Student s) throws FullTableException {
        if (diners[s.getColorId()] == MAXTABLESEATS) throw new FullTableException();
        diners[s.getColorId()]++;
    }

    /**
     * Removes a student from the dining
     * @param s single student to remove
     * @throws EmptyTableException if the table is empty
     */
    public void removeFromDining(Student s) throws EmptyTableException {
        if (diners[s.getColorId()] == 0) throw new EmptyTableException();
        diners[s.getColorId()]--;
    }

    //TODO: add proper exceptions

    /**
     * Adds a list of students to entrance
     * @param students list of students to add
     * @throws FullEntranceException if the entrance is full (DOES NOT add students up until maxsize)
     */
    public void addToEntrance(List<Student> students) throws FullEntranceException {
        if (entrance.size() + students.size() > MAXENTRANCEPLACES) throw new FullEntranceException();
        entrance.addAll(students);
    }

    /**
     * Adds a single student to entrance
     * @param s single student to add
     */
    public void addToEntrance(Student s){ entrance.add(s); }

    /**
     * Removes a list of students from entrance
     * @param students list of students to remove
     */
    public void removeFromEntrance(List<Student> students) {
        for (Student s : students) entrance.remove(s);
    }

    /**
     * Removes a single student from entrance
     * @param s single student to remove
     */
    public void removeFromEntrance(Student s) { entrance.remove(s); }

    /**
     * Adds a tower to the board (increases towerNum)
     */
    public void addTower() { towersNum++; }

    /**
     * Removes a tower from the board (decreases towerNum)
     */
    public void removeTower() { towersNum--; }


    //TODO: better names?
    /**
     * Adds the professor to the board
     * @param professorID
     */
    public void addProfessor(int professorID) {
        professors[professorID] = true;
    }

    /**
     * Removes the professor from the board
     * @param professorID
     */
    public void removeProfessor(int professorID) {
        professors[professorID] = false;
    }

    //********getters*********//

    public int[] getDiners() {
        return diners;
    }

    public List<Student> getEntrance() {
        return new ArrayList<>(entrance);
    }

    public boolean[] getProfessors() {
        return professors;
    }

    public int getTowersNum() {
        return towersNum;
    }

    //************************//


    //********setters*********//
    /*
    public void setDiners(ArrayList<Integer> diners) {
        this.diners = diners;
    }

    public void setEntrance(List<Student> entrance) {
        this.entrance = entrance;
    }

    public void setProfessors(ArrayList<Boolean> professors) {
        this.professors = professors;
    }

    public void setTowersNum(int towersNum) {
        this.towersNum = towersNum;
    }
     */
    //************************//
}