package Network;

import Controller.ExpertGame;
import Controller.Game;
import Exceptions.InvalidNumberOfPlayersException;
import Model.Player;
import Network.Messages.Message;
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

    private final Map<Player, Connection>[] waitingLists = new Map[6];
    private final Map<Player, Connection>[] playingLists = new Map[6];

    private final JsonFactory jsonFactory;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.executor = Executors.newFixedThreadPool(128);
        this.jsonFactory = new JsonFactory();

        for(int i = 0; i < 6; i++) {
            waitingLists[i] = new HashMap<>();
            playingLists[i] = new HashMap<>();
        }
    }

    //Using synchronized, there should be no problems related to the "random" order of the sets

    /**
     * Lobby is called every time a new Connection is initialized.
     * When the waitingList contains a number of players which is equals to the number of players needed for the game to start the game is initialized
     * Synchronized in order to avoid concurrent changes to the lists
     * @param newConnection The connection just created
     * @param name player's name
     * @param playerNumber number of players
     * @param expert true if expert game, else false
     * @throws InvalidNumberOfPlayersException if the number of players is outside the normal range [2-4]
     */
    public synchronized void lobby(Connection newConnection, String name, int playerNumber, boolean expert) throws InvalidNumberOfPlayersException {
        System.out.println("called lobby with name = " + name);

        //lobby for 2 players is in position 0 of the array
        //lobby for 3 players is in position 1 of the array
        //lobby for 4 players is in position 2 of the array
        int listIndex = playerNumber - 2;

        //expert lobbies are 3 position forward
        if (expert) listIndex += 3;

        //duplicate id handling
        int idCount = 0;
        for (Map.Entry<Player, Connection> entry : waitingLists[listIndex].entrySet()) {
            System.out.println("considerando connessione con nome = " + entry.getValue().getName());
            if (entry.getValue().getName().equals(name)) {
                System.out.println("duplicate alert!");
                idCount++;
            }
        }
        newConnection.setProgressive(idCount);

        //sets client id in the connection
        //for now ID == name
        newConnection.setId(name); //if progressive > 0, puts id = name_[progressive]

        //adds a new connection to the correct waiting list
        waitingLists[listIndex].put(new Player(newConnection.getId(), newConnection.getName(), -1), newConnection);


        //if the waiting list has reached the right number of players, the game is initialized
        if (waitingLists[listIndex].size() == playerNumber) {
            //gets the keySet of the waitingList map in order to remove the first ones
            List<Player> players = new ArrayList<>(waitingLists[listIndex].keySet());
            for (Player p : players) {
                System.out.println("DEBUG: playing with "+ p.getName());
            }

            //removes the first playersPerMatch connections from the waitingList and puts them in the playingList
            for (Player p : players) playingLists[listIndex].put(p, waitingLists[listIndex].get(p));
            for (Player p : players) waitingLists[listIndex].remove(p);

            //adds to the remoteViews list as many remoteViews as the players playing
            List<RemoteView> remoteViews = new ArrayList<>();
            for(Player p : players) {
                remoteViews.add(new RemoteView(p, playingLists[listIndex].get(p)));
            }


            switch (playerNumber) {
                case 2:
                case 3:
                    for(int i=0; i<playerNumber; i++) {
                        players.get(i).setTeamId(i);
                    }
                    break;
                case 4:
                    for(int i=0; i<playerNumber; i++) {
                        players.get(i).setTeamId(i%2);
                    }
                    break;
            }

            //initializes the Controller components
            Game c;
            if (!expert) {
                c = new Game(players);
            } else {
                c = new ExpertGame(players);
            }

            //for each remote view:
            //Controller is made observer of RemoteView
            //RemoteView is made observer of all Model components
            for(RemoteView rv : remoteViews) {
                rv.addObserver(c); //Controller observes remoteView
                c.addObserversToModelComponents(rv); //remoteView observes Model
            }


            //-------------------------------------------

            //each connection in the playing list has to be notified of the start of the match
            for (Map.Entry<Player, Connection> entry : playingLists[listIndex].entrySet()) {

                try {
                    //id setting in client
                    String idSignal = jsonFactory.initToJson(entry.getValue().getId());

                    //sends a message to the client containing his id in the match
                    entry.getValue().send(idSignal);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            //--------------------
            //model gets sent to the client for the first time
            System.out.println("Game start: sending entire model!");
            c.sendEntireModel();
            //-------------------
        } else {
            for (Map.Entry<Player, Connection> entry : waitingLists[listIndex].entrySet()) {
                Message waitingMessage = new Message("Waiting for " + (playerNumber - waitingLists[listIndex].size()) + " player(s) to join");

                entry.getValue().send(jsonFactory.messageToJson(waitingMessage));
            }
        }
    }


    /**
     * Deregisters the connection from the server
     * @param id the id to disconnect
     * @param name the player's name
     */
    public void deregisterConnection(String name, String id) {
        for (int i = 0; i < 6; i++) {
            waitingLists[i].remove(new Player(id, name, -1));
        }
        System.out.println("removed " + name + ", " + id + " from waitingList");
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
