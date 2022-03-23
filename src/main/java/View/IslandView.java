package View;
import Model.*;

import java.io.PrintStream;
import java.util.*;

//islandview osserva le modifiche ai modelli delle isole -> islandView verrà notificata delle modifiche del modello
public class IslandView implements Runnable, EventListener {
    final private Scanner input;
    final private PrintStream output;
    //TODO private Game gameController;

    public IslandView(String[] args) {
        input = new Scanner(System.in);
        output = System.out;
    }


    //islands to print will eventually be part of an event object
    //update will be called everytime something in Model.islandWrapper changes
    public void update(List<Island> islands) {
        output.println("---Model has been updated, refreshing islands...---");
        for (Island i : islands) printIsland(i);
        //askChoice??
    }

    /*
    private void askChoice(String gamePhase) {
        output.println("Che vuoi fare?");
        String in = input.nextLine();
        islandController.notify();
    }
    */

    private void printIsland(Island i) {
        String out = (
                "|  Dimension: " + i.getDimension() + " |\n" +
                        "|  Team Influence: " + i.getInfluence() + "  |\n" +
                        "|  MotherNature: " + i.isMotherNature() + "  |\n" +
                        "|  Students: "
        );

        int students[] = i.getStudents();
        out = out.concat(" Y= " + students[0]);
        out = out.concat(" B= " + students[1]);
        out = out.concat(" G= " + students[2]);
        out = out.concat(" R= " + students[3]);
        out = out.concat(" P= " + students[4]);

        out = out.concat(" |\n");
        output.println(out);
    }


    @Override
    public void run() {

    }
}
