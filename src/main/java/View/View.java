package View;

import Events.EventFactory;
import Events.GameEvent;
import Events.Move;
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

    public void askChoice(){

        output.println("Select the letter corresponding to what you want to do: ");
        output.println("A - Play an assistant card");
        output.println("B - Move a student to dining room");
        output.println("C - Move a student to island");
        output.println("D - Move mother nature");
        output.println("E - Pick students from cloud");
        output.println("F - Buy and play character");
        output.println("G - Choose your wizard");

        String in = input.next();
        GameEvent currEvent = null;
        try{
            Move choice = Enum.valueOf(Move.class, in.toUpperCase());
            switch (choice){
                case A:
                    currEvent = EventFactory.getEvent(choice);
                    output.println("Choose an assistant from you hand");
                    break;
                case B:
                    currEvent = EventFactory.getEvent(choice);
                    output.println("Choose a student and a dining room in this format: STUDENT, DINING ROOM");
                    //print all available studentIDs and dining room IDs
                    break;
                case C:
                    currEvent = EventFactory.getEvent(choice);
                    output.println("Choose a student and an island in this format: STUDENT, ISLAND");
                    //print all available studentsIDs and islandIds
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
                    output.println("Choose character");
                    break;
                case G:
                    currEvent = EventFactory.getEvent(choice);
                    output.println("Choose a wizard");
                    //print available wizards
                    break;

            }

            in = input.next();
            currEvent.parseInput(in);



            //A seconda di che evento è il parsing viene fatto in modo diverso. Istanzio l'evento chiamando il factory,
            //e chiamo il parser della classe che ho istanziato. Il parser salva i parametri di input dentro gli
            //attributi della classe.

            //Per Event uso una abstract class invece che una interface perché oltre ai metodi non implementati,
            //che chiederebbero l'uso di interface, mi serve anche un attributo statico che mi dice che tipo di evento
            //istanziare con il factory metod. Un'interface non può avere metodi, quindi devo usare abstract class.
            //Altrimenti: c'è un altro posto dove potrei tenere l'attributo che mi dice che tipo di evento devo
            //istanziare? Magari una nuova classe InputParser che contiene askChoice.

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
