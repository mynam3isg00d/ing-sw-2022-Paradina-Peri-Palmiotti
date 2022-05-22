package Network;

import Controller.ExpertGame;
import Controller.Game;
import Model.Player;
import Util.Message;
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

    private final Map<String, Connection>[] waitingLists = new Map[6];
    private final Map<String, Connection>[] playingLists = new Map[6];

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

    //con synchronized non dovrebbero esserci problemi legati all'ordine "casuale" dei set
    /**
     * Lobby is called every time a new Connection is initialized.
     * When the waitingList contains a number of players which is equals to the number of players needed for the game to start the game is initialized
     * Synchronized in order to avoid concurrent changes to the lists
     * @param newConnection The connection just created
     */
    public synchronized String lobby(Connection newConnection, String name, int playerNumber, boolean expert) {
        System.out.println("called lobby with name = " + name);

        //lobby for 2 players is in position 0 of the array
        //lobby for 3 players is in position 1 of the array
        //lobby for 4 players is in position 2 of the array
        int listIndex = playerNumber - 2;

        //expert lobbies are 3 position forward
        if (expert) listIndex += 3;

        //duplicate id handling
        int idCount = 0;
        for (Map.Entry<String, Connection> entry : waitingLists[listIndex].entrySet()) {
            System.out.println("considerando connessione con nome = " + entry.getValue().getName().equals(name));
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
        waitingLists[listIndex].put(newConnection.getId(), newConnection);


        //if the waiting list has reached the right number of players, the game is initialized
        if (waitingLists[listIndex].size() == playerNumber) {
            //gets the keySet of the waitingList map in order to remove the first ones
            List<String> IDs = new ArrayList<>(waitingLists[listIndex].keySet());
            for (String id : IDs) {
                System.out.println("DEBUG: playing with "+ id);
            }

            //removes the first playersPerMatch connections from the waitingList and puts them in the playingList
            for (String id : IDs) playingLists[listIndex].put(id, waitingLists[listIndex].get(id));
            for (String id : IDs) waitingLists[listIndex].remove(id);

            //adds to the remoteViews list as many remoteViews as the players playing
            List<RemoteView> remoteViews = new ArrayList<>();
            List<Player> players = new ArrayList<>();
            for(Map.Entry<String, Connection> entry : playingLists[listIndex].entrySet()) {
                Player p = new Player(entry.getKey());

                //sets the id in the connection to the player
                p.setPlayerID(entry.getValue().getId());

                players.add(p);
                remoteViews.add(new RemoteView(p, entry.getValue()));
            }


            //TODO choose squad?
            switch (playerNumber) {
                case 2:
                case 3:
                    for(int i=0; i<playerNumber; i++) {
                        players.get(i).setTeamId(i);
                    }
                case 4:
                    for(int i=0; i<playerNumber; i++) {
                        players.get(i).setTeamId(i%2);
                    }
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
            for (Map.Entry<String, Connection> entry : playingLists[listIndex].entrySet()) {
                //entry.getValue().send("Game Started");

                try {
                    //id setting in client
                    String idSignal = jsonFactory.initToJson(entry.getValue().getId(), 301);

                    //sends a message to the client containing his id in the match
                    entry.getValue().send(idSignal);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /* NOT REQUIRED ANYMORE - client clears the screen each time the id is set
                //Clear screen command
                entry.getValue().send("300");
                */

            }

            //--------------------
            //model gets sent to the client for the first time
            c.sendEntireModel();
            //-------------------
        } else {
            for (Map.Entry<String, Connection> entry : waitingLists[listIndex].entrySet()) {
                Message waitingMessage = new Message("Waiting for " + (playerNumber - waitingLists[listIndex].size()) + " player(s) to join");

                entry.getValue().send(jsonFactory.messageToJson(waitingMessage));
            }
        }

        return "";
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
