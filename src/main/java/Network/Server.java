package Network;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Server implements Runnable{
    private final int  PORT = 42069;
    private ServerSocket serverSocket;
    private ExecutorService executor;
    private List<Connection> waitingList;
    private List<Connection> playingList;

    private final int playersPerMatch;

    public Server(int playersPerMatch) throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.executor = Executors.newFixedThreadPool(128);
        waitingList = new ArrayList<>();

        this.playersPerMatch = playersPerMatch;
    }

    /**
     * Lobby is called every time a new Connection is initialized.
     * When the waitingList contains a number of players which is equals to the number of players needed for the game to start the game is initialized
     * @param newConnection
     */
    public void lobby(Connection newConnection) {
        waitingList.add(newConnection);
        if (waitingList.size() == playersPerMatch) {
            //initializes the game components
            playingList = new ArrayList<>(waitingList);
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
