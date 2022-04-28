package Controller.CharacterEffects;

import Controller.Game;
import Controller.IslandController;
import Model.CharacterCard;

import java.util.List;

public class CentaurEffect extends CharacterEffect {

    private IslandController ic;

    public CentaurEffect(CharacterCard c) {

    }

    @Override
    public void getData(Game g) {
        ic = g.getIslandController();
    }

    @Override
    public void playEffect(List<Object> playerInput) {
        //ic.setStrategy(new CentaurStrategy( (Student)playerInput.get(0) );
    }
}
