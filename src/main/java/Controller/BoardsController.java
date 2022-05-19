//NOTE: ALL PLAYER REFERNCES SHOULD BE HANDLED WITH THEIR ID INSTEAD (when sensible).
//NOTE: ALL ENTRANCE REFERENCES MUST BE MADE WITH THEIR POSITION INDEX
//      ALL DINING REFERENCES MUST BE MADE WITH THEIR STUDENT TYPE (OR THEIR CORRESPONDING INT)

package Controller;
import Exceptions.*;
import Model.Board;
import Model.Player;
import Model.Sack;
import Model.Student;
import View.RemoteView;

import java.util.*;

//TODO javadoc

/**
 * The Controller in charge of the operations regarding the player boards.
 */
public class BoardsController {
    private final HashMap<String, Board> playerBoardMap;
    private final List<Player> players;
    private final Player[] professors;

    /**
     * Initializes one board per player in the game and links players to their board using a Map
     * Also fills player boards entrances
     * @param players The list of players participating in the game
     * @param s Reference to the sack, used to fill the boards
     */
    public BoardsController(List<Player> players, Sack s) {
        professors = new Player[5];
        this.players = players;
        playerBoardMap = new HashMap<>();
        createBoards(players.size());

        int studentsPerEntrance = 0;
        switch (players.size()) {
            case 2:
            case 4:
                studentsPerEntrance = 7;
                break;
            case 3:
                studentsPerEntrance = 9;
                break;
            default:
                System.out.println("EXCEPTION NEEDED: Invalid number of players");
        }

        //fills boards entrance with the right number of students, accordingly to the number of players
        try {
            init(s, studentsPerEntrance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the board of the corresponding playerID
     * @param playerID the ID to get
     * @return reference to the board
     */
    public Board getBoard(String playerID) {
        return playerBoardMap.get(playerID);
    }

    /**
     * Adds the remote view as an observer for every Board model
     * @param rv The board model
     */
    public void addObserverToModel(RemoteView rv) {
        for (Player p : players) {
            playerBoardMap.get(p.getPlayerID()).addObserver(rv);
        }
    }

    private void createBoards(int playerNum) {
        switch(playerNum) {
            case 2:
                for (Player p : players) playerBoardMap.put(p.getPlayerID(), new Board(p.getName(), p.getTeamID(), 8, 7));
                break;
            case 3:
                for (Player p : players) playerBoardMap.put(p.getPlayerID(), new Board(p.getName(), p.getTeamID(), 6, 9));
                break;
            case 4:
                //addedTowers contains teams which have a player who already possesses the team's towers
                //if the teamId is absent, it gets added and a new "team leader" is created with an 8 tower board
                //if the teamId is present, the player is connected to a no tower board
                List<Integer> addedTowers = new ArrayList<>();
                for (Player p : players) {
                    if(!addedTowers.contains(p.getTeamID())) {
                        addedTowers.add(p.getTeamID());
                        playerBoardMap.put(p.getPlayerID(), new Board(p.getName(),p.getTeamID(),8, 7));
                    } else {
                        playerBoardMap.put(p.getPlayerID(), new Board(p.getName(), p.getTeamID(),0, 7));
                    }
                }
                break;
            default:
                System.out.println("EXCEPTION NEEDED: Invalid number of players");
        }
    }

    /**
     * A private method that initializes player boards with a number of students decided previously
     * @param s Reference to the Students sack
     * @param studentsPerEntrance The number of students to add to every board
     * @throws EmptySackException
     * @throws FullEntranceException
     */
    private void init(Sack s, int studentsPerEntrance) throws EmptySackException, FullEntranceException {
        for(Player p : players) {
            Board b = playerBoardMap.get(p.getPlayerID());
            b.addToEntrance(s.draw(studentsPerEntrance));
        }
    }

    /**
     * Moves one student from the entrance of the player requesting the move to his diner
     * @param playerID The player requesting the move
     * @param index index of the student in the entrance
     * @throws NoSuchStudentsException
     */
    public void moveFromEntranceToDining(String playerID, int index) throws NoSuchStudentsException, FullTableException {
        Board b = playerBoardMap.get(playerID);
        Student s = b.removeFromEntrance(index);
        b.addToDining(s);
    }

    public void removeFromDining(String playerID, Student s) throws EmptyTableException {
        Board b = playerBoardMap.get(playerID);
        b.removeFromDining(s);
    }

    public void addToDining(String playerID, Student s) throws FullTableException {
        Board b = playerBoardMap.get(playerID);
        b.addToDining(s);
    }

    /**
     * Called each time //TODO a student gets moved to a diner (?)
     */
    public void updateProfessors() {

        Player[] old_professors = professors.clone();

        int[] maxNumOfStudents = {0, 0, 0, 0, 0};
        for(Player p : players) {
            int[] diners = playerBoardMap.get(p.getPlayerID()).getDinings();
            for(int i=0; i<5; i++) {
                if (maxNumOfStudents[i] < diners[i]) {
                    professors[i] = p;
                    maxNumOfStudents[i] = diners[i];
                }
            }
        }

        for(int i=0; i<professors.length; i++) {
            if (professors[i] != null) {
                String newOwner = professors[i].getPlayerID();
                if(old_professors[i] == null) {
                    //There is no old owner, simply add the new professor
                    Board newB = playerBoardMap.get(newOwner);
                    newB.addProfessor(i);
                } else {
                    //Remove the old owner and add the new owner
                    String oldOwner = old_professors[i].getPlayerID();

                    //...given that the owner has actually changed
                    if(!newOwner.equals(oldOwner)) {
                        Board newB = playerBoardMap.get(newOwner);
                        Board oldB = playerBoardMap.get(oldOwner);

                        newB.addProfessor(i);
                        oldB.removeProfessor(i);
                    }
                }
            }
        }
    }

    //if the move is not valid throws an exception
    //else, removes the students in s from the board of the right player
    //called by islandHandler when a player wants to move some students to an island
    public Student removeFromEntrance(String playerID, int studentBoardIndex) throws NoSuchStudentsException {
        Student s = playerBoardMap.get(playerID).removeFromEntrance(studentBoardIndex);
        return s;
    }

    /**
     * Adds a list of students to the entrance of the requesting player
     * @param playerID The ID of the player requesting the move
     * @param ss The list of students to add
     */
    public void addToEntrance(String playerID, List<Student> ss) throws FullEntranceException {
        Board b = playerBoardMap.get(playerID);
        b.addToEntrance(ss);
    }

    public Player getProfessorOwner(int colorId) {
        return professors[colorId];
    }

    public String getTeamLeaderId(int teamID) {
        for(Player p : players) {
            Board b = playerBoardMap.get(p.getPlayerID());
            if (p.getTeamID() == teamID && b.isTeamLeaderBoard()) return p.getPlayerID();
        }
        return null;
    }

    public void addTowers(String playerID, int numToAdd) throws FullElementException {
        Board b = playerBoardMap.get(playerID);
        b.addTower(numToAdd);
    }

    public void removeTowers(String playerID, int numToRemove) throws EmptyElementException {
        Board b = playerBoardMap.get(playerID);
        b.removeTower(numToRemove);
    }

    //----------------------------------
    //Testing functions
    //----------------------------------
    public void setProfessor(String color, Player p) {
        switch (color) {
            case "Y":
                professors[0] = p;
                break;

            case "B":
                professors[1] = p;
                break;

            case "G":
                professors[2] = p;
                break;

            case "R":
                professors[3] = p;
                break;

            case "P":
                professors[4] = p;
                break;
        }
    }
}
