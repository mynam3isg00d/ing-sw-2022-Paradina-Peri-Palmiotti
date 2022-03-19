import Model.*;
import java.util.*;

public class App {

    public static void main(String[] args) {
        String name1 = "Davide Palmiotti, il sigma-chad";
        String name2 = "Samuele Peri, il beta";
        Player p1 = new Player(name1, 0);
        Player p2 = new Player(name2, 0);
        Scanner s = new Scanner(System.in);

        p1.chooseWizard(0);
        p2.chooseWizard(2);

        List<Player> players = new ArrayList<Player>();
        players.add(p1); players.add(p2);
        List<Assistant> playedAssistants = new ArrayList<Assistant>();

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
    }

    public static void printHand(Hand h) {
        for(Assistant a : h.getHand()) {
            System.out.print("[" + a.getOrderNumber() + "|" + a.getMotherNumber() + "]");
        }
        System.out.println("\n");
    }

    public static boolean isInPlay(List<Assistant> playedAssistants, Assistant a) {
        for (Assistant ass : playedAssistants) {
            if (ass.getOrderNumber() == a.getOrderNumber()) return true;
        }
        return false;
    }
}
