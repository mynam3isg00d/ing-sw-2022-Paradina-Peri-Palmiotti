package View;
import Model.*;


import java.io.PrintStream;
import java.util.*;

//islandview osserva le modifiche ai modelli delle isole -> islandView verrà notificata delle modifiche del modello
public class IslandView {
    final private Scanner input;
    final private PrintStream output;
    private IslandHandler islandController;
    private View view;
    //TODO private Game gameController;
    private IslandView islandView;

    public IslandView() {
        input = new Scanner(System.in);
        output = System.out;
    }


    //islands to print will eventually be part of an event object
    //update will be called everytime something in Model.islandWrapper changes
    public void update(List<Island> islands) {
        output.println("---Model has been updated, refreshing islands...---");
        for (Island i : islands) printIsland(i);
    }

    public void askStudentsMove() {

        boolean ok = false;
        while (!ok) {
            output.println("Quanti Studenti vuoi muovere?");
            int k = Integer.parseInt(input.nextLine());
            output.println("Di che Colore?");
            String color = input.nextLine();
            output.println("Su che isola?");
            int islandIndex = Integer.parseInt(input.nextLine());

            //come dirgli cosa gli sto notificando? posso chiamare un metodo del controller?
            ok = islandController.moveStudents(islandIndex, k, color);
            if (!ok) System.out.println("VIEW SAYS: Che cazzo fai");
        }
    }

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

    public void connectController(IslandHandler ic) {
        islandController = ic;
    }
}
