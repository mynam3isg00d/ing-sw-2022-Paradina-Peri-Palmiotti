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

    /**
     * Overload of the Board class constructor used for testing purposes
     * @param towersNum Number of towers that can be on the board
     * @param maxEntrancePlaces Maximum number of students that can be in the entrance
     */
    public Board(int towersNum, int maxEntrancePlaces) {
        diners = new int[]{0, 0, 0, 0, 0};
        MAXENTRANCEPLACES = maxEntrancePlaces;
        entrance = new Student[MAXENTRANCEPLACES];
        Arrays.fill(entrance, null);
        professors = new boolean[]{false, false, false, false, false};
        this.MAXTOWERS = this.towersNum = towersNum;
        isTeamLeaderBoard = true;
    }

    /**
     * Main overload for the Board class constructor
     * @param playerID ID of the player whose board is being constructed
     * @param name name of the player
     * @param teamID ID of the team that the player belongs to
     * @param towersNum Number of towers that can be on the board
     * @param maxEntrancePlaces Maximum number of students that can be in the entrance
     */
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


    /**
     * Sends the board to class observers
     */
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
     * @throws FullEntranceException if the entrance is already full
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


    /**
     * Removes a student from the entrance and returns it
     * @param idx position of the student in the entrance
     * @return The removed student
     * @throws NoSuchStudentsException There is no student at the specified position
     */
    public Student removeFromEntrance(int idx) throws NoSuchStudentsException {
        if (entrance[idx] == null) throw new NoSuchStudentsException();

        Student retval = entrance[idx];
        entrance[idx] = null;

        sendBoard();
        return retval;
    }

    /**
     * Adds towers to the board (increases towerNum)
     * @param n number of towers to add
     * @throws FullElementException The board already has the maximum number of towers possible
     */
    public void addTower(int n) throws FullElementException {
        if (towersNum + n > MAXTOWERS) throw new FullElementException();
        towersNum += n;

        sendBoard();
    }

    /**
     * Removes towers from the board (decreases towerNum)
     * I'm choosing to allow negative towers since the win condition should already be called by then.
     * @param n number of towers to remove
     */
    public void removeTower(int n) {
        towersNum -= n;

        sendBoard();
    }

    /**
     * Adds the professor to the board
     * @param professorID the ID of the professor to add
     */
    public void addProfessor(int professorID) {
        professors[professorID] = true;
        sendBoard();
    }

    /**
     * Removes the professor from the board
     * @param professorID ht eID of the professor to be removed
     */
    public void removeProfessor(int professorID) {
        professors[professorID] = false;
        sendBoard();
    }

    //-----------------------------------------------------------------------------------------------------------
    // Getters
    //-----------------------------------------------------------------------------------------------------------
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


    /**
     * @return number of professors on the board
     */
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
}