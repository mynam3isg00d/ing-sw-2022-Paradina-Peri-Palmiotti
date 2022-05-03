package Controller.CharacterEffects;

import Model.Sack;

public abstract class StudentsEffect extends CharacterEffect {

    protected Sack sack;

    public StudentsEffect(String playerID) {
        super(playerID);
    }
}
