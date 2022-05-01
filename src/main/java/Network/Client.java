package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;

    private class LocalInput implements Runnable {
        private PrintWriter out;
        private Scanner in;
        public LocalInput(PrintWriter out) {
            this.out = out;
            in = new Scanner(System.in);
        }

        public void asyncSend(String message) {
            out.println(message);
        }
        public void run() {
            while(true) {
                String line = in.nextLine();
                out.println(line);
                out.flush();
            }
        }
    }

    private class RemoteInput implements Runnable {

        private BufferedReader in;
        public RemoteInput (BufferedReader inputSource) {
            this.in = inputSource;
        }
        public void run() {
            while(true) {
                try {
                    String line = in.readLine();
                    System.out.println(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void run() throws IOException {
        Socket clientSocket = new Socket(ip, port);
        System.out.println("Connection OK");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        try {
            //the first message is sent by the server
            String firstMessage = in.readLine();
            System.out.println(firstMessage);

            //thread listening the player input
            new Thread(new LocalInput(out)).start();

            //thread listening messages from the socket, then prints them
            new Thread(new RemoteInput(in)).start();
        } catch (Exception e) {
            System.out.println("Connection closed from the client side");
            e.printStackTrace();
            in.close();
            out.close();
            clientSocket.close();
        }
    }
}
