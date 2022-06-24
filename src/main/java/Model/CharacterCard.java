package Model;

/**
 * Class that represents a character card, used in the expert game mode.
 *
 * CharacterIDs in order of appearance in the rulebook
 * 0 = Clerk
 * 1 = Farmer
 * 2 = Herald
 * 3 = Postman
 * 4 = Grandma
 * 5 = Centaur
 * 6 = Jester
 * 7 = Knight
 * 8 = Mushroom
 * 9 = Bard
 * 10 = Princess
 * 11 = Thief
 */
public class CharacterCard {

    private int cardID;
    private int cost;

    /**
     * This is set to true when a card is played, so the next time someone tries to buy it, the cost will be
     * incremented first and then shown to the buyer
     */
    private boolean isIncremented;

    public CharacterCard(int cardID) {
        this.cardID = cardID;
        this.cost = (cardID % 3) + 1;
        isIncremented = false;
    }


    /**
     * Increments the cost if the isIncremented attribute is set to false
     */
    public void incrementCost() {
        if(!isIncremented) {
            cost++;
            isIncremented = true;
        }
    }

    public CharacterCard getCopy() {
        CharacterCard copy = new CharacterCard(this.cardID);
        copy.cost = this.cost;
        copy.isIncremented = this.isIncremented;
        return copy;
    }

    public int getCardID() {
        return cardID;
    }

    public int getCost() {
        return cost;
    }

    public boolean isIncremented() {
        return isIncremented;
    }


    /**
     * This was made for testing purposes and is not an "ideal" equals,
     * in fact it does not check the cost and isIncremented attributes
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof CharacterCard)) return false;
        CharacterCard cc = (CharacterCard)o;
        if(cc.getCardID() == this.cardID) return true;
        return false;
    }
}
