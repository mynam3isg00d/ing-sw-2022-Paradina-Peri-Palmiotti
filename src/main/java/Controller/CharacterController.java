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

    /*
    Prendi il riferimento della carta dallo shop, usando l'index e poi gioca l'effetto
    */
    public void buyCard(int cardIndex, List<Object> playerInput) {

        CharacterCard cc = shop.getShop()[cardIndex];
        CharacterEffect ce = getEffect(cc);
        ce.getData(gameReference);
        ce.playEffect(playerInput);

        /*
        //Play effect
        CharacterEffect ce = characterEffects[cardIndex];
        ce.getData(gameReference);
        ce.playEffect(playerInput);

        //Modify model
        shop.incrementCost(cardIndex);
        */
    }

    private static CharacterEffect getEffect(CharacterCard cc) {
        int charID = cc.getCardID();
        switch(charID) {
            //case 0: return new ClerkEffect(cc);
            //case 1: return new FarmerEffect(cc);
            case 2: return new HeraldEffect();
            //case 3: return new PostmanEffect(cc);
            //case 4: return new GrandmaEffect(cc);
            //case 5: return new CentaurEffect(cc);
            //case 6: return new JesterEffect(cc);
            //case 7: return new KnightEffect(cc);
            //case 8: return new MushroomEffect(cc);
            //case 9: return new BardEffect(cc);
            //case 10: return new PrincessEffect(cc);
            //case 11: return new ThiefEffect(cc);
            default: return null;
        }
    }

}
