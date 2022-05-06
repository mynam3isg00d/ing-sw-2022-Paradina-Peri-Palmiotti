//TODO: Jdocs
//TODO: keep coin information here maybe??

package Model;


import Exceptions.EmptySackException;

import java.util.*;

public class Shop extends Observable {

    private Integer[] AVAILABLE_CHARS = {0, 2, 5, 6, 7, 8, 9, 10};
    //private Integer[] AVAILABLE_CHARS = {0, 2, 5, 7, 8};
    private CharacterCard[] shop;
    private HashMap<String, Integer> coinMap;

    public Shop(List<Player> playerList) {
        shop = new CharacterCard[3];
        coinMap = new HashMap<>();

        for(Player p : playerList) {
            coinMap.put(p.getPlayerID(), 1);
        }
    }

    //Mainly for testing purposes
    public Shop(List<Player> playerList, Integer[] AVAILABLE_CHARS) {
        shop = new CharacterCard[3];
        this.AVAILABLE_CHARS = AVAILABLE_CHARS;
        coinMap = new HashMap<>();

        for(Player p : playerList) {
            coinMap.put(p.getPlayerID(), 1);
        }
    }

    public void initShop(Sack s) {
        List<Integer> charIndex = Arrays.asList(AVAILABLE_CHARS.clone());
        Collections.shuffle(charIndex);
        try {
            fillShop(charIndex.subList(0, 3), s);
        } catch (EmptySackException e) {
            e.printStackTrace();
        }
    }

    public void addCoins(String playerID, int num) {
        int prev = coinMap.get(playerID);
        coinMap.put(playerID, prev + num);
    }

    public void removeCoins(String playerID, int num) {

        //remember to update only the addCoins
        addCoins(playerID, -num);
    }

    public int getPlayerCoins(String playerID) {
        return coinMap.get(playerID);
    }

    //TODO: maybe use factory!!!
    public void fillShop(List<Integer> indexArray, Sack sack) throws EmptySackException {
        if (indexArray.size() != 3) return;
        for(int i=0; i<indexArray.size(); i++) {
            int c = indexArray.get(i);
            switch(c) {
                case 0:
                case 10:
                    shop[i] = new StudentCard(c, sack.draw(4)); break;
                case 6:
                    shop[i] = new StudentCard(c, sack.draw(6)); break;
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
}
