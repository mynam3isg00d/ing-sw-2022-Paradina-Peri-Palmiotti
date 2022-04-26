package Controller.CharacterEffects;

import Controller.CharacterEffects.Strategies.Strategy;
import Controller.Game;
import Model.*;

import java.util.List;

public abstract class CharacterEffect {

    protected CharacterCard card;
    protected Player boughtBy;
    protected int expectedInputSize;

    //TODO così??
    protected Strategy strategy;

    public CharacterEffect() {

    }

    public abstract void getData(Game g);

    //QUI le strategy vengono inizializzate, passando nei costruttori i player input che servono
    //OPPURE bisogna mettere un metodo per cambiarlo
    public abstract void playEffect(List<Object> playerInput);

}
