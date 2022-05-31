package Network;

import Events.EventFactory;
import Exceptions.UnknownMessageException;
import Network.Messages.PlayerInfoMessage;
import Util.HelpInterpreter;
import View.CLI.CLI;
import View.MessageInterpreter;
import View.UI;
import com.google.gson.GsonBuilder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private String ip;
    private int port;
    private UI ui;

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
                if (HelpInterpreter.isHelp(line)) {
                    ((CLI) ui).displayHelp(line);
                } else {
                    line = EventFactory.stringToEventJson(ui.getPlayerID(), line);
                    out.println(line);
                    out.flush();
                }


            }
        }
    }

    private class RemoteInput implements Runnable {

        private String id;
        private BufferedReader in;
        private MessageInterpreter messageInterpreter;


        public RemoteInput (BufferedReader inputSource) {
            this.in = inputSource;
            this.messageInterpreter = new MessageInterpreter(ui);
        }
        public void run() {
            while(true) {
                try {
                    String line = in.readLine();

                    messageInterpreter.interpret(line);

                } catch (IOException | UnknownMessageException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        ui = new CLI();
    }

    public void run() throws IOException {
        Socket clientSocket = new Socket(ip, port);
        System.out.println("Connection OK");

        Scanner firstScanner = new Scanner(System.in);

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        try {
            //PrintWriter firstOutput = new PrintWriter(clientSocket.getOutputStream());

            String name = null;
            while (name == null) {
                System.out.println("What's your name?");
                name = firstScanner.nextLine();
            }

            Integer pNumber = null;
            while (pNumber == null) {
                System.out.println("How many players you want to play with?");

                try {
                    pNumber = Integer.parseInt(firstScanner.nextLine());

                    if (!pNumber.equals(2) && !pNumber.equals(3) && !pNumber.equals(4)) throw new Exception();
                } catch (Exception e) {
                    pNumber = null;
                }
            }

            String expert = null;
            while (expert == null) {
                System.out.println("Expert? [Y/N]");
                expert = firstScanner.nextLine();

                if (!expert.equals("Y") && !expert.equals("N")) {
                    expert = null;
                }
            }

            out.println(new GsonBuilder().serializeNulls().create().toJson(new PlayerInfoMessage(name, pNumber, expert)));

        } catch (Exception e) {
            System.out.println("Connection closed from the client side");
            e.printStackTrace();
            firstScanner.close();
        }


        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        try {
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
