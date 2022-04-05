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

    //gets the number of steps and moves mother nature accordingly
    //TODO pass the maximumSteps parameter in a smart way from the controller
    public void moveMother(int steps, int maximumSteps) throws InvalidMoveException{
        //checks card power
        if (steps > maximumSteps) throw new InvalidMoveException("You must move mother nature a number of steps which is lower than the number indicated on the Assistant card you played");

        //gets the old mother nature position
        int oldPosition = islandModel.getMotherNaturePos();

        //the new position is:
        int newPosition = (oldPosition + steps) % islandModel.getIslandLength();

        //changes the motherNature position in the model
        islandModel.moveMotherNature(newPosition);

        //calculates the influences on the new position
        calcInfluence(motherNaturePosition);
    }

    public void calcInfluence(int islandIndex){
        //every team influence will be stored in a hashmap like:
        //teamId -> influence
        HashMap<Integer, Integer> influences = new HashMap<>();

        //checks which teams have some influence on the island (ie: the teams which have at least one professor and puts them on the infuences map)
        List<Student> colors = Eryantis.getColors();
        for (Student s : colors) {
            Player owner = boardsController.getProfessorOwner(s.getColorId());
            if (!influences.containsKey(owner.getTeamID())) influences.put(owner.getTeamID(), 0);
        }

        //the team who has one or more towers get an extra point of influence for every one of them
        Integer currentInfluence = islandModel.getInfluence(islandIndex);
        if ( currentInfluence != null) {
            influences.put(currentInfluence, islandModel.getIslandDimension(islandIndex));
        }

        int[] students = islandModel.getStudents(islandIndex);

        for(int color = 0; color < 5; color++) {
            //studentsOnIsland is the number of [color] students already on the island
            int studentsOnIsland = students[color];

            Player professorOwner = boardsController.getProfessorOwner(color);
            if (professorOwner != null) {
                //playerKey is the player who controls the [color] professor
                Player playerKey = professorOwner;

                //influenceAdded is the influence value given by the control of the professor in consideration
                //puts oldInfluence + studentsOnIsland in the hashmap next to the relative team
                int oldInfluence = influences.get(playerKey.getTeamID());
                influences.put(playerKey.getTeamID(), oldInfluence+studentsOnIsland);
            }
        }

        //empties the map in order to obtain a list of map entries sorted by influence
        List<Map.Entry<Integer, Integer>> topInfluences = new ArrayList<>();
        for (int j = 0; j < influences.size(); j++){
            int maxInfluence = 0;
            Map.Entry<Integer, Integer> bestEntry = null;

            for (Map.Entry<Integer, Integer> e : influences.entrySet()) {
                if (e.getValue() >= maxInfluence) bestEntry = e;
            }

            topInfluences.add(bestEntry);
            influences.remove(bestEntry.getKey());
        }

        int maximumInfluence = topInfluences.get(0).getValue();

        //only leaves the best scoring entries in the list
        for (Map.Entry<Integer, Integer> e : topInfluences) {
            if (e.getValue() != maximumInfluence) topInfluences.remove(e);
        }

        //handles ties and decides which is the new most influent team
        //TODO handle null pointers in currentInfluence
        int mostInfluentTeam;
        if (topInfluences.size() == 1) {
            mostInfluentTeam = Integer.valueOf(topInfluences.get(0).getKey()); //the player with the maximum influence
        } else {
            //TODO potrebbero pareggiare i due team senza influenza sull'isola? che succederebbe?
            mostInfluentTeam = currentInfluence;
        }



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

    //gets an object from the view indicating: (1) the player requesting to move (2) the destination island (3) the number of students (4) a string indicating the color
    public void moveStudents(String playerID, int islandIndex, int numOfStudents, String color) throws InvalidMoveException, NoSuchIslandException, NoSuchStudentsException {
        //3 is the maximum number of students the rules want you to move
        if (numOfStudents > 3) throw new InvalidMoveException("You must move a maximum of 3 students in this phase of the game");

        //Y, R, P, G, B represent the 5 colors of the game. All other are wrong
        if (!color.equals("Y") && !color.equals("R") && !color.equals("P") && !color.equals("G") && !color.equals("B")) {
            String alert = color + " is not a color";
            throw new InvalidMoveException(alert);
        }

        //every index higher than the length of the islands array in the model is non-existent at this stage of the game
        if (islandIndex >= islandModel.getIslandLength() || islandIndex < 0) throw new NoSuchIslandException();

        //generates a list of students to add to the selected island
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

        //removes the students from playerID's board
        boardsController.removeFromEntrance(playerID, students);

        //modifies the model, adding the student to the [islandIndex] island
        islandModel.addStudents(islandIndex, students);

        //TODO aggiungere notifica alla view in IslandModel
    }
    public void connectIslandModel(IslandsWrapper m) {
        islandModel = m;
    }
}
