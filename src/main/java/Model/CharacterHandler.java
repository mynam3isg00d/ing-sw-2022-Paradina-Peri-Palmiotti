package Model;

import Model.Characters.*;
import java.util.*;

public class CharacterHandler {
    List<AbstractCharacter> characters;
    HashMap<Player, Integer> playerCoins;
    Game game;

    public CharacterHandler(Game g) {
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
        AbstractCharacter c = characters.get(charIndex);
        c.incrementCost();
        c.setBoughtBy(p);
        c.getData(game);
        c.playEffect(playerInput);
    }

}
