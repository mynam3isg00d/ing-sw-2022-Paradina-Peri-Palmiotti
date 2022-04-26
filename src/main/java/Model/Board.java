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
    private Student[] entrance;
    private boolean[] professors;
    private int towersNum;

    /**
     * Creates a new board
     * @param towersNum max number of available towers
     */
    public Board(int towersNum) {
        //TODO: tower colors unhandled, maybe finally time to create a tower enum
        diners = new int[]{0, 0, 0, 0, 0};
        entrance = new Student[MAXENTRANCEPLACES];
        Arrays.fill(entrance, null);
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
    public void addToEntrance(List<Student> s) throws FullEntranceException {
        int emptyCount = 0;
        for(int i=0; i<MAXENTRANCEPLACES; i++) {
            if (entrance[i] == null) emptyCount++;
        }
        if (emptyCount < s.size()) throw new FullEntranceException();
        for(Student st : s) addToEntrance(st);
    }

    /**
     * Adds a single student to entrance
     * @param s single student to add
     */
    public void addToEntrance(Student s) throws FullEntranceException {
        for(int i=0; i<entrance.length; i++) {
            if (entrance[i] == null) {
                entrance[i] = s;
                return;
            }
        }
        throw new FullEntranceException();
    }

    @Deprecated
    /**
     * Removes a list of students from entrance
     * @param students list of students to remove
     */
    public void removeFromEntrance(List<Student> students) {
        System.out.println("THIS HAS BEEN DEPRECATED");
        //for (Student s : students) entrance.remove(s);
    }

    @Deprecated
    /**
     * Removes a single student from entrance
     * @param s single student to remove
     */
    public void removeFromEntrance(Student s) {
        System.out.println("THIS HAS BEEN DEPRECATED");
    }

    public Student removeFromEntrance(int idx) {
        Student retval = entrance[idx];
        entrance[idx] = null;
        return retval;
    }

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

    public Student[] getEntrance() {
        return entrance.clone();
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