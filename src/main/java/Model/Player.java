package Model;
import java.util.*;

public class Player {

    private String name;
    private int teamId;
    private Hand hand;
    private Stack discardPile;
    private Assistant assistantInPlay;

    public Player(String n, int tid) {
        name = n;
        teamId = tid;
        hand = null;
        discardPile = new Stack();
    }

    public void chooseWizard(int wizardID) {
        hand = new Hand(wizardID);
    }

    //At the bottom a mockup of a use of playAssistant and returnAssistantToHand
    public Assistant playAssistant(int orderNumber) {
        assistantInPlay = hand.getAssistantFromOrderNumber(orderNumber);
        return assistantInPlay;
    }

    public void returnAssistantToHand(Assistant a) {
        hand.addAssistant(a);
    }
    //At the bottom a mockup of a use of playAssistant and returnAssistantToHand

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public int getTeamId() {
        return teamId;
    }

    public Assistant getAssistantInPlay() { return assistantInPlay; }
}

/*
        for(Player p : players) {
            System.out.println(p.getName() + " play a card: ");
            printHand(p.getHand());
            Assistant toPlay = p.playAssistant(s.nextInt());
            while(isInPlay(playedAssistants, toPlay)) {
                p.returnAssistantToHand(toPlay);
                System.out.println("This assistant has already been played, choose another one: ");
                printHand(p.getHand());
                toPlay = p.playAssistant(s.nextInt());
            }
            playedAssistants.add(toPlay);
        }

With isInPlay a function that checks if the chosen assistant has been played
*/
