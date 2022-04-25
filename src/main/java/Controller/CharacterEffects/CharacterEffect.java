package Controller.CharacterEffects;

import Controller.Game;
import Model.*;

import java.util.List;

public abstract class CharacterEffect {

    protected CharacterCard card;
    protected Player boughtBy;
    protected int expectedInputSize;

    public CharacterEffect() {

    }

    public abstract void getData(Game g);
    public abstract void playEffect(List<Object> playerInput);

}
