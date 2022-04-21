//TODO: Jdocs
//TODO: keep coin information here maybe??

package Model;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Shop {

    private final Integer[] AVAILABLE_CHARS = {0, 2, 5, 6, 7, 8, 9, 10};
    private CharacterCard[] shop;

    //TODO: THIS DEFINITELY DOESNT WORK

    public Shop() {
        shop = new CharacterCard[3];
        List<Integer> charIndex = Arrays.asList(AVAILABLE_CHARS.clone());
        Collections.shuffle(charIndex);
        fillShop((Integer[]) charIndex.toArray());
    }

    private void fillShop(Integer[] indexArray) {
        if (indexArray.length != 3) return;
        for(int i=0; i<indexArray.length; i++) {
            int c = indexArray[i];
            switch(c) {
                case 0: shop[i] = new StudentCard(c, 4); break;
                case 6: shop[i] = new StudentCard(c, 6); break;
                case 10: shop[i] = new StudentCard(c, 4); break;
                default: shop[i] = new CharacterCard(c); break;
            }
        }
    }

    public void incrementCost(int index) {
        shop[index].incrementCost();
    }
}
