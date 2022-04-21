package Controller.CharacterEffects;

import Controller.Game;
import Controller.IslandController;

public class CentaurEffect extends CharacterEffect {

    private IslandController ic;

    @Override
    public void getData(Game g) {
        ic = g.getIslandController();
    }

    @Override
    public void playEffect(Object playerInput) {
        
    }
}
