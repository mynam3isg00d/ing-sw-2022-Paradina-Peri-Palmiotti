//NOTE: ALL PLAYER REFERNCES SHOULD BE HANDLED WITH THEIR ID INSTEAD (when sensible).

package Controller;
import Exceptions.EmptySackException;
import Exceptions.FullEntranceException;
import Exceptions.FullTableException;
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
        createBoards(players.size());
    }

    private void createBoards(int playerNum) {
        switch(playerNum) {
            case 2:
                for (Player p : players) playerBoardMap.put(p.getPlayerID(), new Board(8));
                break;
            case 3:
                for (Player p : players) playerBoardMap.put(p.getPlayerID(), new Board(6));
                break;
            case 4:
                //addedTowers contains teams which have a player who already possesses the team's towers
                //if the teamId is absent, it gets added and a new "team leader" is created with an 8 tower board
                //if the teamId is present, the player is connected to a no tower board
                List<Integer> addedTowers = new ArrayList<>();
                for (Player p : players) {
                    if(!addedTowers.contains(p.getTeamID())) {
                        addedTowers.add(p.getTeamID());
                        playerBoardMap.put(p.getPlayerID(), new Board(8));
                    } else {
                        playerBoardMap.put(p.getPlayerID(), new Board(0));
                    }
                }
                break;
        }
    }

    public void init(Sack s) throws EmptySackException, FullEntranceException {
        for(Player p : players) {
            Board b = playerBoardMap.get(p.getPlayerID());
            b.addToEntrance(s.draw(9));
        }
    }

    public void moveToDiner(String playerID, Student s) throws NoSuchStudentsException {
        Board b = playerBoardMap.get(playerID);
        for(Student st : b.getEntrance()) {
            if (st == s) {
                try {
                    b.addToDining(s);
                } catch (FullTableException e) {
                    e.getMessage();
                    return;
                }
                b.removeFromEntrance(s);
                return;
            }
        }
        throw new NoSuchStudentsException();
    }

    public void updateProfessors() {
        int[] maxNumOfStudents = {0, 0, 0, 0, 0};
        for(Player p : players) {
            int[] diners = playerBoardMap.get(p.getPlayerID()).getDiners();
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

    public void fillEntrance(String playerID, List<Student> ss) {
        Board b = playerBoardMap.get(playerID);
        try {
            b.addToEntrance(ss);
        } catch (FullEntranceException e) {
            e.getMessage();
        }
    }
}
