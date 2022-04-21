package Controller.CharacterEffects;

import Controller.Game;
import Controller.IslandController;

public class HeraldEffect extends CharacterEffect {
    IslandController ih;

    @Override
    public void getData(Game g) {
        ih = g.getIslandController();
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
