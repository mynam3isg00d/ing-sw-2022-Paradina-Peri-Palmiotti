package Controller.CharacterEffects;

import Controller.CharacterEffects.Strategies.KnightStrategy;
import Controller.ExpertGame;
import Exceptions.InvalidPlayerInputException;
import Model.Player;

import java.util.List;
import java.util.Objects;

//During the influence calculation this turn, you count as having two more influence
public class KnightEffect extends InfluenceEffect {

    private String playerID;
    private int teamID;

    public KnightEffect(String playerID) {
        super(playerID);
        this.playerID = playerID;
    }

    /**
     * Initializes the attributes with the appropriate references
     * @param g reference to expertGame
     * @param cardIndex index of the Knight character card
     */
    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
        for(Player p : g.getPlayers()) {
            if (Objects.equals(p.getPlayerID(), playerID)) teamID = p.getTeamID();
        }
    }

    /**
     * Sets the right influenceStrategy in the island controller.
     * @param playerInput this should be empty
     * @throws InvalidPlayerInputException The input is not empty as it should be
     * @throws Exception Something went wrong
     */
    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        // Expects:
        // {}
        if(playerInput.size() != 0) throw new InvalidPlayerInputException();

        ic.setInfluenceStrategy(new KnightStrategy(teamID));
    }
}
