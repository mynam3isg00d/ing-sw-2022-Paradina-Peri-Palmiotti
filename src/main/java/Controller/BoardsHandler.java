package Controller;
import Model.Board;
import Model.Player;
import Model.Sack;
import Model.Student;

import java.util.*;

public class BoardsHandler {
    private HashMap<Player, Board> playerBoardMap;
    private List<Player> players;
    private Player[] professors;

    public BoardsHandler(List<Player> players) {
        professors = new Player[5];
        this.players = players;
        playerBoardMap = new HashMap<>();
        for(Player p : players) {
            playerBoardMap.put(p, new Board());
        }
    }

    public void init(Sack s) {
        for(Player p : players) {
            Board b = playerBoardMap.get(p);
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
}
