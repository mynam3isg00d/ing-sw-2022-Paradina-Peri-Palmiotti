package Controller.CharacterEffects;

import Controller.BoardsController;
import Controller.CharacterEffects.Strategies.FarmerStrategy;
import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

//During this turn, you take control of any number of Professors even if you have the same number of Students as the player who currently controls them
public class FarmerEffect extends CharacterEffect{

    private BoardsController bc;
    public FarmerEffect(String playerID) {
        super(playerID);
    }

    /**
     * Initializes the attributes with the appropriate references
     * @param g reference to expertGame
     * @param cardIndex index of the Farmer character card
     */
    @Override
    public void init(ExpertGame g, int cardIndex) {
        bc = g.getBoardsController();
    }

    /**
     * Sets the right professorStrategy in the boardController.
     * @param playerInput Here this should be empty
     * @throws InvalidPlayerInputException The input is not empty as it should be
     * @throws Exception Something went wrong
     */
    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {
        // Expects:
        // {}
        if(playerInput.size() != 0) throw new InvalidPlayerInputException();
        bc.setProfessorStrategy(new FarmerStrategy());
    }
}
