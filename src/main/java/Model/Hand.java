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

    /**
     * Gets the Assistant card in the selected position, without removing it
     * @param index position of the assistant
     * @return the assistant in the selected position
     * @throws AssistantMissingException The position given is out of range: there is no such position in this hand
     */
    public Assistant getAssistantByIndex(int index) throws AssistantMissingException {
        if (index >= hand.size()) throw new AssistantMissingException();
        else return hand.get(index);
    }

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

    public void removeAssistant(Assistant a) {
        hand.remove(a);
    }
}
