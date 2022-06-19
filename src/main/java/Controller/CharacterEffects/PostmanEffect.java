package Controller.CharacterEffects;

import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

public class PostmanEffect extends CharacterEffect{

    private ExpertGame g;
    public PostmanEffect(String playerID) {
        super(playerID);
    }

    @Override
    public String explainEffect() {
        return "You may move Mother Nature up to 2 additional islands than is indicated by the assistant card you played";
    }

    /**
     * Initializes the attributes with the appropriate references
     * @param g reference to expertGame
     * @param cardIndex index of the Postman character card
     */
    @Override
    public void init(ExpertGame g, int cardIndex) {
        this.g = g;
    }


    /**
     * Sets the postmanActive boolean attribute in Game to True. Now the number of steps that mother nature can
     * move is increased by 2.
     * @param playerInput This should be empty for this character card
     * @throws InvalidPlayerInputException The input is not empty as expected
     * @throws Exception Something went wrong
     */
    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        // Expects:
        // {}
        if(playerInput.size()!=0) throw new InvalidPlayerInputException();

        g.setPostmanActive(true);
    }
}
