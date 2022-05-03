package Controller.CharacterEffects;

import Controller.CharacterEffects.Strategies.CentaurStrategy;
import Controller.CharacterEffects.Strategies.KnightStrategy;
import Controller.ExpertGame;
import Controller.IslandController;
import Model.Player;

import java.util.List;

public class KnightEffect extends InfluenceEffect {

    private String playerID;
    private int teamID;

    public KnightEffect(String playerID) {
        super(playerID);
        this.playerID = playerID;
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
        for(Player p : g.getPlayers()) {
            if (p.getPlayerID() == playerID) teamID = p.getTeamID();
        }
    }

    @Override
    public void playEffect(List<Object> playerInput) {
        ic.setInfluenceStrategy(new KnightStrategy(teamID));
    }
}
