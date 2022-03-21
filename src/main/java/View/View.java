package View;

import Model.*;
import java.util.*;

public class View {

    private Game g;
    private Scanner s;

    public View(Game g) {
        this.g = g;
        s = new Scanner(System.in);
    }

    public int getPlayedAssistant(Player p) {
        int assistantChoice;
        System.out.println(p.getName() + ", play a card: ");
        Hand h = p.getHand();
        printPlayerHand(h);
        assistantChoice = s.nextInt();
        while(!h.isAssistantInHand(assistantChoice)) {
            System.out.println("This assistant is not in your hand!! Choose another one: ");
            printPlayerHand(h);
            assistantChoice = s.nextInt();
        }
        return assistantChoice;
    }

    public int getWizard(Player p, boolean[] availableWizards) {
        int wizardChoice;

        System.out.println(p.getName() + ", choose your starting deck: ");
        printAvailableWizards(availableWizards);
        wizardChoice = s.nextInt();
        while(!availableWizards[wizardChoice]) {
            System.out.println("yo you can't choose this one fool, it's already been taken.");
            printAvailableWizards(availableWizards);
            wizardChoice = s.nextInt();
        }
        return wizardChoice;
    }

    public static void printAvailableWizards(boolean[] aW) {
        for(int i = 0; i < aW.length; i++) {
            if (aW[i]) System.out.print("[Wizard " + i + "]");
        }
    }

    public static void printPlayerHand(Hand h) {
        for(Assistant a : h.getHand()) {
            System.out.print("[" + a.getOrderNumber() + "|" + a.getMotherNumber() + "]");
        }
        System.out.println("\n");
    }
}
