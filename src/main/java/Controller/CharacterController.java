package Controller;

import Controller.CharacterEffects.CharacterEffect;
import Model.Shop;

public class CharacterController {

    private CharacterEffect[] characterEffects;
    private Shop shop;

    public CharacterController() {
        characterEffects = new CharacterEffect[3];
        getRandomEffects();
        shop = new Shop();
    }

    private void getRandomEffects() {
        //switch case with random
    }

}
