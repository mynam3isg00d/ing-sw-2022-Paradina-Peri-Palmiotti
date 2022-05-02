package Controller.CharacterEffects;

import Model.Sack;

public abstract class StudentsEffect extends CharacterEffect {

    protected Sack sack;

    public StudentsEffect(int playerID) {
        super(playerID);
    }
}
