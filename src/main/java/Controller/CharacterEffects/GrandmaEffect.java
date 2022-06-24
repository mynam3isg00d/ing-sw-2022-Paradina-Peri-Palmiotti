package Controller.CharacterEffects;

import Controller.ExpertGame;
import Controller.IslandController;
import Exceptions.EmptyElementException;
import Exceptions.InvalidPlayerInputException;
import Model.NoTileCard;

import java.util.List;

public class GrandmaEffect extends CharacterEffect {

    private NoTileCard ntc;
    private IslandController ic;

    public GrandmaEffect(String playerID) {
        super(playerID);
    }

    @Override
    public String explainEffect() {
        return "Grandma.txt";
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        ic = g.getIslandController();
        ntc = (NoTileCard) g.getCharacterController().getShopReference().getShop()[cardIndex];
    }

    @Override
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        //This is the only card whose model can be modified outside of the "playEffect" method.
        //This is how it works (Only in ExpertGame, obviously):
        //  - the handleEvent for MoveMotherNature contains a line which checks the number of
        //    noEntryTiles present on the islands
        //  - based on this it calculates the new number of tiles for the card and calls a ntc.setNoTile(int n)

        //Expects:
        //{islandID : int}
        if (playerInput.size() != 1) throw new InvalidPlayerInputException();
        int islandID = Integer.parseInt(playerInput.get(0));

        if (ntc.getNoTile() <= 0) throw new EmptyElementException();

        //Modify island
        ic.setNoEntry(islandID);

        //Modify card
        ntc.removeNoTile();
    }
}
