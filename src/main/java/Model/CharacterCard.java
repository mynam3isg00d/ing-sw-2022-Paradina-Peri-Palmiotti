//TODO: Jdocs

/*
CharacterIDs in order of apperance in the rulebook
0 = Clerk
1 = Farmer
2 = Herald
3 = Postman
4 = Grandma
5 = Centaur
6 = Jester
7 = Knight
8 = Mushroom
9 = Bard
10 = Princess
11 = Thief
 */

package Model;

public class CharacterCard {
    private int cardID;
    private int cost;
    private boolean isIncremented;

    //TODO: Connect cardID and cost somehow? maybe a big if
    public CharacterCard(int cardID) {
        this.cardID = cardID;
        this.cost = (cardID % 3) + 1;
        isIncremented = false;
    }

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

    //TODO: could be useful, but it was made for testing
    //      this means that it's not an *ideal* equals
    //      (for example it doesn't check isIncremented)
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof CharacterCard)) return false;
        CharacterCard cc = (CharacterCard)o;
        if(cc.getCardID() == this.cardID) return true;
        return false;
    }
}
