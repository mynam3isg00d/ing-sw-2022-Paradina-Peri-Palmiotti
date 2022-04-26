package View;

import Events.EventFactory;
import Events.GameEvent;
import Model.Player;
import Network.Connection;

import java.util.Observable;
import java.util.Observer;

public class RemoteView extends Observable implements Observer{
    private Player player;
    private Connection connection;

    /**
     * MessageReceiver is an internal class that directly observes the connection tied to the RemoteView
     * Each time an event/message is received, messageReceiver acts accordingly
     */
    private class MessageReceiver implements Observer {
        /**
         * Receives a stream of bytes from the network and turns it into a GameEvent
         * @param obs
         * @param o
         */
        @Override
        public void update(Observable obs, Object o) {
            System.out.println("MESSAGE RECEIVER SAYS: Event Received");
            try{
                //Factory che crea eventi
                GameEvent e = EventFactory.getGenericEvent();
                processChoice(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void update(Observable obs, String s) {
            System.out.println("MESSAGE RECEIVER SAYS: String Received");
            System.out.println(s);
        }

    }

    public RemoteView(Player p, Connection c) {
        player = p;
        connection = c;

        connection.addObserver(new MessageReceiver());
        //connection.send("Qualcosa");
    }

    public void processChoice(GameEvent event) {
        notifyAll();
    }

    @Override
    public void update(Observable observable, Object o) {
        //connection.send("Modello aggiornato");
    }
}
