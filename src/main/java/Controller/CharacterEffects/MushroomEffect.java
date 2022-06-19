package Controller.CharacterEffects;

import Controller.CharacterEffects.Strategies.MushroomStrategy;
import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

public class MushroomEffect extends InfluenceEffect {

    @Override
    public String explainEffect() {
        return "Choose a color of Student: during the influence calculation this turn, that color adds no influence.";
    }

    public MushroomEffect(String playerID) {
        super(playerID);
    }

    /**
     * Initializes the attributes with the appropriate references
     * @param g reference to expertGame
     * @param cardIndex index of the Mushroom character card
     */
    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
    }

    /**
     * Sets the right influenceStrategy in the island controller.
     * @param playerInput the color selected by the user. The students of this color will not count when calculating
     *                    the influence.
     * @throws InvalidPlayerInputException The input given by the user is invalid
     * @throws Exception Something went wrong
     */
    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        //Expects:
        //{cid : int}
        if (playerInput.size() != 1) throw new InvalidPlayerInputException();

        int cid = Integer.parseInt(playerInput.get(0));
        if (cid < 0 || cid > 4) throw new InvalidPlayerInputException();

        ic.setInfluenceStrategy(new MushroomStrategy(cid));
    }
}
