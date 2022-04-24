package Controller;

import Controller.CharacterEffects.CharacterEffect;
import Model.CharacterCard;
import Model.Shop;

public class CharacterController {

    private CharacterEffect[] characterEffects;
    private Shop shop;

    public CharacterController() {
        characterEffects = new CharacterEffect[3];
        shop = new Shop();
        getRandomEffects();
    }

    private void getRandomEffects() {
        /*
        for(int i=0; i<shop.getShop().length; i++) {
            characterEffects[i] =
        }
        */
    }

}
