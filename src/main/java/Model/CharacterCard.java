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
    int cardID;
    int cost;
    boolean isIncremented;

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
}
