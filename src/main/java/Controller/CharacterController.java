package Controller;

import Controller.CharacterEffects.*;
import Exceptions.InsufficientCoinsException;
import Exceptions.InvalidPlayerInputException;
import Model.CharacterCard;
import Model.Shop;
import View.RemoteView;

import java.util.List;

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

    public void buyCard(int cardIndex, String playerID, List<Object> playerInput) throws InsufficientCoinsException, InvalidPlayerInputException {

        CharacterCard cc = shop.getShop()[cardIndex];

        if (shop.getPlayerCoins(playerID) < cc.getCost()) throw new InsufficientCoinsException();

        //Update model
        shop.removeCoins(playerID, cc.getCost());
        shop.incrementCost(cardIndex);

        //Get and play the effect
        CharacterEffect ce = getEffect(cc, playerID);
        ce.init(gameReference, cardIndex);
        ce.playEffect(playerInput);
    }

    public void giveCoins(String playerID, int n) {
        shop.addCoins(playerID, n);
    }

    public Game getGameReference() {
        return gameReference;
    }
    public Shop getShopReference() { return shop; }

    private static CharacterEffect getEffect(CharacterCard cc, String playerID) {
        int charID = cc.getCardID();
        switch(charID) {
            case 0: return new ClerkEffect(playerID);
            //case 1: return new FarmerEffect();
            case 2: return new HeraldEffect(playerID);
            //case 3: return new PostmanEffect();
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
