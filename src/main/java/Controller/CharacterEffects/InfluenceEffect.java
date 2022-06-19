package Controller.CharacterEffects;

import Controller.IslandController;


/**
 * Character classes that have an effect on how influence is calculated inherit from this abstract class
 */
public abstract class InfluenceEffect extends CharacterEffect {

    protected IslandController ic;

    public InfluenceEffect(String playerID) {
        super(playerID);
    }
    //something
}
