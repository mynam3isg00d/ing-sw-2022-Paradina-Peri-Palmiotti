/*
package Model;
import java.util.*;

public class Round {
    private List<Player> players;

    private Sack sack;
    private IslandHandler islandHandler;
    private CloudHandler cloudHandler;
    private BoardsHandler boardsHandler;
    private Game game;

    public Round(Game g) {
        //when Round is built, playerOrder is yet to be ordered. Players are ordered
        //as they are at the beginning of the game. They will be ordered
        //correctly in planningPhase.
        game = g;
        players = new ArrayList<>(game.getPlayers());

        sack = game.getSack();
        islandHandler = game.getIslandHandler();
        cloudHandler = game.getCloudHandler();
        boardsHandler = game.getBoardsHandler();
    }

    public void planningPhase() {
        //draw 3 students from the bag for each Cloud
        cloudHandler.fillClouds(sack);

        //Players choose 1 Assistant card, starting from the
        //first player and proceeding in a clockwise order
        List<Assistant> playedAssistants = new ArrayList<>();
        for(Player p : players) {
            Assistant toPlay = p.playAssistant(game.getPlayedAssistant(p));
            while(isInPlay(playedAssistants, toPlay)) {
                p.returnAssistantToHand(toPlay);
                toPlay = p.playAssistant(game.getPlayedAssistant(p));
            }
            playedAssistants.add(toPlay);
        }

        //Assuming the following players are in order
        sortPlayersByAssistant();
    }

    /*
    public void actionPhase() {
        //1.Move 3 Students from the board to either your Dining Room or an Island
        //2.Move mother nature to an island, check if island is conquered, unify islands
        //  if allowed.
        //3.Choose a cloud and pick the students on it
        for(Player p : players) {

            //Show available island information!
            islandHandler.showIslands();

            //moveToIsland returns the list of students that
            //p wants to move to an Island

            //toMove: INTEGER is Island Index, STUDENT is the student to move;
            HashMap<Integer, Student> toMove = new HashMap<Integer, Student>();
            toMove = p.moveToIsland();

            //Forward toMove information to IslandHandler somehow
            //IslandHandler writer, you decide how to implement


            //Move motherNature by a number of steps that is forwarded from the view

            //NOTE: check for max movement granted by assistant and other checks
            int steps = p.getMotherNatureSteps();
            islandHandler.moveMother(steps);


            //view tells which cloud to pick from
            Cloud cloudToPickFrom = //view give me a cloud
            ArrayList<Student> moveableStudents = cloudToPickFrom.empty();

            //interaction with view: choose how to move moveableStudents
        }

        //Move Mother Nature to an Island
    }


    private static boolean isInPlay(List<Assistant> playedAssistants, Assistant a) {
        for (Assistant ass : playedAssistants) {
            if (ass.getOrderNumber() == a.getOrderNumber()) return true;
        }
        return false;
    }

    private void sortPlayersByAssistant() {
        //TODO: doesn't consider same assistant play for now
        ArrayList<Player> temp = new ArrayList<>();
        while(!players.isEmpty()) {
            Player minPlayer = players.get(0);
            int minOrderNumber = 11;
            for(Player p : players) {
                int t = p.getAssistantInPlay().getOrderNumber();
                if(t < minOrderNumber) {
                    minPlayer = p;
                    minOrderNumber = t;
                }
            }
            players.remove(minPlayer);
            temp.add(minPlayer);
        }
        players = temp;
    }
}
*/