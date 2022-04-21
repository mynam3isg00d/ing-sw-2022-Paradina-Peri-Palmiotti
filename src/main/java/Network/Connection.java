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

            //send("What's your name??");

            server.lobby(this);
            while (isActive) {
                //Object o = in.get??
                //notify(o);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
