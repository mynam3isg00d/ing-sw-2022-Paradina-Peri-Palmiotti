package Model;
import java.util.*;

//cloudhandler's job is to handle all interactions with clouds, providing methods which are simpler for the developer
public class IslandHandler {
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
        islands.get(0).setMotherNature(true);
        motherNaturePosition = 0;

        //initially, no professor is taken. So the array is filled with null
        professors = new Player[5];
    }

    public void moveMother(int steps) {
        //moves motherNature
        islands.get(motherNaturePosition).setMotherNature(false);
        motherNaturePosition = (motherNaturePosition + steps) % islands.size();
        islands.get(motherNaturePosition).setMotherNature(true);

        calcInfluence(motherNaturePosition);
    }

    private void calcInfluence(int islandIndex){
        //every player influence will be stored in a hashmap like:
        //PlayerName -> influence
        HashMap<Player, Integer> influences = new HashMap<Player, Integer>();
        for(Player p : professors) {
            if (p!=null) influences.put(p, 0);
        }

        //the player who has influence over an island gets an extra point of influence
        Player currentInfluence= islands.get(islandIndex).getInfluence();
        if ( currentInfluence != null) {
            influences.put(currentInfluence, 1);
        }

        for(int color = 0; color < 5; color++) {
            //studentsOnIsland is the number of [color] students already on the island
            int studentsOnIsland = islands.get(islandIndex).getStudents()[color];

            if (professors[color] != null) {
                //playerKey is the player who controls the [color] professor
                Player playerKey = professors[color];
                System.out.println(playerKey.getName());

                //influenceAdded is the influence value given by the control of the professor in consideration
                int oldInfluence = influences.get(playerKey);
                influences.put(playerKey, oldInfluence+studentsOnIsland);
            }
        }

        //returns an Entry with the player and the greatest influence
        //TODO parità
        Map.Entry<Player, Integer> e = influences.entrySet().stream().max( (Map.Entry<Player, Integer> e1, Map.Entry<Player, Integer> e2) -> {
            if (e1.getValue() > e2.getValue()) return 1;
            else return -1;
        }).get();
        System.out.println("max: " + e.getKey().getName() + "   " + Integer.toString(e.getValue()));

        Player p = e.getKey(); //the player with the maximum influence

        //if(!p.equals( islands.get(islandIndex).getInfluence()) ) //se l'influenza è cambiata
        //checkAndMerge()
    }

    public static void main(String[] args) {
        Player p1 = new Player("samu");
        Player p2 = new Player("dinho");

        IslandHandler ih = new IslandHandler();
        List<Student> toAdd = new ArrayList<Student>();
        toAdd.add(Student.BLUE);
        toAdd.add(Student.YELLOW);
        toAdd.add(Student.RED);
        toAdd.add(Student.YELLOW);

        ih.islands.get(0).addStudents(toAdd);
        for (int i : ih.islands.get(0).getStudents()) System.out.println(i);


        ih.professors[0] = p1;
        //ih.professors[1] = p2;
        ih.professors[2] = p2;
        //ih.professors[3] = p1;
        ih.professors[4] = p1;

        ih.calcInfluence(0);
    }
}
