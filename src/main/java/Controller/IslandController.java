package Controller;
import Exceptions.InvalidMoveException;
import Exceptions.NoSuchIslandException;
import Exceptions.NoSuchStudentsException;
import Model.*;

import java.util.*;

/**
 * The Controller in charge of the operations regarding the game Islands.
 */
public class IslandController {
    private IslandsWrapper islandModel;
    private BoardsController boardsController;

    /**
     * The IslandController constructor is called by Game.
     * Also initializes the Model of the Islands (IslandWrapper)
     */
    public IslandController() {
        //islandModel = new IslandsWrapper();
    }

    /**
     * Connects BoardController to the IslandController
     * @param b BoardController passed by reference
     */
    public void connectBoards(BoardsController b) {
        boardsController = b;
    }

    /**
     * Connects IslandModel to the IslandController
     * @param m IslandModel passed by reference
     */
    public void connectIslandModel(IslandsWrapper m) {
        islandModel = m;
    }

    /**
     * A method that gets the number of steps and moves mother nature accordingly. Only moves if the move is valid
     * @param steps number of steps selected by the player
     * @param maximumSteps the limit of steps the player can choose, accordingly to the assistant he played
     * @throws InvalidMoveException
     */
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
        calcInfluence(newPosition);
    }

    /**
     * Calculates the most influent team on the island and acts accordingly
     * @param islandIndex The island
     */
    public void calcInfluence(int islandIndex){
        //gets the students on the island, if there are no students on the island the situation remains unchanged
        int[] students = islandModel.getStudents(islandIndex);
        int[] noStudentsArray = new int[] {
                0,0,0,0,0
        };
        if (Arrays.equals(students, noStudentsArray)) {
            System.out.println("CONTROLLER SAYS: no students on the island. Nothing changes");
            return;
        }


        //every team influence will be stored in a hashmap like:
        //teamId -> influence
        HashMap<Integer, Integer> influences = new HashMap<>();
        //checks which teams have some influence on the island (ie: the teams which have at least one professor) and puts them on the infuences map
        List<Student> colors = Eryantis.getColors();
        for (Student s : colors) {
            Player owner = boardsController.getProfessorOwner(s.getColorId());
            if (owner != null && !influences.containsKey(owner.getTeamID())) influences.put(owner.getTeamID(), 0);
        }
        //if no professor is assigned, no team can have influence>0 on the island
        if (influences.isEmpty()) {
            System.out.println("CONTROLLER SAYS: no professor is assigned. Nothing changes");
            return;
        }

        //the team who has one or more towers get an extra point of influence for every one of them
        Integer currentInfluence = islandModel.getInfluence(islandIndex);
        if ( currentInfluence != null) {
            influences.put(currentInfluence, islandModel.getIslandDimension(islandIndex));
        }


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
        int influencesSize = influences.size();
        for (int j = 0; j < influencesSize; j++){
            int maxInfluence = 0;
            Map.Entry<Integer, Integer> bestEntry = null;

            for (Map.Entry<Integer, Integer> e : influences.entrySet()) {
                if (e.getValue() >= maxInfluence) {
                    maxInfluence = e.getValue();
                    bestEntry = e;
                }
            }

            topInfluences.add(bestEntry);
            System.out.println("CONTROLLER SAYS: added to topInfluences: " + bestEntry.getKey() + "   " + bestEntry.getValue());
            influences.remove(bestEntry.getKey());
        }

        int maximumInfluence = topInfluences.get(0).getValue();

        //only leaves the best scoring entries in the list
        topInfluences.removeIf(e -> e.getValue() != maximumInfluence);

        //handles ties and decides which is the new most influent team
        //TODO handle null pointers in currentInfluence
        int mostInfluentTeam;
        if (topInfluences.size() == 1) {
            mostInfluentTeam = Integer.valueOf(topInfluences.get(0).getKey()); //the team with the maximum influence
        } else {
            boolean isOldInfluenceInTheList = false;
            for (Map.Entry<Integer, Integer> e : topInfluences) {
                if (e.getKey() == currentInfluence) isOldInfluenceInTheList = true;
            }

            if (isOldInfluenceInTheList) {
                mostInfluentTeam = currentInfluence;   //in case of a tie between the team which currently has the tower and another team, the influence remains unchanged
            }
            else mostInfluentTeam = -1;     //signals that we get a tie between two teams which don't currently have influence on the island, so the situation should remain unchanged
        }

        System.out.println("The most influent team on Island " + islandIndex + " is team " + mostInfluentTeam);


        //if the influence has changed, we check if a merge is needed
        if (currentInfluence == null || mostInfluentTeam != currentInfluence) {
            System.out.println("CONTROLLER SAYS: Checking if a merge is needed");
            checkAndMerge(mostInfluentTeam, islandIndex);
        }
    }

    /**
     * A private method that gets called by calcInfluence() only if the influence on an island has changed. If necessary asks the model to merge the islands
     * @param mostInfluentTeam the new most influent team
     * @param islandIndex the index of the island
     */
    private void checkAndMerge(Integer mostInfluentTeam, int islandIndex) {
            //sets the influence on the island
            islandModel.setInfluence(islandIndex, mostInfluentTeam);
            System.out.println("CONTROLLER SAYS: Set influence " + mostInfluentTeam + " on island " + islandIndex);


            //checks if a merge is needed with the nearby islands
            boolean mergedPrev = false;
            boolean mergedNext = false;
            if (islandModel.getInfluence(islandIndex + 1)!=null && islandModel.getInfluence(islandIndex).equals(islandModel.getInfluence(islandIndex + 1))) {
                mergedNext = true;
                System.out.println("CONTROLLER SAYS: merge needed with island on the right");
            }
            if (islandModel.getInfluence(islandIndex + 1)!=null && islandModel.getInfluence(islandIndex).equals(islandModel.getInfluence(islandIndex - 1))) {
                mergedPrev = true;
                System.out.println("CONTROLLER SAYS: merge needed with island on the left");
            }

            if (mergedPrev && mergedNext) {
                islandModel.mergeIslands(islandIndex-1, 3);
            } else if (mergedPrev) {
                islandModel.mergeIslands(islandIndex-1, 2);
            } else if (mergedNext) {
                islandModel.mergeIslands(islandIndex,2);
            } else {
                System.out.println("CONSOLE: No merge happened");
            }
    }

    //gets an object from the view indicating: (1) the player requesting to move (2) the destination island (3) the number of students (4) a string indicating the color

    /**
     * Removes students from a player entrance and adds them to a selected island
     * @param playerID the player requesting to move
     * @param islandIndex the destination island
     * @param numOfStudents how many students (of the same color) the player wants to move
     * @param color a string indicating the chosen color ( Y/B/G/R/P )
     * @throws InvalidMoveException
     * @throws NoSuchIslandException
     * @throws NoSuchStudentsException
     */
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

    //TODO init
    public void initGame() {}
}
