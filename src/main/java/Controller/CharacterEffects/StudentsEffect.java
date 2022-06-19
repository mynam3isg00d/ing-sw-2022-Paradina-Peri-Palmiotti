package Controller.CharacterEffects;

import Model.Sack;
import Model.StudentCard;

/**
 * Character classes that have an effect that involve StudentCard inherit from this abstract class
 */
public abstract class StudentsEffect extends CharacterEffect {

    protected Sack sack;
    protected StudentCard sc;

    public StudentsEffect(String playerID) {
        super(playerID);
    }
}
