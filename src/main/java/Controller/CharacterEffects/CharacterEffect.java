package Controller.CharacterEffects;

import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

public abstract class CharacterEffect {

    protected String boughtByID;


    public CharacterEffect(String playerID) {
        boughtByID = playerID;
    }

    public abstract void init(ExpertGame g, int cardIndex);

    //TODO: Generic Exception should be replaced with a "Model Related Exception" for clarity
    public abstract void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception;

}
