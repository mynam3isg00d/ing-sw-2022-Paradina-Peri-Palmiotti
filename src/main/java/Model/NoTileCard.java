package Model;

import Exceptions.EmptyElementException;
import Exceptions.FullElementException;

public class NoTileCard extends CharacterCard {

    private final int MAX_NOTILES = 4;
    private int numOfNoTiles;

    public NoTileCard(int cardID) {
        super(cardID);
        numOfNoTiles = MAX_NOTILES;
    }

    /**
     * Used to add a No Entry Tile to the card.
     * @throws FullElementException if its capacity is at max
     */
    public void addNoTile() throws FullElementException {
        if(numOfNoTiles >= MAX_NOTILES) {
            throw new FullElementException();
        } else {
            numOfNoTiles++;
        }
    }

    /**
     * Removes a No Entry Tile from the card.
     * @throws EmptyElementException if there are no No Entry Tiles
     */
    public void removeNoTile() throws EmptyElementException {
        if(numOfNoTiles <= 0) {
            throw new EmptyElementException();
        } else {
            numOfNoTiles--;
        }
    }

    public void setNoTile(int num) {
        if(num < 0 || num > 4) throw new IllegalArgumentException();
        else {
            numOfNoTiles = num;
        }
    }

    public int getNoTile() {return numOfNoTiles;}

}
