//TODO: Jdocs

package Model;

public class CharacterCard {
    int cardID;
    int cost;
    boolean isIncremented;

    //TODO: Connect cardID and cost somehow? maybe a big if
    public CharacterCard(int cardID, int cost) {
        this.cardID = cardID;
        this.cost = cost;
        isIncremented = false;
    }

    public void incrementCost() {
        if(!isIncremented) {
            cost++;
            isIncremented = true;
        }
    }
}
