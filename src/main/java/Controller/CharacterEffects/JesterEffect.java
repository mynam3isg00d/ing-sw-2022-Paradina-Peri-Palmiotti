package Controller.CharacterEffects;

import Controller.BoardsController;
import Controller.ExpertGame;
import Exceptions.FullElementException;
import Exceptions.InvalidPlayerInputException;
import Exceptions.NoSuchStudentsException;
import Model.Student;
import Model.StudentCard;

import java.util.ArrayList;
import java.util.List;

public class JesterEffect extends StudentsEffect {

    private BoardsController bc;
    private String playerID;

    public JesterEffect(String playerID) {
        super(playerID);
        this.playerID = playerID;
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        bc = g.getBoardsController();
        sc = (StudentCard) g.getCharacterController().getShopReference().getShop()[cardIndex];
    }

    @Override
    public void playEffect(List<Object> playerInput) throws InvalidPlayerInputException, Exception {

        //Expects:
        // { List<Integer> cardStudents, List<Integer> entranceStudents }
        List<Integer> cardStudents = (List<Integer>) playerInput.get(0);
        List<Integer> entranceStudents = (List<Integer>) playerInput.get(1);

        if(cardStudents.size() > 3 ||
           cardStudents.size() != entranceStudents.size()) throw new InvalidPlayerInputException();

        List<Student> toMove = new ArrayList<>(sc.getStudents(cardStudents));

        //Add entrance students to the card
        for(Integer i : entranceStudents) {
            sc.addStudent( bc.removeFromEntrance(playerID, i) );
        }

        //Add card students to entrance
        bc.addToEntrance(playerID, toMove);
    }
}
