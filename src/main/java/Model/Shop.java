package Model;

import Exceptions.EmptySackException;
import Network.JsonFactory;
import Observer.Observable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Shop extends Observable {

    private Integer[] AVAILABLE_CHARS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private CharacterCard[] shop;
    private HashMap<String, Integer> coinMap;
    private List<Player> playerList;

    public Shop(List<Player> playerList) {
        shop = new CharacterCard[3];
        coinMap = new HashMap<>();
        this.playerList = playerList;

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

    /**
     * Initializes the shop with 3 randomly selected character cards
     * @param s The sack to be used by the shop. This will be used by the fillShop method if
     *          there are student cards to be filled with students, which will be drawn from
     *          the sack
     */
    public void initShop(Sack s) {
        List<Integer> charIndex = Arrays.asList(AVAILABLE_CHARS.clone());
        Collections.shuffle(charIndex);
        try {
            fillShop(charIndex.subList(0, 3), s);
        } catch (EmptySackException e) {
            e.printStackTrace();
        }

        sendShop();
    }

    /**
     * Gives coins to a player
     * @param playerID The ID of the player that recieves the coins
     * @param num number of coins to give
     */
    public void addCoins(String playerID, int num) {
        int prev = coinMap.get(playerID);
        coinMap.put(playerID, prev + num);

        sendShop();
    }

    /**
     * Takes coins from a player
     * @param playerID The ID of the player that coins are taken from
     * @param num number of coins taken
     */
    public void removeCoins(String playerID, int num) {

        //remember to update only the addCoins
        addCoins(playerID, -num);
    }

    /**
     * Returns how many coins the selected player has
     * @param playerID The ID of the selected players
     * @return How many coins the selected player has
     */
    public int getPlayerCoins(String playerID) {
        return coinMap.get(playerID);
    }

    /**
     * Fills the shop with 3 given character cards and fills the student cards if there are any
     * @param indexArray Contains the indexes of the character cards that the shop should be filled with
     * @param sack The sack used to draw students to fill the student cards
     * @throws EmptySackException The sack is empty, can't draw any students to fill the student card
     */
    private void fillShop(List<Integer> indexArray, Sack sack) throws EmptySackException {
        if (indexArray.size() != 3) return;
        for(int i=0; i<indexArray.size(); i++) {
            int c = indexArray.get(i);
            switch(c) {
                case 0:
                case 10:
                    shop[i] = new StudentCard(c, sack.draw(4)); break;
                case 6:
                    shop[i] = new StudentCard(c, sack.draw(6)); break;
                case 4:
                    shop[i] = new NoTileCard(c); break;
                default:
                    shop[i] = new CharacterCard(c); break;
            }
        }
    }

    public CharacterCard[] getShop() {
        return shop.clone();
    }

    /**
     * Increments the cost of a character card
     * @param index The index of the selected card
     */
    public void incrementCost(int index) {
        shop[index].incrementCost();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setCard(CharacterCard cc, int i) {
        shop[i] = cc;
    }

    /**
     * Sends the instance to the class' observers
     */
    public void sendShop() {
        String s = new JsonFactory().modelToJson(this);
        notify(s);
    }
}


