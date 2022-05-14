package Controller.CharacterEffects;

import Controller.BoardsController;
import Controller.ExpertGame;
import Exceptions.EmptyTableException;
import Exceptions.FullTableException;
import Exceptions.InvalidPlayerInputException;
import Exceptions.NoSuchStudentsException;
import Model.Sack;
import Model.Student;

import java.util.ArrayList;
import java.util.List;

public class BardEffect extends CharacterEffect {

    private String playerID;
    private BoardsController bc;

    public BardEffect(String playerID) {
        super(playerID);
        this.playerID = playerID;
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        bc = g.getBoardsController();
    }

    @Override
    public void playEffect(List<Object> playerInput) throws InvalidPlayerInputException, Exception {
        // Expects
        // {List<Integer> studentsToEntrance, List<Integer> studentsToDining}

        List<Integer> studentsToEntrance = (List<Integer>) playerInput.get(0);
        List<Integer> studentsToDining = (List<Integer>) playerInput.get(1);

        //TODO: check valid input

        //add entrance students to the dining
        for(Integer i : studentsToDining) {
            bc.moveFromEntranceToDining(playerID, i);
        }

        //remove students from the dining and add them to the entrance
        List<Student> toAdd = new ArrayList<>();
        for(Integer i : studentsToEntrance) {
            bc.removeFromDining(playerID, Sack.intToStudent(i));
            toAdd.add(Sack.intToStudent(i));
        }
        bc.addToEntrance(playerID, toAdd);
    }
}
