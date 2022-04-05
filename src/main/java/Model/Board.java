package Model;
import Exceptions.EmptyTableException;
import Exceptions.FullEntranceException;
import Exceptions.FullTableException;

import java.util.*;

public class Board {

    private final int maxTableSeats = 10;
    private final int maxEntrancePlaces = 9;
    private int maxTowers;

    private int[] diners;
    private List<Student> entrance;
    private boolean[] professors;
    private int towersNum;

    public Board(int towersNum) {
        //TODO: tower colors unhandled, maybe finally time to create a tower enum
        diners = new int[]{0, 0, 0, 0, 0};
        entrance = new ArrayList<>();
        professors = new boolean[]{false, false, false, false, false};
        this.maxTowers = this.towersNum = towersNum;
    }

    public void addToDining(Student s) throws FullTableException {
        if (diners[s.getColorId()] == maxTableSeats) throw new FullTableException();
        diners[s.getColorId()]++;
    }
    public void removeFromDining(Student s) throws EmptyTableException {
        if (diners[s.getColorId()] == 0) throw new EmptyTableException();
        diners[s.getColorId()]--;
    }

    //TODO: add proper exceptions
    public void addToEntrance(List<Student> students) throws FullEntranceException {
        if (entrance.size() + students.size() > maxEntrancePlaces) throw new FullEntranceException();
        entrance.addAll(students);
    }
    public void addToEntrance(Student s){ entrance.add(s); }

    public void removeFromEntrance(List<Student> students){ entrance.removeAll(students); }
    public void removeFromEntrance(Student s) { entrance.remove(s); }

    public void addTower() { towersNum++; }
    public void removeTower() { towersNum--; }

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