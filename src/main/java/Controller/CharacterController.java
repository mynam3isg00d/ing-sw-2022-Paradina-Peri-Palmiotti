//TODO: deal with coins

package Controller;

import Controller.CharacterEffects.CharacterEffect;
import Controller.CharacterEffects.HeraldEffect;
import Model.CharacterCard;
import Model.Shop;

import java.util.List;

public class CharacterController {

    private Shop shop;
    private Game gameReference;

    public CharacterController(Game g) {
        shop = new Shop();
        gameReference = g;
    }

    public void buyCard(int cardIndex, List<Object> playerInput) {

        CharacterCard cc = shop.getShop()[cardIndex];
        CharacterEffect ce = getEffect(cc);
        ce.getData(gameReference);
        ce.playEffect(playerInput);
        //Modify model
        shop.incrementCost(cardIndex);

    }

    private static CharacterEffect getEffect(CharacterCard cc) {
        int charID = cc.getCardID();
        switch(charID) {
            //case 0: return new ClerkEffect();
            //case 1: return new FarmerEffect();
            case 2: return new HeraldEffect();
            //case 3: return new PostmanEffect();
            //case 4: return new GrandmaEffect();
            //case 5: return new CentaurEffect();
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
