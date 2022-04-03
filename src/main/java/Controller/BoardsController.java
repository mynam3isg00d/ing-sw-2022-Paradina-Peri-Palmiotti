package Controller;
import Exceptions.EmptySackException;
import Exceptions.NoSuchStudentsException;
import Model.Board;
import Model.Player;
import Model.Sack;
import Model.Student;

import java.util.*;

public class BoardsController {
    private HashMap<String, Board> playerBoardMap;
    private List<Player> players;
    private Player[] professors;

    public BoardsController(List<Player> players) {
        professors = new Player[5];
        this.players = players;
        playerBoardMap = new HashMap<>();
        for(Player p : players) {
            playerBoardMap.put(p.getPlayerID(), new Board());
        }
    }

    public void init(Sack s) throws EmptySackException {
        for(Player p : players) {
            Board b = playerBoardMap.get(p.getPlayerID());
            b.addToEntrance(s.draw(9));
        }
    }

    public void moveToDiner(Player p, Student s) {
        Board b = playerBoardMap.get(p);
        for(Student st : b.getEntrance()) {
            if (st == s) {
                b.addToDining(s);
                b.removeFromEntrance(s);
                return;
            }
        }
        System.out.println("ERROR from BoardsHandler.java: Student not present");
        return;
    }

    public void updateProfessors() {
        int[] maxNumOfStudents = {0, 0, 0, 0, 0};
        for(Player p : players) {
            int[] diners = playerBoardMap.get(p).getDiners();
            for(int i=0; i<5; i++) {
                if (maxNumOfStudents[i] < diners[i]) {
                    professors[i] = p;
                    maxNumOfStudents[i] = diners[i];
                }
            }
        }
    }

    //if the move is not valid throws an exception
    //else, removes the students in s from the board of the right player
    //called by islandHandler when a player wants to move some students to an island
    public void removeFromEntrance(String playerID, List<Student> ss) throws NoSuchStudentsException {
        List<Student> entranceCopy = playerBoardMap.get(playerID).getEntrance();
        for (Student s : ss) {
            if (!entranceCopy.remove(s)) throw new NoSuchStudentsException();
        }

        playerBoardMap.get(playerID).removeFromEntrance(ss);
    }
}
