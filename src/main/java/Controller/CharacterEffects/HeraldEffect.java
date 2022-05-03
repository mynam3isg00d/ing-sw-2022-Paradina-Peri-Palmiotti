package Controller.CharacterEffects;

import Controller.ExpertGame;
import Controller.IslandController;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

public class HeraldEffect extends CharacterEffect {

    IslandController ic;

    public HeraldEffect(String playerID) {
        super(playerID);
        expectedInputSize = 1;
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
    }

    @Override
    public void playEffect(List<Object> playerInput) throws InvalidPlayerInputException {
        //Expects:
        //{islandID : Integer}
        if (playerInput.size() != expectedInputSize || !((playerInput.get(0) instanceof Integer))) {
            throw new InvalidPlayerInputException();
        }
        int islandID = (int)playerInput.get(0);
        ic.calcInfluence(islandID);
    }
}
