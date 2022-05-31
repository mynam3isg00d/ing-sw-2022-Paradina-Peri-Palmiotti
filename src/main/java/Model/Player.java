package Model;
import Exceptions.AssistantMissingException;
import Exceptions.IllegalWizardException;
import Network.JsonFactory;
import Observer.Observable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Player extends Observable {

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

    public Player(String pid, String n, int tid) {
        playerID = pid;
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
    public void chooseWizard(int wizardID) throws  IllegalWizardException{
        if (wizardID < 0 || wizardID > 3) throw new IllegalWizardException();
        hand = new Hand(wizardID);

        sendPlayer();
    }

    /**
     * Plays the corrisponding assistant, making it the assistant in play
     * @param orderNumber assistant's order number (identifier)
     * @return the assistant's now in play
     * @throws AssistantMissingException if the assistant is missing from the player's hand
     */
    public Assistant playAssistant(int orderNumber) throws AssistantMissingException {
        assistantInPlay = hand.getAssistantFromOrderNumber(orderNumber);

        sendPlayer();
        return assistantInPlay;
    }

    /**
     * Plays the provided assistant and removes it from the hand
     * @param a
     */
    public void playAssistant(Assistant a) {
        assistantInPlay = a;
        getHand().removeAssistant(a);

        sendPlayer();
    }

    public void clearAssistant() {
        assistantInPlay = null;
    }

    /**
     * S
     * @param pid id to set
     */
    public void setPlayerID(String pid) { playerID = pid; }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return teamId == player.teamId && name.equals(player.name) && playerID.equals(player.playerID);
    }

    //what the fuck is this
    @Override
    public int hashCode() {
        return Objects.hash(name, playerID, teamId);
    }

    public void sendPlayer() {
        String s = new JsonFactory().modelToJson(this);
        notify(s);
    }
}
