package Controller.CharacterEffects;

import Controller.Game;
import Controller.IslandController;
import Model.Shop;

import java.util.List;

public class HeraldEffect extends CharacterEffect {
    IslandController ic;

    public HeraldEffect(int playerID) {
        super(playerID);
        expectedInputSize = 1;
    }

    @Override
    public void init(Game g) {
        ic = g.getIslandController();
    }

    @Override
    public void playEffect(List<Object> playerInput) {
        //Expects:
        //{islandID : Integer}
        if (playerInput.size() != expectedInputSize || !((playerInput.get(0) instanceof Integer))) {
            //TODO: Exception?
            System.out.println("Message from Herald.java:\nERROR unusable player input");
            return;
        }
        int islandID = (int)playerInput.get(0);
        ic.calcInfluence(islandID);
    }
}
