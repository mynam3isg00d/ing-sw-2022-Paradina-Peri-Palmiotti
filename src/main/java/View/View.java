package View;

import Controller.Game;
import Model.*;

import java.io.PrintStream;
import java.util.*;

public class View {
    final private PrintStream output;
    private Scanner input;

    public View() {
        input = new Scanner(System.in);
        output = System.out;
    }

    public void askChoice(String gamePhase) {
        output.println("Che vuoi fare?");
        switch (gamePhase) {
            case "actionPhase":
                output.println("1 - Sposta degli studenti su un'isola a scelta");
                output.println("2 - Muovi madre natura");
        }
        String in = input.nextLine();

    }


    public int getPlayedAssistant(Player p) {
        int assistantChoice;
        System.out.println(p.getName() + ", play a card: ");
        Hand h = p.getHand();
        printPlayerHand(h);
        assistantChoice = input.nextInt();
        while(!h.isAssistantInHand(assistantChoice)) {
            System.out.println("This assistant is not in your hand!! Choose another one: ");
            printPlayerHand(h);
            assistantChoice = input.nextInt();
        }
        return assistantChoice;
    }

    public int getWizard(Player p, boolean[] availableWizards) {
        int wizardChoice;

        System.out.println(p.getName() + ", choose your starting deck: ");
        printAvailableWizards(availableWizards);
        wizardChoice = input.nextInt();
        while(!availableWizards[wizardChoice]) {
            System.out.println("yo you can't choose this one fool, it's already been taken.");
            printAvailableWizards(availableWizards);
            wizardChoice = input.nextInt();
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
