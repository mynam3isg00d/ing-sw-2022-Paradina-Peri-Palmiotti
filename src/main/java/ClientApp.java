import Network.Client;
import View.GUI.GUI;
import javafx.application.Application;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("0 for CLI, 1 for GUI: ");
        int choice = s.nextInt();

        switch(choice) {
            case 0: {
                //TODO: !!COOLER!! if moved to cli
                System.out.print("IP address: ");
                String ip = s.nextLine();
                Client c = new Client(ip, 42069);
                try {
                    c.run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case 1: {
                Application.launch(GUI.class);
            }
        }
    }
}
