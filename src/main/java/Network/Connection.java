package Network;

import java.io.BufferedReader;
import Observer.Observable;
import Util.Message;
import Util.PlayerInfoMessage;
import com.google.gson.GsonBuilder;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

public class Connection extends Observable implements Runnable {
    private Socket socket;
    private Scanner in;
    private BufferedReader buf;
    private PrintWriter out;
    private Server server;
    private String name;

    private String id;
    private int progressive;

    private final JsonFactory jsonFactory;

    private boolean isActive;

    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.jsonFactory = new JsonFactory();
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


            int playerNumber = 0;
            boolean validEntry;
            boolean expert = false;
            do {
                validEntry = true;
                try {
                    String received = in.nextLine();
                    System.out.println(received);

                    PlayerInfoMessage pim = new GsonBuilder().serializeNulls().create().fromJson(received, PlayerInfoMessage.class);

                    name = pim.getName();
                    playerNumber = pim.getPlayerNumber();
                    expert = pim.isExpert();

                    //player number
                    if (playerNumber != 2 && playerNumber != 3 && playerNumber != 4) throw new Exception();

                } catch (Exception e) {
                    validEntry = false;
                    e.printStackTrace();
                }
            } while(!validEntry);

            server.lobby(this, name, playerNumber, expert);

            while (isActive) {
                String message = in.nextLine();
                notify(message);

                //Object o = in.get??
                //notifica virtual view
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void setId(String name) {
        if (name.equals(this.name)) this.id = name + "_" + progressive;
    }

    public String getId() {
        return id;
    }


    public void setProgressive(int p) {
        progressive = p;
    }


    public String getName() {
        return name;
    }
}
