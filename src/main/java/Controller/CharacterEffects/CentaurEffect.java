package Controller.CharacterEffects;

import Controller.CharacterEffects.Strategies.CentaurStrategy;
import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

public class CentaurEffect extends InfluenceEffect {

    public CentaurEffect(String playerID) {
        super(playerID);
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
    }

    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        // Expects:
        // {}
        if(playerInput.size() != 0) throw new InvalidPlayerInputException();

        ic.setInfluenceStrategy(new CentaurStrategy());
    }
}
