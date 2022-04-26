package Controller;
import Controller.CharacterEffects.DefaultStrategies.DefaultInfluenceStrategy;
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
    private DefaultInfluenceStrategy influenceStrategy;

    /**
     * The IslandController constructor is called by Game.
     * Also initializes the Model of the Islands (IslandWrapper)
     */
    public IslandController() {
        influenceStrategy = new DefaultInfluenceStrategy();
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
        int mostInfluentTeam = influenceStrategy.calcInfluence(islandIndex, islandModel, boardsController);

        if (mostInfluentTeam == -1) return;

        checkAndMerge(mostInfluentTeam, islandIndex);
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
