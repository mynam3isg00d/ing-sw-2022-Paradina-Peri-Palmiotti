package Model;
import Exceptions.AssistantMissingException;

import java.util.*;

public class Player {

    private String name;
    private String playerID;
    private int teamId;
    private Hand hand;
    private Stack discardPile;
    private Assistant assistantInPlay;

    public Player(String n, int tid) {
        name = n;
        teamId = tid;
        playerID = "testID";
        hand = null;
        discardPile = new Stack();
    }

    public void chooseWizard(int wizardID) {
        hand = new Hand(wizardID);
    }

    public String getPlayerID() { return playerID; }

    //At the bottom a mockup of a use of playAssistant and returnAssistantToHand
    public Assistant playAssistant(int orderNumber) throws AssistantMissingException {
        assistantInPlay = hand.getAssistantFromOrderNumber(orderNumber);
        return assistantInPlay;
    }

    public void returnAssistantToHand(Assistant a) {
        hand.addAssistant(a);
    }

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public int getTeamID() {
        return teamId;
    }

    public Assistant getAssistantInPlay() { return assistantInPlay; }
}
