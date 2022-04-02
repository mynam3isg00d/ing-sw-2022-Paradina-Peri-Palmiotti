package Controller;
import Exceptions.InvalidMoveException;
import Exceptions.NoSuchIslandException;
import Exceptions.NoSuchStudentsException;
import Model.*;

import java.util.*;

public class IslandController {
    private List<Island> islands;
    private int motherNaturePosition;
    private IslandsWrapper islandModel;
    private BoardsController boardsController;

    //an array containing references to the players that have control on each professor.
    //position 0 -> Yellow
    //position 1 -> Blue
    //position 2 -> Green
    //position 3 -> Red
    //position 4 -> Pink
    private Player[] professors;

    public IslandController() {
        /*
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

        //every island starts with 1 random student on it, except the mother island and the opposite island
        // (in our case all but islands 0 and 5)
        Stack<Student> firstSack= new Stack<Student>();
        for (int i = 0; i < 5; i++) {
            firstSack.add(Sack.intToStudent(i));
            firstSack.add(Sack.intToStudent(i));
        }
        Collections.shuffle(firstSack);

        for (int i = 1; i < 12; i++) {
            if (i != 5) {
                List<Student> toAdd = new ArrayList<Student>();
                toAdd.add(firstSack.pop());
                islands.get(i).addStudents(toAdd);
            }
        }
        */
    }

    public void connectBoards(BoardsController b) {
        boardsController = b;
    }

    public void moveMother(int steps) {
        //moves motherNature
        islands.get(motherNaturePosition).setMotherNature(false);
        motherNaturePosition = (motherNaturePosition + steps) % islands.size();
        islands.get(motherNaturePosition).setMotherNature(true);

        calcInfluence(motherNaturePosition);
    }

    public void calcInfluence(int islandIndex){
        //every team influence will be stored in a hashmap like:
        //teamId -> influence
        HashMap<Integer, Integer> influences = new HashMap<Integer, Integer>();
        for(Player p : professors) {
            if (p!=null) influences.put(Integer.valueOf(p.getTeamId()), 0);
        }

        //the team who has influence over an island gets an extra point of influence
        Integer currentInfluence = islands.get(islandIndex).getInfluence();
        if ( currentInfluence != null) {
            influences.put(currentInfluence, islands.get(islandIndex).getDimension());
        }

        for(int color = 0; color < 5; color++) {
            //studentsOnIsland is the number of [color] students already on the island
            int studentsOnIsland = islands.get(islandIndex).getStudents()[color];

            if (professors[color] != null) {
                //playerKey is the player who controls the [color] professor
                Player playerKey = professors[color];
                System.out.println("Team " + playerKey.getTeamId());

                //influenceAdded is the influence value given by the control of the professor in consideration
                int oldInfluence = influences.get(playerKey.getTeamId());
                influences.put(playerKey.getTeamId(), oldInfluence+studentsOnIsland);
            }
        }

        //returns an Entry with the player and the greatest influence
        //TODO parità
        Map.Entry<Integer, Integer> e = influences.entrySet().stream().max( (Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) -> {
            if (e1.getValue() > e2.getValue()) return 1;
            else return -1;
        }).get();
        System.out.println("max: Team " + e.getKey() + "   " + Integer.toString(e.getValue()));

        //Player mostInfluent = e.getKey(); //the player with the maximum influence
        Integer mostInfluentTeam = Integer.valueOf(e.getKey()); //the player with the maximum influence


        checkAndMerge(mostInfluentTeam, islandIndex, islands.get(islandIndex));
    }

    private void checkAndMerge(Integer mostInfluentTeam, int islandIndex, Island currentIsland) {
        if (currentIsland.getInfluence()==null || mostInfluentTeam != currentIsland.getInfluence() ) { //if TRUE the player with the most influence has changed
            //sets the influence on the island
            islands.get(islandIndex).setInfluence(mostInfluentTeam);
            System.out.println("influenza ora: " + islands.get(0).getInfluence());

            //gets the next island on the right and the next island on the left
            Island nextIsland = islands.get((islandIndex+1)%islands.size());
            Island prevIsland;
            if (islandIndex != 0)  prevIsland = islands.get(islandIndex-1);
            else prevIsland = islands.get(islands.size()-1);

            //if necessary, uses the Island constructor to build new Island merging the old ones
            //the two flags are used in order to understand which island(s) has to be replaced
            Island newIsland = currentIsland;
            boolean mergedPrev = false;
            boolean mergedNext = false;

            if (currentIsland.getInfluence().equals(nextIsland.getInfluence())) {
                newIsland = new Island(newIsland, nextIsland);
                mergedNext = true;
            }
            if (currentIsland.getInfluence().equals(prevIsland.getInfluence())) {
                newIsland = new Island(newIsland, prevIsland);
                mergedPrev = true;
            }

            int oldSize = islands.size();
            if (mergedPrev && mergedNext) {
                islands.remove(islandIndex + 1);
                islands.remove(islandIndex);
                if (islandIndex != 0) {
                    islands.remove(islandIndex - 1);
                    islands.add(islandIndex - 1, newIsland);
                    System.out.println("CONSOLE: Merged with island " + (islandIndex-1) + " and " + (islandIndex+1));
                } else {
                    islands.remove(islands.size()-1);
                    islands.add(newIsland);
                    System.out.println("CONSOLE: Merged with island " + (islandIndex-1) + " and " + (islandIndex+1));
                }
            } else if (mergedPrev) {
                islands.remove(islandIndex);
                if (islandIndex != 0) {
                    islands.remove(islandIndex-1);
                    islands.add(islandIndex-1, newIsland);
                    System.out.println("CONSOLE: Merged with island " + (islandIndex-1));
                } else {
                    islands.remove(islands.size() - 1);
                    islands.add(newIsland);
                    System.out.println("CONSOLE: Merged with island " + (islandIndex-1));
                }
            } else if (mergedNext) {
                islands.remove(islandIndex + 1);
                islands.remove(islandIndex);
                islands.add(islandIndex, newIsland);
                System.out.println("CONSOLE: Merged with island " + (islandIndex+1));
            } else {
                System.out.println("CONSOLE: No merge happened");
            }

        }
    }

    public void moveStudents(String playerID, int islandIndex, int numOfStudents, String color) throws InvalidMoveException, NoSuchIslandException, NoSuchStudentsException {
        boolean valid = true;
        System.out.println("CONTROLLER SAYS: Voglio spostare " + numOfStudents + " studenti di colore " + color + " sull'isola " + islandIndex);
        if (numOfStudents > 3) throw new InvalidMoveException();
        if (!color.equals("Y") && !color.equals("R") && !color.equals("P") && !color.equals("G") && !color.equals("B")) throw new InvalidMoveException();
        if (islandIndex > islandModel.getIslandLength()) throw new NoSuchIslandException();

        List<Student> students = new ArrayList<>();
        for (int i=0; i < numOfStudents; i++) {
            switch (color) {
                case "Y": students.add(Sack.intToStudent(0));
                    break;
                    
                case "B": students.add(Sack.intToStudent(1));
                    break;

                case "G": students.add(Sack.intToStudent(2));
                    break;

                case "R": students.add(Sack.intToStudent(3));
                    break;

                case "P": students.add(Sack.intToStudent(4));
                    break;

            }
        }
        System.out.println("CONTROLLER SAYS: La lista da aggiungere e'");
        for (Student s : students) {
            System.out.println(s);
        }

        boardsController.removeFromEntrance(playerID, students);

        //TODO aggiungere notifica alla view in IslandModel
        islandModel.addStudents(islandIndex, students);
    }
    /*
    public static void main(String[] args) {
        Player p1 = new Player("samu", 0);
        Player p2 = new Player("dinho", 1);

        IslandHandler ih = new IslandHandler();
        List<Student> toAdd = new ArrayList<Student>();
        toAdd.add(Student.BLUE);
        toAdd.add(Student.YELLOW);
        toAdd.add(Student.RED);
        toAdd.add(Student.YELLOW);

        ih.islands.get(0).addStudents(toAdd);
        System.out.println("Sull'isola 0: ");
        for (int i : ih.islands.get(0).getStudents()) System.out.println(i);
        System.out.println(" ");


        ih.professors[0] = p1;
        //ih.professors[1] = p2;
        ih.professors[2] = p2;
        //ih.professors[3] = p1;
        ih.professors[4] = p1;

        ih.islands.get(11).setInfluence(0);
        ih.islands.get(1).setInfluence(0);

        ih.calcInfluence(0);

        System.out.println(" ");System.out.println(" ");
        for (Island i : ih.islands) {
            System.out.println(i);
        }
    }
    */
    public void connectIslandModel(IslandsWrapper m) {
        islandModel = m;
    }
}
