package View;

import Controller.Game;
import Events.EventFactory;
import Events.GameEvent;
import Events.Move;
import Exceptions.NoSuchPlayerException;
import Model.*;

import java.io.PrintStream;
import java.util.*;


public class View extends Observable implements Runnable, Observer {

    //TODO: add notify and update;

    final private PrintStream output;
    private Scanner input;

    public View() {
        input = new Scanner(System.in);
        output = System.out;
    }

    @Override
    public void run(){

    }

    /*
    public void askChoice(String gamePhase) {
        output.println("What do you want to do?");
        switch (gamePhase) {
            case "actionPhase":
                output.println("1 - Move students to an island of your choice");
                output.println("2 - Move mother nature");
        }
        String in = input.nextLine();

    }
    */

    public void askChoice() throws NoSuchPlayerException {

        output.println("Select the letter corresponding to what you want to do: ");
        output.println("A - Play an assistant card");
        output.println("B - Move a student to dining room");
        output.println("C - Move a student to island");
        output.println("D - Move mother nature");
        output.println("E - Pick students from cloud");
        output.println("F - Buy and play character");
        output.println("G - Choose your wizard");

        String in = input.next();

        //currEvent variable might need to be protected when using concurrency
        GameEvent currEvent = null;

        try{
            Move choice = Enum.valueOf(Move.class, in.toUpperCase());
            switch (choice){
                case A:
                    currEvent = EventFactory.getEvent(choice);
                    output.println("Choose an assistant from you hand: " +
                                   "write tho orderNumber of the assistant you want to play");
                    break;

                case B:
                    currEvent = EventFactory.getEvent(choice);
                    output.println("Choose a student to move to the dining room by writing its color");
                    break;

                case C:
                    currEvent = EventFactory.getEvent(choice);
                    output.println("Choose a student and an island in this format: STUDENT ISLAND");
                    break;

                case D:
                    currEvent = EventFactory.getEvent(choice);
                    output.println("Choose number of steps to move mother nature");
                    break;

                case E:
                    currEvent = EventFactory.getEvent(choice);
                    output.println("Choose cloud");
                    //print all available cloudIDs
                    break;
                case F:
                    currEvent = EventFactory.getEvent(choice);
                    output.println("Choose character and insert parameters");
                    break;
                case G:
                    currEvent = EventFactory.getEvent(choice);
                    output.println("Choose a wizard");
                    //print available wizards
                    break;

            }

            in = input.next();

            //A seconda di che evento è il parsing viene fatto in modo diverso. IL'evento è stato istanziato sopra
            //chiamando il factory method, e qui chiamo il parser della classe che ho istanziato. Il parser salva i
            //parametri di input dentro gli attributi della classe.
            currEvent.parseInput(in);

            //Here a notification has to be sent to observers. currEvent needs to be handled by Game.


        }catch(IllegalArgumentException e){
            output.println("Invalid argument");
        }

    }

    /*
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
    */

    /*
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
     */

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

    @Override
    public void update(Observable o, Object arg) {

    }
}
