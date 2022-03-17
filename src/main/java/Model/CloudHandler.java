package Model;
import java.util.*;
//cloudhandler's job iCreated first draft of Island classs to handle all interactions with clouds, providing methods which are simpler
public class CloudHandler {
    private List<Island> islands;
    private int motherNaturePosition;

    //an array containing references to the players that have control on each professor.
    //position 0 -> Yellow
    //position 1 -> Blue
    //position 2 -> Green
    //position 3 -> Red
    //position 4 -> Pink
    private Player[] professors;

    public IslandHandler() {
        //initializes the list of islands
        islands = new ArrayList<Island>();
        for (int i = 0; i < 12; i++) {
            Island newIsland = new Island();
            islands.add(newIsland);
        }

        //puts motherNature on the first island (index 0)
        island.get(0).setMotherNature(true);
        motherNaturePosition = 0;

        //initially, no professor is taken. So the array is filled with null
        professors = new Player[5];
    }

    public moveMother(int steps) {
        //moves motherNature
        islands.get(motherNaturePosition).setMotherNature(false);
        motherNaturePosition = (motherNaturePosition + steps) % islands.size();
        islands.get(motherNaturePosition).setMotherNature(true);

        calcInfluence(motherNaturePosition);
    }

    private void calcInfluence(int islandIndex){
        for(Player p : professors) {
            if (p != null) {



            }
        }
    }

    private void mergeIslands(Island i1, Island i2) {
        Island newIsland = new Island(i1, i2);

    }

    public void
}
