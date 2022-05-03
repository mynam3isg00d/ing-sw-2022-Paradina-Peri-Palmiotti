package Controller.CharacterEffects;

import Controller.CharacterEffects.Strategies.CentaurStrategy;
import Controller.ExpertGame;
import Controller.IslandController;

import java.util.List;

public class CentaurEffect extends InfluenceEffect {

    public CentaurEffect(String playerID) {
        super(playerID);
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
    }

    @Override
    public void playEffect(List<Object> playerInput) {
        ic.setInfluenceStrategy(new CentaurStrategy());
    }
}
