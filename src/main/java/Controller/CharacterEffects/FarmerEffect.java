package Controller.CharacterEffects;

import Controller.BoardsController;
import Controller.CharacterEffects.Strategies.FarmerStrategy;
import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

public class FarmerEffect extends CharacterEffect{

    private BoardsController bc;
    public FarmerEffect(String playerID) {
        super(playerID);
    }

    @Override
    public String explainEffect() {
        return "During this turn, you take control of any number of Professors even if you have the same number of Students as the player who currently controls them";
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        bc = g.getBoardsController();
    }

    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {
        // Expects:
        // {}
        if(playerInput.size() != 0) throw new InvalidPlayerInputException();
        bc.setProfessorStrategy(new FarmerStrategy());
    }
}
