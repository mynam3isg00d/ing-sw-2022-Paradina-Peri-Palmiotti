package Model;
import java.util.*;

public class Hand {
    private int wizardID;
    private List<Assistant> hand;

    public Hand(int wizardID) {
        this.wizardID = wizardID;
        hand = new ArrayList<Assistant>();
        for(int i = 1; i <= 10; i++) {
            hand.add(new Assistant(i));
        }
    }

    public Assistant getAssistantFromOrderNumber(int on) {
        for(Assistant a : hand) {
            if (a.getOrderNumber() == on) {
                hand.remove(a);
                return a;
            }
        }
        System.out.println("ERROR no assistant found for that number");
        return null;
    }

    public List<Assistant> getHand() {
        return hand;
    }

    public void addAssistant(Assistant a) {
        hand.add(a);
    }

    public boolean isAssistantInHand(int on) {
        for(Assistant a : hand) {
            if (a.getOrderNumber() == on) {
                return true;
            }
        }
        return false;
    }
}
