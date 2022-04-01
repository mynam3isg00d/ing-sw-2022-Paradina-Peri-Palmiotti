package Controller.CharacterEffects;

import Controller.Game;
import Model.*;

public abstract class CharacterEffect {
    int cost;
    boolean isIncremented;
    Player boughtBy;

    public CharacterEffect() {
        isIncremented = false;
    }

    public abstract void getData(Game g);
    public abstract void playEffect(Object playerInput);

    public void incrementCost() {
        if (!isIncremented) cost++;
        isIncremented = true;
    }

    public void setBoughtBy(Player boughtBy) {
        this.boughtBy = boughtBy;
    }
}
