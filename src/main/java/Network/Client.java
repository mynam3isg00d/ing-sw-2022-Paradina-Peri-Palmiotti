package Network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void run() throws IOException {
        Socket clientSocket = new Socket(ip, port);
        System.out.println("Connection OK");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        Scanner in = new Scanner(clientSocket.getInputStream());

        Scanner stdin = new Scanner(System.in);

        try {
            //the first message is sent by the server
            String firstMessage = in.nextLine();
            System.out.println(firstMessage);

            while (true) {
                String playerInput = stdin.nextLine();

                out.println(playerInput);
                out.flush();

                String message = in.nextLine();
                System.out.println(message);
            }
        } catch (Exception e) {
            System.out.println("Connection closed from the client side");
            e.printStackTrace();
        } finally {
            stdin.close();
            in.close();
            out.close();
            clientSocket.close();
        }
    }
}
