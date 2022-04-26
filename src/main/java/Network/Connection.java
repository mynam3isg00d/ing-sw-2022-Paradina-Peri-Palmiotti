package Network;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class Connection extends Observable implements Runnable {
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private Server server;
    private String name;

    private boolean isActive;

    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        isActive = true;
    }

    /**
     * Sends the updated model to the client over the network
     */
    public void send() {

    }

    public void send(String message) {
        out.println(message);
        out.flush();
    }

    public void close() {
      isActive = false;
    }

    /**
     * Waits for events coming from the client and notifies the remoteView each time a new event is received.
     */
    @Override
    public void run() {
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());

            send("What's your name??");
            name = in.nextLine();

            int playerNumber = 0;
            boolean validEntry;
            do{
                validEntry = true;
                send("How many players would you like to play with??");
                try {
                    playerNumber = Integer.parseInt(in.nextLine());
                    if (playerNumber != 2 && playerNumber != 3 && playerNumber != 4) validEntry = false;
                } catch (NumberFormatException e) {
                    validEntry = false;
                    e.printStackTrace();
                } finally {

                }
            } while(!validEntry);


            int n = server.lobby(this, name, playerNumber);
            send("Waiting for " + n + " player(s) to join");

            while (isActive) {
                //currently, only works with strings
                String message = in.nextLine();
                notifyObservers(message);

                //Object o = in.get??
                //notifica virtual view
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
