package Controller.CharacterEffects;

import Controller.BoardsController;
import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;
import Model.Student;
import Model.StudentCard;

import java.util.ArrayList;
import java.util.List;

public class JesterEffect extends StudentsEffect {

    private BoardsController bc;
    private String playerID;

    @Override
    public String explainEffect() {
        return "You may take up to 3 Students from this card and replace them with the same number of Students from your Entrance";
    }

    public JesterEffect(String playerID) {
        super(playerID);
        this.playerID = playerID;
    }

    /**
     * Initializes the attributes with the appropriate references
     * @param g reference to expertGame
     * @param cardIndex index of the Jester character card
     */
    @Override
    public void init(ExpertGame g, int cardIndex) {
        bc = g.getBoardsController();
        sc = (StudentCard) g.getCharacterController().getShopReference().getShop()[cardIndex];
    }

    /**
     * Swaps the students selected by the user.
     * @param playerInput the students selected by the user. The user can select up to 3 couples of students, where each
     *                    couple has 1 student from the student card and 1 from the entrance.
     *                    Possible inputs:
     *                    {cardStudent0 : int, entranceStudent0 : int}
     *                    {cardStudent0 : int, cardStudent1 : int, entranceStudent0 : int, entranceStudent1 : int}
     *                    {cardStudent0 : int, cardStudent1 : int, cardStudent2 : int, entranceStudent0 : int, entranceStudent1 : int, entranceStudent2 : int}
     * @throws InvalidPlayerInputException the input given by the user is invalid
     * @throws Exception something went wrong
     */
    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        //Expects: (3 options)
        //{cardStudent0 : int, entranceStudent0 : int}
        //{cardStudent0 : int, cardStudent1 : int, entranceStudent0 : int, entranceStudent1 : int}
        //{cardStudent0 : int, cardStudent1 : int, cardStudent2 : int, entranceStudent0 : int, entranceStudent1 : int, entranceStudent2 : int}
        if (playerInput.size() != 2 && playerInput.size() != 4 && playerInput.size() != 6) throw new InvalidPlayerInputException();

        List<Integer> cardStudents = new ArrayList<>();
        List<Integer> entranceStudents = new ArrayList<>();

        for(int i=0; i<playerInput.size()/2; i++) {
            int cs = Integer.parseInt(playerInput.get(i));
            if (cs < 0 || cs > 6) throw new InvalidPlayerInputException();
            cardStudents.add(cs);
        }
        for(int i=playerInput.size()/2; i<playerInput.size(); i++) {
            int es = Integer.parseInt(playerInput.get(i));
            if (es < 0 || es > bc.getBoard(playerID).getMaxEntrance() - 1) throw new InvalidPlayerInputException();
            entranceStudents.add(es);
        }

        List<Student> toMove = new ArrayList<>(sc.getStudents(cardStudents));

        //Add entrance students to the card
        for(Integer i : entranceStudents) {
            sc.addStudent( bc.removeFromEntrance(playerID, i) );
        }

        //Add card students to entrance
        bc.addToEntrance(playerID, toMove);
    }
}
