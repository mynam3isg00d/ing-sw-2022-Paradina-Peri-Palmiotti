import Network.Client;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("IP Address: ");
        String ip = s.nextLine();

        Client c = new Client(ip, 42069);

        try {
            c.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
