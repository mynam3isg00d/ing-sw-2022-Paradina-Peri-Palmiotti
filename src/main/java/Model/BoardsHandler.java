package Model;
import java.util.*;

public class BoardsHandler {
    private HashMap<Player, Board> playerBoardMap;
    private List<Player> players;
    private Player[] professors;

    public BoardsHandler(List<Player> players) {
        professors = new Player[5];
        this.players = players;
        playerBoardMap = new HashMap<Player, Board>();
        for(Player p : players) {
            playerBoardMap.put(p, new Board());
        }
    }

    public void updateProfessors() {
        int[] maxNumOfStudents = {0, 0, 0, 0, 0};
        for(Player p : players) {
            ArrayList<Integer> diners = playerBoardMap.get(p).getDiners();
            for(int i=0; i<5; i++) {
                if (maxNumOfStudents[i] < diners.get(i)) {
                    professors[i] = p;
                    maxNumOfStudents[i] = (int)diners.get(i);
                }
            }
        }
    }
}
