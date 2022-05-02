package Controller;

import Controller.CharacterEffects.CentaurEffect;
import Controller.CharacterEffects.CharacterEffect;
import Controller.CharacterEffects.ClerkEffect;
import Controller.CharacterEffects.HeraldEffect;
import Model.CharacterCard;
import Model.Shop;
import View.RemoteView;

import java.util.List;

public class CharacterController {

    private Shop shop;
    private Game gameReference;

    public CharacterController(Game g) {
        gameReference = g;
        shop = new Shop(gameReference.getPlayers());
    }

    public void addObserverToModel(RemoteView rv) {
        shop.addObserver(rv);
    }

    public void buyCard(int cardIndex, int playerID, List<Object> playerInput) {

        CharacterCard cc = shop.getShop()[cardIndex];
        CharacterEffect ce = getEffect(cc, playerID);
        ce.init(gameReference);
        ce.playEffect(playerInput);
        shop.incrementCost(cardIndex);
    }

    public Game getGameReference() {
        return gameReference;
    }

    private static CharacterEffect getEffect(CharacterCard cc, int playerID) {
        int charID = cc.getCardID();
        switch(charID) {
            case 0: return new ClerkEffect(playerID);
            //case 1: return new FarmerEffect();
            case 2: return new HeraldEffect(playerID);
            //case 3: return new PostmanEffect();
            //case 4: return new GrandmaEffect();
            case 5: return new CentaurEffect(playerID);
            //case 6: return new JesterEffect();
            //case 7: return new KnightEffect();
            //case 8: return new MushroomEffect();
            //case 9: return new BardEffect();
            //case 10: return new PrincessEffect();
            //case 11: return new ThiefEffect();
            default: return null;
        }
    }

}
