package Controller.CharacterEffects;

import Controller.Game;
import Controller.IslandController;
import Model.CharacterCard;
import Model.Sack;
import Model.Shop;
import Model.StudentCard;

import java.util.List;

public class ClerkEffect extends StudentsEffect {

    private Sack sack;
    private StudentCard sc;
    private IslandController ic;

    public ClerkEffect(int playerID) {
        super(playerID);
    }

    @Override
    public void init(Game g) {
        sack = g.getSack();

    }

    @Override
    public void playEffect(List<Object> playerInput) {

    }
}
