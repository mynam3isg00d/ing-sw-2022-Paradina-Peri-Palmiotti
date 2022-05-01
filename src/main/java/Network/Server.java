package Network;

import Controller.Game;
import Model.Model;
import Model.Player;
import View.RemoteView;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Server implements Runnable{
    private final int  PORT = 42069;
    private final ServerSocket serverSocket;
    private final ExecutorService executor;

    private final Map<String, Connection>[] waitingLists = new Map[3];
    private final Map<String, Connection>[] playingLists = new Map[3];

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.executor = Executors.newFixedThreadPool(128);

        for(int i = 0; i < 3; i++) {
            waitingLists[i] = new HashMap<>();
            playingLists[i] = new HashMap<>();
        }
    }

    //con synchronized non dovrebbero esserci problemi legati all'ordine "casuale" dei set
    /**
     * Lobby is called every time a new Connection is initialized.
     * When the waitingList contains a number of players which is equals to the number of players needed for the game to start the game is initialized
     * Synchronized in order to avoid concurrent changes to the lists
     * @param newConnection The connection just created
     */
    public synchronized void lobby(Connection newConnection, String name, int playerNumber) {
        int listIndex = playerNumber - 2;

        //adds a new connection to the correct waiting list
        waitingLists[listIndex].put(name, newConnection);

        //if the waiting list has reached the right number of players, the game is initialized
        if (waitingLists[listIndex].size() == playerNumber) {
            //gets the keySet of the waitingList map in order to remove the first ones
            List<String> names = new ArrayList<>(waitingLists[listIndex].keySet());
            for (String n : names) {
                System.out.println("DEBUG: "+n);
            }

            //removes the first playersPerMatch connections from the waitingList and puts them in the playingList
            for (String n :names) playingLists[listIndex].put(n, waitingLists[listIndex].get(n));
            for (String n :names) waitingLists[listIndex].remove(n);

            //adds to the remoteViews list as many remoteViews as the players playing
            List<RemoteView> remoteViews = new ArrayList<>();
            List<Player> players = new ArrayList<>();
            for(Map.Entry<String, Connection> entry : playingLists[listIndex].entrySet()) {
                Player p = new Player(entry.getKey());
                players.add(p);
                remoteViews.add(new RemoteView(p, entry.getValue()));
            }

            //initializes the Controller components
            Game c = new Game(players);

            //for each remote view:
            //Controller is made observer of RemoteView
            //RemoteView is made observer of all Model components
            for(RemoteView rv : remoteViews) {
                rv.addObserver(c); //Controller observes remoteView
                c.addObserversToModelComponents(rv); //remoteView observes Model
            }

            //-------------------------------------------

            //each connection in the playing list has to be notified of the start of the match
            for (Map.Entry<String, Connection> entry : playingLists[listIndex].entrySet()) {
                entry.getValue().send("Game Started");
            }

        } else {
            for (Map.Entry<String, Connection> entry : waitingLists[listIndex].entrySet()) {
                entry.getValue().send("Waiting for " + (playerNumber - waitingLists[listIndex].size()) + "player(s) to join");
            }
        }
    }

    public Map<String, Connection> getWaitingList(int playerNumber) {
        return new HashMap<>(waitingLists[playerNumber-2]);
    }

    public Map<String, Connection> getPlayingList(int playerNumber) {
        return new HashMap<>(playingLists[playerNumber-2]);
    }

    /**
     * The server listens in order to accept connections.
     * Each new connection is executed by the thread pool.
     */
    public void run() {
        System.out.println("SERVER SAYS: server listening on port " + PORT);
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
                executor.submit(connection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
