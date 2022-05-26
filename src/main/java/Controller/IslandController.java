package Controller;
import Controller.CharacterEffects.Strategies.DefaultInfluenceStrategy;
import Controller.CharacterEffects.Strategies.InfluenceStrategy;
import Exceptions.EmptyElementException;
import Exceptions.FullElementException;
import Exceptions.InvalidMoveException;
import Model.*;
import View.RemoteView;

import java.util.*;

/**
 * The Controller in charge of the operations regarding the game Islands.
 */
public class IslandController {
    private IslandsWrapper islandModel;
    private BoardsController boardsController;
    private InfluenceStrategy influenceStrategy;

    /**
     * The IslandController constructor is called by Game.
     * Also initializes the Model of the Islands (IslandWrapper)
     */
    public IslandController() {
        influenceStrategy = new DefaultInfluenceStrategy();
        islandModel = new IslandsWrapper();
    }

    public void initIslands() {
        islandModel.initIslands();
    }

    /**
     * Connects BoardController to the IslandController
     * @param b BoardController passed by reference
     */
    public void connectBoards(BoardsController b) {
        boardsController = b;
    }

    public void addObserverToModel(RemoteView rv) {
        islandModel.addObserver(rv);
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
     * @throws InvalidMoveException
     */
    public void moveMother(int steps) throws InvalidMoveException, EmptyElementException, FullElementException {
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
     * Sets a new Influence Strategy
     * @param newStrategy The new InfluenceStrategy
     */
    public void setInfluenceStrategy(InfluenceStrategy newStrategy) {
        influenceStrategy = newStrategy;
    }

    public InfluenceStrategy getInfluenceStrategy() {
        return influenceStrategy;
    }

    /**
     * Calculates the most influent team on the island and acts accordingly
     * @param islandIndex The island
     */
    public void calcInfluence(int islandIndex) throws EmptyElementException, FullElementException {
        int mostInfluentTeam = influenceStrategy.calcInfluence(islandIndex, islandModel, boardsController);

        //case: tie
        //case: no one has influence
        if (mostInfluentTeam == -1) return;

        checkAndMerge(mostInfluentTeam, islandIndex);
    }

    /**
     * A private method that gets called by calcInfluence() only if the influence on an island has changed. If necessary asks the model to merge the islands
     * @param mostInfluentTeam the new most influent team
     * @param islandIndex the index of the island
     */
    private void checkAndMerge(Integer mostInfluentTeam, int islandIndex) throws EmptyElementException, FullElementException {
            //updates the tower counts of the two involved teams
            //removes a tower from the new owner
            String newOwnerId = boardsController.getTeamLeaderId(mostInfluentTeam);
            boardsController.removeTowers(newOwnerId, islandModel.getIslandDimension(islandIndex));

            //adds back the removed towers to the board of the previous most influent team (if existing)
            if (islandModel.getInfluence(islandIndex) != null) {
                String oldOwnerId = boardsController.getTeamLeaderId(islandModel.getInfluence(islandIndex));
                boardsController.addTowers(oldOwnerId, islandModel.getIslandDimension(islandIndex));
            }


            //sets the influence on the island
            islandModel.setInfluence(islandIndex, mostInfluentTeam);
            System.out.println("CONTROLLER SAYS: Set influence " + mostInfluentTeam + " on island " + islandIndex);

            //checks if a merge is needed with the nearby islands
            boolean mergedPrev = false;
            boolean mergedNext = false;

            int rightIndex = islandIndex + 1;
            if (islandIndex == getIslandsQuantity() - 1) {
                rightIndex = 0;
            }

            if (islandModel.getInfluence(rightIndex)!=null && islandModel.getInfluence(islandIndex).equals(islandModel.getInfluence(rightIndex))) {
                mergedNext = true;
                System.out.println("CONTROLLER SAYS: merge needed with island on the right");
            }


            int leftIndex = islandIndex - 1;
            if (islandIndex == 0) {
                leftIndex = getIslandsQuantity() - 1;
            }

            if (islandModel.getInfluence(leftIndex)!=null && islandModel.getInfluence(islandIndex).equals(islandModel.getInfluence(leftIndex))) {
                mergedPrev = true;
                System.out.println("CONTROLLER SAYS: merge needed with island on the left");
            }



            if (mergedPrev && mergedNext) {
                islandModel.mergeIslands(leftIndex, 3);
            } else if (mergedPrev) {
                islandModel.mergeIslands(leftIndex, 2);
            } else if (mergedNext) {
                islandModel.mergeIslands(islandIndex,2);
            } else {
                System.out.println("CONSOLE: No merge happened");
            }
    }


    public void moveStudent(int islandIndex, Student student){
        List<Student> toAdd = new ArrayList<>();
        toAdd.add(student);

        islandModel.addStudents(islandIndex, toAdd);
    }

    public int getIslandsQuantity() {
        return islandModel.getIslandLength();
    }

    public IslandsWrapper getIslandModel() {
        return islandModel;
    }
}
