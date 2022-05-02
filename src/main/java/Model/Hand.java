package Model;
import Exceptions.AssistantMissingException;

import java.util.*;

public class Hand {
    private final int wizardID;
    private List<Assistant> hand;

    /**
     * Creates a new hand
     * @param wizardID wizard id (hand identifier)
     */
    public Hand(int wizardID) {
        this.wizardID = wizardID;
        hand = new ArrayList<>();
        for(int i = 1; i <= 10; i++) {
            hand.add(new Assistant(i));
        }
    }

    /**
     * Gets the assistant from the hand based on order number and
     * removes it if present
     * @param on order number
     * @return the assistant of the corresponding on
     * @throws AssistantMissingException if the assistant is missing from the hand
     */
    public Assistant getAssistantFromOrderNumber(int on) throws AssistantMissingException {
        for(Assistant a : hand) {
            if (a.getOrderNumber() == on) {
                hand.remove(a);
                return a;
            }
        }
        throw new AssistantMissingException();
    }

    //TODO: check if it's a valid assistant (it should be because
    //      Hand is the only one able to create assistants...)
    //      (therefore maybe unnecessary...)

    /**
     * Adds assistant to hand
     * @param a assistant to add
     */
    public void addAssistant(Assistant a) {
        hand.add(a);
    }

    /**
     * Checks if assistant is in hand
     * @param on order number
     * @return true if assistant in hand, else false
     */
    public boolean isAssistantInHand(int on) {
        for(Assistant a : hand) {
            if (a.getOrderNumber() == on) {
                return true;
            }
        }
        return false;
    }

    public int getHandSize() { return hand.size(); }

    public List<Assistant> getHand() {
        return new ArrayList<>(hand);
    }

    public int getWizardID() {
        return wizardID;
    }
}
