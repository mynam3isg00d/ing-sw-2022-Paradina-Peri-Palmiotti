package Controller.CharacterEffects;

import Controller.BoardsController;
import Controller.ExpertGame;
import Controller.IslandController;
import Exceptions.InvalidPlayerInputException;
import Model.Student;
import Model.StudentCard;
import View.GUI.Controllers.BoardController;

import java.util.List;

public class ThiefEffect extends StudentsEffect {

    private BoardsController bc;

    @Override
    public String explainEffect() {
        return "Choose a type of student: Every player (including yourself must return 3 Students of that type from their dining room to the bag. If any player has fewer than three students of that type, return as many Students as they have.";
    }

    public ThiefEffect(String playerID) {
        super(playerID);
    }

    /**
     * Initializes the attributes with the appropriate references
     * @param g reference to expertGame
     * @param cardIndex index of the Clerk character card
     */
    @Override
    public void init(ExpertGame g, int cardIndex) {
        bc = g.getBoardsController();
    }

    /**
     * Moves the selected student to the selected island and updates the model.
     * @param playerInput the index of the student and of the island selected by the player
     * @throws InvalidPlayerInputException the input given by the player is invalid
     * @throws Exception something went wrong
     */
    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {
        //Expects:
        //{colorId : int}
        if (playerInput.size() != 1) throw new InvalidPlayerInputException();

        int colorId = Integer.parseInt(playerInput.get(0));
        if (colorId < 0 || colorId > 4) throw new InvalidPlayerInputException();

        //removes 3 students of colorId from everyone's board
        bc.thiefRemove(Student.idToStudent(colorId));
    }
}
