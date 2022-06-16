package Observer.View;

import Model.*;
import Network.Connection;
import Network.JsonFactory;
import Observer.*;


public class RemoteView extends Observable implements Observer {

    private final Player player;
    private final Connection connection;
    private final JsonFactory json;

    public RemoteView(Player p, Connection c) {
        player = p;
        connection = c;
        json = new JsonFactory();

        connection.addObserver(new MessageReceiver());
    }

    @Override
    public void update(Observable obs, Object o) {

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
        public void update(Observable obs, Object o) {
            String line = (String) o;
            processChoice(line);
        }


    }

    public void processChoice(String event) {
        notify(event);
    }

    public void sendError(Exception e) {
        JsonFactory jf = new JsonFactory();
        connection.send(jf.errorToJson(e));
    }
}
