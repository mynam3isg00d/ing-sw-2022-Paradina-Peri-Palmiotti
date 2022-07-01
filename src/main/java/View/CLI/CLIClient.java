package View.CLI;

import Events.EventFactory;
import Exceptions.UnknownMessageException;
import Network.Messages.Message;
import Network.Messages.PlayerInfoMessage;
import Util.HelpInterpreter;
import View.MessageInterpreter;
import View.UI;
import com.google.gson.GsonBuilder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CLIClient {

    boolean isActive = true;
    private String ip;
    private int port;
    private UI ui;

    /**
     * A runnable class that is listening for input from the client's interface.
     * When a message is entered, it is sent over the connection
     */
    private class LocalInput implements Runnable {

        private PrintWriter out;
        private Scanner in;

        public LocalInput(PrintWriter out) {
            this.out = out;
            in = new Scanner(System.in);
        }

        public void run() {
            while(isActive) {
                String line = in.nextLine();
                if (isActive) {
                    if (HelpInterpreter.isHelp(line)) {
                        ((CLI) ui).displayHelp(line);
                    } else {
                        line = EventFactory.stringToEventJson(ui.getPlayerID(), line);
                        if (line.contains("not an event")) {
                            ui.renderError(new Message(line, true));
                        }
                        out.println(line);
                        out.flush();
                    }
                }
            }
        }
    }

    /**
     * A runnable class that is listening for remote input from the server.
     * When a message is received, it is forwarded to a message interpreter that acts accordingly
     */
    private class RemoteInput implements Runnable {

        private BufferedReader in;
        private MessageInterpreter messageInterpreter;


        public RemoteInput (BufferedReader inputSource) {
            this.in = inputSource;
            this.messageInterpreter = new MessageInterpreter(ui);
        }
        public void run() {
            while(isActive) {
                String line = null;
                try {
                    line = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (line != null) {
                    try {
                        messageInterpreter.interpret(line);
                    } catch (UnknownMessageException e) {

                        e.printStackTrace();
                    }
                } else {
                    isActive = false;
                    System.out.println("Server RIP\nPress enter to close the app");
                }
            }
        }
    }

    public CLIClient(String ip, int port){
        this.ip = ip;
        this.port = port;
        ui = new CLI();
    }

    /**
     * As long as client is marked as active it's waiting for input coming both from the server and the user.
     * @throws IOException Signals something went wrong opening input/output streams
     */
    public void run() throws IOException {
        Socket clientSocket = new Socket(ip, port);
        System.out.println("Connection OK");

        Scanner firstScanner = new Scanner(System.in);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        try {

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
            new Thread(new CLIClient.LocalInput(out)).start();

            //thread listening messages from the socket, then prints them
            new Thread(new CLIClient.RemoteInput(in)).start();

        } catch (Exception e) {
            System.out.println("Connection closed from the client side");
            e.printStackTrace();
            in.close();
            out.close();
            clientSocket.close();
        }
    }
}
