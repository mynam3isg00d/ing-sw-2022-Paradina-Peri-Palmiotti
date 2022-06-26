package Network;

import Events.GameEvent;
import Exceptions.UnknownMessageException;
import Model.*;
import Util.AnnotationExclusionStrategy;
import Network.Messages.Message;
import com.google.gson.*;

public class JsonFactory {

    private Gson builder;

    /**
     * Initializes a new json builder using Google's Gson, with some useful attributes.
     */
    public JsonFactory() {
        builder = new GsonBuilder()
                .setExclusionStrategies(new AnnotationExclusionStrategy())
                .serializeNulls()
                .create();
    }

    /**
     * Converts a written message to a json message event (code 100)
     * @param m The message string
     * @return A json string representing a message event
     */
    public String messageToJson(Message m) {
        JsonElement jsonElement = builder.toJsonTree(m);
        jsonElement.getAsJsonObject().addProperty("messageCode", 100);
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;
    }

    /**
     * Takes an exception and builds a json error event (code 400) including the exception's tostring message
     * @param e An exception
     * @return A json string representing an error event
     */
    public String errorToJson(Exception e) {
        Message m = new Message(e.toString());
        JsonElement jsonElement = builder.toJsonTree(m);
        jsonElement.getAsJsonObject().addProperty("messageCode", 400);
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;
    }

    /**
     * Builds a json event including a client's id in order for him to use it throughout the connection and, at the same time, notify the match is about to start
     * @param id The player's id
     * @return A json string the client will need to set its id
     */
    public String initToJson(String id) {

        Message message = new Message(id);
        JsonElement jsonElement = builder.toJsonTree(message);
        jsonElement.getAsJsonObject().addProperty("messageCode", 301);
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;
    }


    /**
     * Builds a json representation of a board in order to send it over the connection
     * @param b The board to send
     * @return The json representation of the board
     */
    public String modelToJson(Board b) {
        JsonElement jsonElement = builder.toJsonTree(b);
        jsonElement.getAsJsonObject().addProperty("messageCode", "200");
        String jsonStr = builder.toJson(jsonElement);


        return jsonStr;
    }

    /**
     * Builds a json representation of a gamemodel object in order to send it over the connection
     * @param gm The gamemodel to send
     * @return The json representation of the gamemodel object
     */
    public String modelToJson(GameModel gm) {
        JsonElement jsonElement = builder.toJsonTree(gm);
        jsonElement.getAsJsonObject().addProperty("messageCode", "201");
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;
    }

    /**
     * Builds a json representation of an islandwrapper object in order to send it over the connection
     * @param iw The islandwrapper object to send
     * @return The json representation of the islandwrapper object
     */
    public String modelToJson(IslandsWrapper iw) {
        JsonElement jsonElement = builder.toJsonTree(iw);
        jsonElement.getAsJsonObject().addProperty("messageCode", "202");
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;    }

    /**
     * Builds a json representation of a cloudwrapper object in order to send it over the connection
     * @param cw The cloudwrapper object to send
     * @return The json representation of the cloudwrapper object
     */
    public String modelToJson(CloudWrapper cw) {
        JsonElement jsonElement = builder.toJsonTree(cw);
        jsonElement.getAsJsonObject().addProperty("messageCode", "203");
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;    }

    /**
     * Builds a json representation of a shop object in order to send it over the connection
     * @param s The shop object to send
     * @return The json representation of the shop object
     */
    public String modelToJson(Shop s) {
        JsonElement jsonElement = builder.toJsonTree(s);
        jsonElement.getAsJsonObject().addProperty("messageCode", "204");
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;    }

    /**
     * Builds a json representation of a player object in order to send it over the connection
     * @param p The player object to send
     * @return The json representation of the player object
     */
    public String modelToJson(Player p) {
        JsonElement jsonElement = builder.toJsonTree(p);
        jsonElement.getAsJsonObject().addProperty("messageCode", "205");
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;    }

    /**
     * Builds a json representation of a sack object in order to send it over the connection
     * @param s The sack object to send
     * @return The json representation of the sack object
     */
    public String modelToJson(Sack s) {
        JsonElement jsonElement = builder.toJsonTree(s);
        jsonElement.getAsJsonObject().addProperty("messageCode", "206");
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;
    }

}
