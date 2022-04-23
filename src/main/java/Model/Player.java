package Model;
import Exceptions.AssistantMissingException;

import java.util.*;

public class Player {

    private String name;
    private String playerID;
    private int teamId;
    private Hand hand;
    private Assistant assistantInPlay;

    //TODO: maybe add playerID to constructor
    /**
     * Creates a new Player
     * @param n player's name
     * @param tid team ID
     */
    public Player(String n, int tid) {
        name = n;
        teamId = tid;
        hand = null;
    }

    public Player(String n) {
        name = n;
        teamId = -1;
        hand = null;
    }

    //TODO: what if two players choose the same wizard
    //TODO: handle if wizardID not between 1 and 4
    /**
     * Creates the player's hand from the wizardID
     * @param wizardID id of the wizard (identifies the hand aswell)
     */
    public void chooseWizard(int wizardID) {
        hand = new Hand(wizardID);
    }

    /**
     * Plays the corrisponding assistant, making it the assistant in play
     * @param orderNumber assistant's order number (identifier)
     * @return the assistant's now in play
     * @throws AssistantMissingException if the assistant is missing from the player's hand
     */
    public Assistant playAssistant(int orderNumber) throws AssistantMissingException {
        assistantInPlay = hand.getAssistantFromOrderNumber(orderNumber);
        return assistantInPlay;
    }

    /**
     * Set the player's ID
     * @param pid id to set
     */
    public void setPlayerID(String pid) { playerID = pid; }


    //Do i need to jdoc this?????????????????????
    public String getPlayerID() { return playerID; }

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
