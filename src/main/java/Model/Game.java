//Notes:
/*
Possible implementation for Game constructors:
    Take from App the names of the players and build different constructors like:
    Game(String name1, String name2)
    Game(String name1, String name2, String name3)
    Game(String name1, String name2, String name3, String name4)
    then create the proper number
*/
//TODO: Add BoardsHandler implementation

package Model;
import java.util.*;

public class Game {

    private int roundCount;
    private int playersNumber;

    private List<Player> players;
    private Sack sack;
    private IslandHandler islandHandler;
    private CloudHandler cloudHandler;

    //NOTE: boardHandler is not properly implemented, keep in mind.
    //BoardHandler boardHandler;

    //Assuming this is a 2game constructor
    public Game(String name1, String name2) {
        roundCount = 0;
        playersNumber = 2;
        players = new ArrayList<Player>();

        //This is hard-coded for now
        players.add(new Player(name1, 0));
        players.add(new Player(name2, 1));

        islandHandler = new IslandHandler();
        cloudHandler = new CloudHandler();
        //TODO: add boardHandler
    }

    private boolean checkEnd() {
        //BoardsHandler end conditions: a player (or team) has no towers left;
        //IslandHandler end conditions: 3 islands left;
        //Sack end conditions:          the sack is empty, but the game goes on for one last round!
        //Hand end conditions:          if the hand is empty, one last round is played.

        //NOTE: this might need some tinkering, the game should end IMMEDIATELY after the last tower is placed
        //      or IMMEDIATELY after the islands go from 4 to 3

        return false;
    }

    public void playRound() {
        roundCount++;
        System.out.println("Playing round " + roundCount);
        //Round r = new Round(this);
    }

    public IslandHandler getIslandHandler() {
        return islandHandler;
    }

    public CloudHandler getCloudHandler() {
        return cloudHandler;
    }

    public Sack getSack() {
        return sack;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
