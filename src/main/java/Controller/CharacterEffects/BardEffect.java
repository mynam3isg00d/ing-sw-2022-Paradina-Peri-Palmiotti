package Controller.CharacterEffects;

import Controller.BoardsController;
import Controller.CharacterController;
import Controller.ExpertGame;
import Exceptions.EmptyElementException;
import Exceptions.InvalidPlayerInputException;
import Model.Board;
import Model.Sack;
import Model.Student;

import java.util.ArrayList;
import java.util.List;

public class BardEffect extends CharacterEffect {

    private String playerID;
    private BoardsController bc;
    private CharacterController cc;

    public String explainEffect() {
        return "You may exchange up to 2 students between your Entrance and your Dining Room";
    }

    public BardEffect(String playerID) {
        super(playerID);
        this.playerID = playerID;
    }

    /**
     * Initializes the attributes with the appropriate references
     * @param g reference to expertGame
     * @param cardIndex index of the Bard character card
     */
    @Override
    public void init(ExpertGame g, int cardIndex) {
        bc = g.getBoardsController();
        cc = g.getCharacterController();
    }

    /**
     * Swaps the 2 or 4 students selected by the player.
     * @param playerInput the input given by the player where they select what students to swap.
     *                    Possible inputs:
     *                    -swap 1 couple of students: input is {entranceStudent0 : int, diningStudent0 : int}
     *                    -swap 2 couples of students: input is {entranceStudent0 : int, entranceStudent1 : int, diningStudent0 : int, diningStudent1 : int}
     * @throws InvalidPlayerInputException The input given by the player is invalid
     * @throws Exception Something went wrong
     */
    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        //Expects: (2 options)
        //{entranceStudent0 : int, diningStudent0 : int}
        //{entranceStudent0 : int, entranceStudent1 : int, diningStudent0 : int, diningStudent1 : int}
        if (playerInput.size() != 2 && playerInput.size() != 4) throw new InvalidPlayerInputException();

        //Note: studentsToEntrance are taken from the dining, their integer value is the color of the student
        //      studentsToDining are taken from the entrance, their integer value is the entrance array index
        List<Integer> studentsToEntrance = new ArrayList<>();
        List<Integer> studentsToDining = new ArrayList<>();

        for(int i=playerInput.size()/2; i<playerInput.size(); i++) {
            int std = Integer.parseInt(playerInput.get(i));
            if (std < 0 || std > bc.getBoard(playerID).getMaxEntrance() - 1) throw new InvalidPlayerInputException();
            studentsToDining.add(std);
        }

        for(int i=playerInput.size()/2; i<playerInput.size(); i++) {
            int ste = Integer.parseInt(playerInput.get(i));
            if (ste < 0 || ste > 4) throw new InvalidPlayerInputException();
            studentsToEntrance.add(ste);
        }

        //Check that the inputs are valid
        int[] check = new int[]{0, 0, 0, 0, 0};
        for(Integer i : studentsToEntrance) {
            check[i]++;
            if (check[i] > bc.getBoard(playerID).getDinings()[i]) throw new EmptyElementException();
        }

        for(Integer i : studentsToDining) {
            if (bc.getBoard(playerID).getEntrance()[i] == null) throw new EmptyElementException();
        }

        //remove students from the dining...
        List<Student> toAdd = new ArrayList<>();
        for(Integer i : studentsToEntrance) {
            bc.removeFromDining(playerID, Sack.intToStudent(i));
            toAdd.add(Sack.intToStudent(i));
        }

        //add entrance students to the dining
        for(Integer i : studentsToDining) {
            bc.moveFromEntranceToDining(playerID, i);
            bc.updateProfessors();
            Board b = bc.getBoard(playerID);
            if (b.getDinings()[i] % 3 == 0) cc.giveCoins(playerID, 1);
        }

        bc.addToEntrance(playerID, toAdd);
    }
}
