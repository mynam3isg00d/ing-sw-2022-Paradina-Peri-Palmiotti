package Controller.CharacterEffects;

import Controller.ExpertGame;
import Controller.IslandController;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

public class HeraldEffect extends CharacterEffect {

    IslandController ic;

    @Override
    public String explainEffect() {
        return "Choose an island and resolve the Island as if Mother Nature had ended her movement there. Mother Nature will still move and the Island where she ends her movement will also be resolved.";
    }

    public HeraldEffect(String playerID) {
        super(playerID);
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
    }


    /**
     * Calculates the influence on the island selected by the player and takes action accordingly.
     * The island is resolved by calling the usual calcInfluence method in the IslandController class.
     * @param playerInput the island selected by the player that will be resolved
     * @throws InvalidPlayerInputException the input given by the user is invalid
     * @throws Exception something went wrong
     */
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
