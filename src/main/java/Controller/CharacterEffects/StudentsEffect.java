package Controller.CharacterEffects;

import Model.Sack;
import Model.StudentCard;

public abstract class StudentsEffect extends CharacterEffect {

    protected Sack sack;
    protected StudentCard sc;

    public StudentsEffect(String playerID) {
        super(playerID);
    }
}
