package Controller.CharacterEffects;

import Controller.Game;
import Controller.IslandHandler;

public class HeraldEffect extends CharacterEffect {
    IslandHandler ih;

    @Override
    public void getData(Game g) {
        ih = g.getIslandHandler();
    }

    @Override
    public void playEffect(Object playerInput) {
        if (!((playerInput instanceof Integer))) {
            System.out.println("Message from Araldo.java:\nERROR unusable player input");
            return;
        }
        int islandID = (int)playerInput;
        ih.calcInfluence(islandID);
    }
}
