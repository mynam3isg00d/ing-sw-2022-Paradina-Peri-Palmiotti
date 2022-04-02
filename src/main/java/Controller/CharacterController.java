package Controller;

import Controller.CharacterEffects.*;
import Model.Player;

import java.util.*;

public class CharacterController {
    List<CharacterEffect> characters;
    HashMap<Player, Integer> playerCoins;
    Game game;

    //grande :)

    public CharacterController(Game g) {
        game = g;
        playerCoins = new HashMap<>();
        for(Player p : game.getPlayers()) {
            playerCoins.put(p, 1);
        }
        characters = new ArrayList<>();
        chooseRandomCharacters();
    }

    private void chooseRandomCharacters() {
        return;
    }

    public void buyCharacter(Player p, int charIndex, Object playerInput) {
        CharacterEffect c = characters.get(charIndex);
        c.incrementCost();
        c.setBoughtBy(p);
        c.getData(game);
        c.playEffect(playerInput);
    }

}
