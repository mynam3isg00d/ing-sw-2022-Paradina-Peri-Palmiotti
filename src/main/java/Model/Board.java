package Model;
import Exceptions.*;

import java.util.*;

public class Board extends Observable{

    private final int MAXTABLESEATS = 10;
    private final int MAXENTRANCEPLACES;
    private final int MAXTOWERS;

    private int[] diners;
    private Student[] entrance;
    private boolean[] professors;
    private int towersNum;

    public Board(int towersNum, int maxEntrancePlaces) {
        diners = new int[]{0, 0, 0, 0, 0};
        MAXENTRANCEPLACES = maxEntrancePlaces;
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

    /**
     * Adds the student list s to the entrance (drawn from sack usually)
     * @param s is the list to add!!
     * @throws FullEntranceException if the entrance is full
     */
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

    public Student removeFromEntrance(int idx) throws NoSuchStudentsException {
        if (entrance[idx] == null) throw new NoSuchStudentsException();

        Student retval = entrance[idx];
        entrance[idx] = null;
        return retval;
    }


    //TODO: maybe set these like addTower(int n)? so that we don't send 200.000 models
    /**
     * Adds towers to the board (increases towerNum)
     */
    public void addTower(int n) throws FullElementException {
        if (towersNum + n > MAXTOWERS) throw new FullElementException();
        towersNum += n;
    }

    /**
     * Removes towers from the board (decreases towerNum)
     */
    //TODO: if the towersNum reaches 0, a win condition should be called!!!!!
    public void removeTower(int n) throws EmptyElementException {
        if (towersNum - n < 0) throw new EmptyElementException();
        towersNum -= n;
    }


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

    public int[] getDinings() {
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