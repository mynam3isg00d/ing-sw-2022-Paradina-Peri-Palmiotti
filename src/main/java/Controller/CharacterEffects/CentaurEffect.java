package Controller.CharacterEffects;

import Controller.CharacterEffects.Strategies.CentaurStrategy;
import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

public class CentaurEffect extends InfluenceEffect {

    @Override
    public String explainEffect() {
        return "When resolving a Conquering on an Island, Towers do not count towards influence";
    }

    public CentaurEffect(String playerID) {
        super(playerID);
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
    }


    /**
     * Sets the right influenceStrategy in the island controller.
     * @param playerInput the input given by the player where they select how to play the character card
     * @throws InvalidPlayerInputException There should be no input for this character card
     * @throws Exception Something went wrong
     */
    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        // Expects:
        // {}
        if(playerInput.size() != 0) throw new InvalidPlayerInputException();

        ic.setInfluenceStrategy(new CentaurStrategy());
    }
}
