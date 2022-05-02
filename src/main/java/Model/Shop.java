//TODO: Jdocs
//TODO: keep coin information here maybe??

package Model;


import java.util.*;

public class Shop extends Observable {

    private final Integer[] AVAILABLE_CHARS = {0, 2, 5, 6, 7, 8, 9, 10};
    private CharacterCard[] shop;
    private HashMap<String, Integer> coinMap;

    public Shop(List<Player> playerList) {

        shop = new CharacterCard[3];
        coinMap = new HashMap<>();

        for(Player p : playerList) {
            coinMap.put(p.getPlayerID(), 1);
        }

        List<Integer> charIndex = Arrays.asList(AVAILABLE_CHARS.clone());
        Collections.shuffle(charIndex);
        fillShop(charIndex.subList(0, 3));
    }

    //Mainly for testing purposes
    public Shop(Integer[] AVAILABLE_CHARS) {
        shop = new CharacterCard[3];
        List<Integer> charIndex = Arrays.asList(AVAILABLE_CHARS.clone());
        Collections.shuffle(charIndex);
        fillShop(charIndex.subList(0, 3));
    }

    public void addCoins(String playerID, int num) {
        int prev = coinMap.get(playerID);
        coinMap.put(playerID, prev + num);
    }

    public void removeCoins(String playerID, int num) {

        //remember to update only the addCoins
        addCoins(playerID, -num);
    }


    //TODO: maybe use factory!!!
    public void fillShop(List<Integer> indexArray) {
        if (indexArray.size() != 3) return;
        for(int i=0; i<indexArray.size(); i++) {
            int c = indexArray.get(i);
            switch(c) {
                case 0:
                case 10:
                    shop[i] = new StudentCard(c, 4); break;
                case 6:
                    shop[i] = new StudentCard(c, 6); break;
                default:
                    shop[i] = new CharacterCard(c); break;
            }
        }
    }

    //TODO: this is wrong
    //      it returns a shop that has refrences to the original cards
    //      meaning the cards themselves are not copies! (idk why it happens tho)
    public CharacterCard[] getShop() {
        return shop.clone();
    }

    public void incrementCost(int index) {
        shop[index].incrementCost();
    }

    @Override
    public Object clone() {
        CharacterCard[] clonedShop = new CharacterCard[shop.length];
        for(int i=0; i<3; i++) clonedShop[i] = shop[i].getCopy();
        return clonedShop;
    }
}
