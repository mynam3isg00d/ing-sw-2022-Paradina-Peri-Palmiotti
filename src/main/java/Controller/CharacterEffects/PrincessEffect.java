//TODO: Jdocs

package Controller.CharacterEffects;

import Controller.BoardsController;
import Controller.ExpertGame;
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
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        //Expects
        //{int studentToTake}
        if (playerInput.size() != 1) throw new InvalidPlayerInputException();

        int studentToTake = Integer.parseInt(playerInput.get(0));
        if (studentToTake < 0 || studentToTake > sc.getMAX_STUDENTS()) throw new InvalidPlayerInputException();

        bc.addToDining(playerID, sc.getStudent(studentToTake));
        sc.addStudent(sack.draw(1).get(0));
    }
}
