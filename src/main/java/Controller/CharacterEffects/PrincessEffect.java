//TODO: Jdocs

package Controller.CharacterEffects;

import Controller.BoardsController;
import Controller.ExpertGame;
import Exceptions.EmptySackException;
import Exceptions.FullElementException;
import Exceptions.InvalidPlayerInputException;
import Model.StudentCard;

import java.util.List;

public class PrincessEffect extends StudentsEffect {

    private String playerID;
    private BoardsController bc;

    public PrincessEffect(String playerID) {
        super(playerID);
        this.playerID = playerID;
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        bc = g.getBoardsController();
        sack = g.getSack();
        sc = (StudentCard) g.getCharacterController().getShopReference().getShop()[cardIndex];
    }

    @Override
    public void playEffect(List<Object> playerInput) throws InvalidPlayerInputException, Exception {

        //Expects
        //{int studentToTake}
        int studentToTake = (Integer) playerInput.get(0);

        //TODO: check player input

        bc.addToDining(playerID, sc.getStudent(studentToTake));
        sc.addStudent(sack.draw(1).get(0));
    }
}
