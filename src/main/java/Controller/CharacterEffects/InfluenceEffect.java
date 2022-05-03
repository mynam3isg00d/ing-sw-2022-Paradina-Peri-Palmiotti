package Controller.CharacterEffects;

import Controller.IslandController;

public abstract class InfluenceEffect extends CharacterEffect {

    protected IslandController ic;

    public InfluenceEffect(String playerID) {
        super(playerID);
    }
    //something
}
