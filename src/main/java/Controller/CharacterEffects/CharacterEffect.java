package Controller.CharacterEffects;

import Controller.Game;
import Model.Shop;

import java.util.List;

public abstract class CharacterEffect {

    protected int boughtByID;
    protected int expectedInputSize;

    public CharacterEffect(int playerID) {
        boughtByID = playerID;
    }

    //Init requires the which holds:
    public abstract void init(Game g);

    //QUI le strategy vengono inizializzate, passando nei costruttori i player input che servono
    //OPPURE bisogna mettere un metodo per cambiarlo
    public abstract void playEffect(List<Object> playerInput);
}
