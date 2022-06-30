package Controller.CharacterEffects;

import Controller.BoardsController;
import Controller.CharacterController;
import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;
import Model.Board;
import Model.Student;
import Model.StudentCard;

import java.util.List;

//Take 1 student from this card and place it in your Dining Room. Then, draw a new Student from the Bag and place it on this card.
public class PrincessEffect extends StudentsEffect {

    private String playerID;
    private BoardsController bc;
    private CharacterController cc;


    public PrincessEffect(String playerID) {
        super(playerID);
        this.playerID = playerID;
    }

    /**
     * Initializes the attributes with the appropriate references
     * @param g reference to expertGame
     * @param cardIndex index of the Princess character card
     */
    @Override
    public void init(ExpertGame g, int cardIndex) {
        bc = g.getBoardsController();
        sack = g.getSack();
        sc = (StudentCard) g.getCharacterController().getShopReference().getShop()[cardIndex];
        cc = g.getCharacterController();
    }


    /**
     * Moves the selected student from the student card to the dining room.
     * @param playerInput The student selected by the player to be moved from the card to the dining room
     *                    Expected input: {studentToTake: int}
     * @throws InvalidPlayerInputException The input given by the user is invalid
     * @throws Exception Something went wrong
     */
    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        //Expects
        //{int studentToTake}
        if (playerInput.size() != 1) throw new InvalidPlayerInputException();

        int studentToTake = Integer.parseInt(playerInput.get(0));
        if (studentToTake < 0 || studentToTake > sc.getMAX_STUDENTS()) throw new InvalidPlayerInputException();

        Student student = sc.getStudent(studentToTake);

        bc.addToDining(playerID, student);
        bc.updateProfessors();
        Board b = bc.getBoard(playerID);
        if (b.getDinings()[student.getColorId()] % 3 == 0) cc.giveCoins(playerID, 1);

        sc.addStudent(sack.draw(1).get(0));
    }
}
