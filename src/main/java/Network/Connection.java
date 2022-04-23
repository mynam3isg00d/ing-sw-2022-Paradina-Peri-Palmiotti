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
            do{
                send("How many players would you like to play with??");
                playerNumber = Integer.parseInt(in.nextLine());
            } while(playerNumber != 2 && playerNumber != 3 && playerNumber != 4);


            server.lobby(this, name, playerNumber);
            while (isActive) {
                //Object o = in.get??

                //notifica virtual view
                //notify(o);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
