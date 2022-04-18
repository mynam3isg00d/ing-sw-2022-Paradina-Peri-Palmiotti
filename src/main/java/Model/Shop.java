//TODO: Jdocs

package Model;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private CharacterCard[] shop;

    public Shop() {
        shop = new CharacterCard[3];
    }

    public void setSlot(int index, CharacterCard cc) {
        try {
            shop[index] = cc;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void incrementCost(int index) {
        shop[index].incrementCost();
    }
}
