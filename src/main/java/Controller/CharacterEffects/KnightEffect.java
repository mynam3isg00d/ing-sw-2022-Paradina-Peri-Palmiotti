package Controller.CharacterEffects;

import Controller.CharacterEffects.Strategies.KnightStrategy;
import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;
import Model.Player;

import java.util.List;
import java.util.Objects;

public class KnightEffect extends InfluenceEffect {

    private String playerID;
    private int teamID;

    @Override
    public String explainEffect() {
        return "During the influence calculation this turn, you count as having two more influence.";
    }

    public KnightEffect(String playerID) {
        super(playerID);
        this.playerID = playerID;
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
        for(Player p : g.getPlayers()) {
            if (Objects.equals(p.getPlayerID(), playerID)) teamID = p.getTeamID();
        }
    }

    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        // Expects:
        // {}
        if(playerInput.size() != 0) throw new InvalidPlayerInputException();

        ic.setInfluenceStrategy(new KnightStrategy(teamID));
    }
}
