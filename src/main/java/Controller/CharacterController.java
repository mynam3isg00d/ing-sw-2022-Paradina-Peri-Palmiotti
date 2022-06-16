package Controller;

import Controller.CharacterEffects.*;
import Controller.CharacterEffects.FarmerEffect;
import Exceptions.InsufficientCoinsException;
import Exceptions.InvalidPlayerInputException;
import Model.CharacterCard;
import Model.Shop;
import Observer.View.RemoteView;

import java.util.List;

/**
 * Controller in charge of operations regarding character cards in the expert game
 */
public class CharacterController {

    private Shop shop;
    private ExpertGame gameReference;

    public CharacterController(ExpertGame g) {
        gameReference = g;
        shop = new Shop(gameReference.getPlayers());
        shop.initShop(gameReference.getSack());
    }

    public CharacterController(ExpertGame g, Integer[] shopList) {
        gameReference = g;
        shop = new Shop(gameReference.getPlayers(), shopList);
        shop.initShop(gameReference.getSack());
    }

    public void addObserverToModel(RemoteView rv) {
        shop.addObserver(rv);
    }


    /**
     * Method to buy and play a character card at the same time
     * @param cardIndex index of the card to buy
     * @param playerID ID of the player that is buying the card
     * @param playerInput the player chooses, among some options, how to play the character effect. These choices
     *                    are expressed in playerInput
     * @throws InsufficientCoinsException
     * @throws InvalidPlayerInputException
     * @throws Exception
     */
    public void buyCard(int cardIndex, String playerID, List<String> playerInput) throws InsufficientCoinsException, InvalidPlayerInputException, Exception {

        CharacterCard cc = shop.getShop()[cardIndex];

        if (shop.getPlayerCoins(playerID) < cc.getCost()) throw new InsufficientCoinsException();

        //Update model
        shop.removeCoins(playerID, cc.getCost());
        shop.incrementCost(cardIndex);

        //Get and play the effect
        CharacterEffect ce = getEffect(cc, playerID);
        ce.init(gameReference, cardIndex);
        ce.playEffect(playerInput);

        //Another async send to take StudentCard edits into account
        shop.sendShop();
    }


    /**
     * @param playerID ID of the player to whom coins are given
     * @param n number of coins given to the player
     */
    public void giveCoins(String playerID, int n) {
        shop.addCoins(playerID, n);
    }

    public Game getGameReference() {
        return gameReference;
    }
    public Shop getShopReference() { return shop; }

    /**
     * @param cc character card that we want the effect of
     * @param playerID player that is playing the character card. The effect can be
     *                 different for each player because it depends on what options
     *                 a player chooses when playing a character (see playerInput parameter in buyCard method).
     * @return the effect of the character card. Each effect has a class containing a method
     *         that allows to play the character's effect.
     */
    private static CharacterEffect getEffect(CharacterCard cc, String playerID) {
        int charID = cc.getCardID();
        switch(charID) {
            case 0: return new ClerkEffect(playerID);
            case 1: return new FarmerEffect(playerID);
            case 2: return new HeraldEffect(playerID);
            case 3: return new PostmanEffect(playerID);
            //case 4: return new GrandmaEffect();
            case 5: return new CentaurEffect(playerID);
            case 6: return new JesterEffect(playerID);
            case 7: return new KnightEffect(playerID);
            case 8: return new MushroomEffect(playerID);
            case 9: return new BardEffect(playerID);
            case 10: return new PrincessEffect(playerID);
            //case 11: return new ThiefEffect();
            default: return null;
        }
    }

}
