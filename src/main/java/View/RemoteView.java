package View;

import Controller.Game;
import Events.EventFactory;
import Events.GameEvent;
import Model.*;
import Network.Connection;
import Network.JsonFactory;
import Observer.*;
import com.google.gson.JsonElement;


public class RemoteView extends Observable implements Observer {

    private Player player;
    private Connection connection;
    private JsonFactory json;

    public RemoteView(Player p, Connection c) {
        player = p;
        connection = c;
        json = new JsonFactory();

        connection.addObserver(new MessageReceiver());
    }

    @Override
    public void update(Object o) {
        System.out.println("Arrivato");

        //o is a json string representing the updated model
        //the json string also includes a messageCode signaling the received message is, indeed, a model update (the code is added by JsonFactory)
        String jsonModelUpdate = (String) o;

        connection.send(jsonModelUpdate);
    }

    /**
     * MessageReceiver is an internal class that directly observes the connection tied to the RemoteView
     * Each time an event/message is received, messageReceiver acts accordingly
     */
    private class MessageReceiver implements Observer {
        /**
         * Receives a stream of bytes from the network and turns it into a GameEvent
         * @param o
         */
        @Override
        public void update(Object o) {
            String line = (String) o;
            System.out.println("MESSAGE RECEIVER SAYS: Event Received" + line);
            try{
                //Factory che crea eventi
                //GameEvent e = EventFactory.getEvent();
                //processChoice(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void processChoice(GameEvent event) {
        notify(event);
    }

    public void sendError(Exception e) {
        //connection.send(e);   JSON
    }
}
