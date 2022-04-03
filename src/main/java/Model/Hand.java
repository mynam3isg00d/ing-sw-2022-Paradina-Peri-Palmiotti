package Model;
import Exceptions.AssistantMissingException;

import java.util.*;

public class Hand {
    private int wizardID;
    private List<Assistant> hand;

    public Hand(int wizardID) {
        this.wizardID = wizardID;
        hand = new ArrayList<>();
        for(int i = 1; i <= 10; i++) {
            hand.add(new Assistant(i));
        }
    }

    public Assistant getAssistantFromOrderNumber(int on) throws AssistantMissingException {
        for(Assistant a : hand) {
            if (a.getOrderNumber() == on) {
                hand.remove(a);
                return a;
            }
        }
        throw new AssistantMissingException();
    }

    public List<Assistant> getHand() {
        return hand;
    }

    //TODO: check if it's a valid assistant (it should be because
    //      Hand is the only one able to create assistants...)
    //      (therefore maybe unnecessary...)
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
