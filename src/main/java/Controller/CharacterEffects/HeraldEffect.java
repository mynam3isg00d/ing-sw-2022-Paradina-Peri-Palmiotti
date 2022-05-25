package Controller.CharacterEffects;

import Controller.ExpertGame;
import Controller.IslandController;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

public class HeraldEffect extends CharacterEffect {

    IslandController ic;

    public HeraldEffect(String playerID) {
        super(playerID);
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
    }

    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        //Expects:
        //{islandID : int}
        if (playerInput.size() != 1) throw new InvalidPlayerInputException();

        int islandID = Integer.parseInt(playerInput.get(0));

        if (islandID < 0 || islandID > ic.getIslandsQuantity() - 1) throw new InvalidPlayerInputException();

        ic.calcInfluence(islandID);
    }
}
