package Network;

import Controller.Game;
import Model.Player;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Server implements Runnable{
    private final int  PORT = 42069;
    private ServerSocket serverSocket;
    private ExecutorService executor;

    private Map<String, Connection>[] waitingLists;
    private Map<String, Connection>[] playingLists;

    private final int playersPerMatch;

    public Server(int playersPerMatch) throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.executor = Executors.newFixedThreadPool(128);

        this.playersPerMatch = playersPerMatch;
    }

    /**
     * Lobby is called every time a new Connection is initialized.
     * When the waitingList contains a number of players which is equals to the number of players needed for the game to start the game is initialized
     * @param newConnection
     */
    public void lobby(Connection newConnection, String name, int playerNumber) {
        int listIndex = playerNumber - 2;

        //adds a new connection to the correct waiting list
        waitingLists[listIndex].put(name, newConnection);

        //if the waiting list has reached the right number of players, the game is initialized
        if (waitingLists[listIndex].size() == playersPerMatch) {
            //gets the keySet of the waitingList map in order to remove the first ones
            //TODO test if the order is mantained
            List<String> names = new ArrayList<>(waitingLists[listIndex].keySet());

            //removes the first playersPerMatch connections from the waitingList and puts them in the playingList
            for (int i = 0; i < playerNumber; i++) playingLists[listIndex].put(names.get(i), waitingLists[listIndex].get(names.get(i)));
            for (int i = 0; i < playerNumber; i++) waitingLists[listIndex].remove(names.get(0));

            //adds to the remoteViews list as many remoteViews as the players playing
            List<RemoteView> remoteViews = new ArrayList<>();
            List<Player> players = new ArrayList<>();
            for(Map.Entry<String, Connection> entry : playingLists[listIndex].entrySet()) {
                Player p = new Player(entry.getKey());
                players.add(p);
                remoteViews.add(new RemoteView(p, entry.getValue()));
            }

            //initializes the server game components
            //Game c = new Game(players);
            //Model m = new Model()

            //for(RemoteView rv : remoteViews) {
            //m.addObserver(rv);
            //rv.addObserver(c);
            //}


        }
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
