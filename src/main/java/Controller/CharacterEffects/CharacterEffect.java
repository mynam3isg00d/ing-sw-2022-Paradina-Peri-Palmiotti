package Controller.CharacterEffects;

import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

public abstract class CharacterEffect {

    protected String boughtByID;

    public abstract String explainEffect();
    public CharacterEffect(String playerID) {
        boughtByID = playerID;
    }

    //Init requires the which holds:
    public abstract void init(ExpertGame g, int cardIndex);

    //QUI le strategy vengono inizializzate, passando nei costruttori i player input che servono
    //OPPURE bisogna mettere un metodo per cambiarlo

    //TODO: Generic Exception should be replaced with a "Model Related Exception" for clarity
    public abstract void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception;

}
