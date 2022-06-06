package Model;
import Exceptions.*;
import Network.JsonFactory;
import Observer.Observable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Board extends Observable {

    private final int MAXTABLESEATS = 10;
    private final int MAXENTRANCEPLACES;
    private final int MAXTOWERS;

    private int[] diners;
    private Student[] entrance;
    private boolean[] professors;
    private int towersNum;

    private String playerName;
    private String playerID;
    private int teamID;
    private boolean isTeamLeaderBoard;

    public Board(int towersNum, int maxEntrancePlaces) {
        diners = new int[]{0, 0, 0, 0, 0};
        MAXENTRANCEPLACES = maxEntrancePlaces;
        entrance = new Student[MAXENTRANCEPLACES];
        Arrays.fill(entrance, null);
        professors = new boolean[]{false, false, false, false, false};
        this.MAXTOWERS = this.towersNum = towersNum;
        isTeamLeaderBoard = true;
    }

    public Board(String playerID, String name, int teamID, int towersNum, int maxEntrancePlaces) {
        diners = new int[]{0, 0, 0, 0, 0};
        MAXENTRANCEPLACES = maxEntrancePlaces;
        entrance = new Student[MAXENTRANCEPLACES];
        Arrays.fill(entrance, null);
        professors = new boolean[]{false, false, false, false, false};
        this.MAXTOWERS = this.towersNum = towersNum;
        playerName = name;
        this.teamID = teamID;
        isTeamLeaderBoard = towersNum != 0;
        this.playerID = playerID;
    }

    public void sendBoard() {
        String s = new JsonFactory().modelToJson(this);
        notify(s);
    }


    /**
     * Adds a student to dining
     * @param s single student to add
     * @throws FullTableException if the table is full
     */
    public void addToDining(Student s) throws FullTableException {
        if (diners[s.getColorId()] == MAXTABLESEATS) throw new FullTableException();
        diners[s.getColorId()]++;

        sendBoard();
    }

    /**
     * Removes a student from the dining
     * @param s single student to remove
     * @throws EmptyTableException if the table is empty
     */
    public void removeFromDining(Student s) throws EmptyTableException {
        if (diners[s.getColorId()] == 0) throw new EmptyTableException();
        diners[s.getColorId()]--;

        sendBoard();
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

        if (emptyCount < s.size()) {
            throw new FullEntranceException();
        }

        for(Student st : s) addToEntrance(st);

        sendBoard();
    }

    /**
     * Adds a single student to entrance
     * @param s single student to add
     */
    public void addToEntrance(Student s) throws FullEntranceException {
        for(int i=0; i<entrance.length; i++) {
            if (entrance[i] == null) {
                entrance[i] = s;

                sendBoard();
                return;
            }
        }

        throw new FullEntranceException();
    }

    public Student removeFromEntrance(int idx) throws NoSuchStudentsException {
        if (entrance[idx] == null) throw new NoSuchStudentsException();

        Student retval = entrance[idx];
        entrance[idx] = null;

        sendBoard();
        return retval;
    }


    //TODO: maybe set these like addTower(int n)? so that we don't send 200.000 models
    /**
     * Adds towers to the board (increases towerNum)
     */
    public void addTower(int n) throws FullElementException {
        if (towersNum + n > MAXTOWERS) throw new FullElementException();
        towersNum += n;

        sendBoard();
    }

    /**
     * Removes towers from the board (decreases towerNum)
     * I'm choosing to allow negative towers since the win condition should already be called by then, we will see if it works
     */
    public void removeTower(int n) {
        towersNum -= n;

        sendBoard();
    }


    //TODO: better names?
    /**
     * Adds the professor to the board
     * @param professorID
     */
    public void addProfessor(int professorID) {
        professors[professorID] = true;
        sendBoard();
    }

    /**
     * Removes the professor from the board
     * @param professorID
     */
    public void removeProfessor(int professorID) {
        professors[professorID] = false;
        sendBoard();
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
    public int getTeamID() {
        return teamID;
    }
    public String getPlayerID() {
        return playerID;
    }
    public String getPlayerName() {
        return playerName;
    }
    public boolean isTeamLeaderBoard() {
        return isTeamLeaderBoard;
    }

    public int getProfNum() {
        int ret = 0;
        for(int i=0; i<professors.length; i++) {
            if (professors[i]) ret++;
        }
        return ret;
    }

    public int getMaxEntrance() {
        return MAXENTRANCEPLACES;
    }
    //************************//


    //********setters*********//

    /*
    public void setDiners(int[] diners) {
        this.diners = diners;
    }

    public void setEntrance(Student[] entrance) {
        this.entrance = entrance;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setProfessors(boolean[] professors) {
        this.professors = professors;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public void setTowersNum(int towersNum) {
        this.towersNum = towersNum;
    }

     */

    //************************//
}