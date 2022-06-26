package Network;

import java.io.BufferedReader;

import Exceptions.InvalidNumberOfPlayersException;
import Observer.Observable;
import Network.Messages.PlayerInfoMessage;
import com.google.gson.GsonBuilder;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 * A connection object is initialized every time a new client reaches the server.
 * Includes the client's name and id that will be used for identification.
 * The connection object is the endpoint for messages both directed to the server and the client.
 */
public class Connection extends Observable implements Runnable {
    private final Socket socket;
    private Scanner in;
    private PrintWriter out;
    private final Server server;
    private String name;

    private String id;
    private int progressive;
    private boolean isActive;

    /**
     * Initializes the new connection object
     * @param socket The socket over which the connection will be carried out
     * @param server The server object that has initialized the connection
     */
    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        isActive = true;
    }


    /**
     * Used to send the updated model to the client over the network
     */
    public void send(String message) {
        out.println(message);
        out.flush();
    }

    /**
     * Closes the connection when the corresponding client disconnects.
     */
    public void close() {
      isActive = false;
    }

    /**
     * Waits for json events coming from the client and notifies the remoteView each time a new event is received.
     */
    @Override
    public void run() {
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());


            int playerNumber = 0;
            boolean validEntry;
            boolean expert = false;

            boolean disconnectedEarly = false;
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

                }  catch (NoSuchElementException e) {
                    disconnectedEarly = true;
                } catch (Exception e) {
                    validEntry = false;
                    e.printStackTrace();
                }
            } while(!validEntry && !disconnectedEarly);

            if (!disconnectedEarly) {
                try {
                    server.lobby(this, name, playerNumber, expert);
                } catch (InvalidNumberOfPlayersException e) {
                    e.printStackTrace();
                }

                while (isActive) {
                    try {
                        String message = in.nextLine();
                        notify(message);
                    } catch (NoSuchElementException e) { //thrown if one disconnects
                        System.out.println("Client with name " + name + " disconnected");

                        if(observers.isEmpty()) {  //if the observers list is empty, the game hasn't started
                            server.deregisterConnection(name, id);
                        } else {    //else the game has started
                            notify("{\"eventId\":\"0010\"}");
                        }

                        isActive = false;
                    }
                }
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }  finally {
            close();
        }
    }

    public void setId(String name) {
        if (name.equals(this.name)) this.id = name + "_" + progressive;
    }

    public String getId() {
        return id;
    }


    /**
     * Sets the progressive attribute, which is used in case of multiple users with the same name
     * @param p The number of players with the same name connected before this one
     */
    public void setProgressive(int p) {
        progressive = p;
    }


    public String getName() {
        return name;
    }
}
