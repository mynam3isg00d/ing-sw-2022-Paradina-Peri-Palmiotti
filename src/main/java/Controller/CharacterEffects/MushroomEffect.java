package Controller.CharacterEffects;

import Controller.CharacterEffects.Strategies.MushroomStrategy;
import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

public class MushroomEffect extends InfluenceEffect {

    public MushroomEffect(String playerID) {
        super(playerID);
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
    }

    @Override
    public void playEffect(List<Object> playerInput) throws InvalidPlayerInputException, Exception {

        //TODO: Check input format
        int cid = (Integer) playerInput.get(0);

        ic.setInfluenceStrategy(new MushroomStrategy(cid));
    }
}
