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

    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
    }

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
