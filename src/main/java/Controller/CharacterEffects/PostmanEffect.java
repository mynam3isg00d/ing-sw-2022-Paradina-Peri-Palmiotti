package Controller.CharacterEffects;

import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;

import java.util.List;

public class PostmanEffect extends CharacterEffect{

    private ExpertGame g;
    public PostmanEffect(String playerID) {
        super(playerID);
    }

    @Override
    public String explainEffect() {
        return "You may move Mother Nature up to 2 additional islands than is indicated by the assistant card you played";
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        this.g = g;
    }

    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {
        g.setPostmanActive(true);
    }
}