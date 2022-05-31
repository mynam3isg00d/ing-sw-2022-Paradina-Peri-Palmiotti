package Network;

import Events.GameEvent;
import Exceptions.UnknownMessageException;
import Model.*;
import Util.AnnotationExclusionStrategy;
import Network.Messages.Message;
import com.google.gson.*;

public class JsonFactory {

    private Gson builder;

    public JsonFactory() {
        builder = new GsonBuilder()
                .setExclusionStrategies(new AnnotationExclusionStrategy())
                .serializeNulls()
                .create();
    }

    public String messageToJson(Message m) {
        JsonElement jsonElement = builder.toJsonTree(m);
        jsonElement.getAsJsonObject().addProperty("messageCode", 100);
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;
    }

    public String errorToJson(Exception e) {
        Message m = new Message(e.toString());
        JsonElement jsonElement = builder.toJsonTree(m);
        jsonElement.getAsJsonObject().addProperty("messageCode", 400);
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;
    }

    public String initToJson(String m, int code) throws UnknownMessageException {
        if (code / 100 != 3) {
            throw new UnknownMessageException();
        }

        Message message = new Message(m);
        JsonElement jsonElement = builder.toJsonTree(message);
        jsonElement.getAsJsonObject().addProperty("messageCode", code);
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;
    }


    public String modelToJson(Board b) {
        JsonElement jsonElement = builder.toJsonTree(b);
        jsonElement.getAsJsonObject().addProperty("messageCode", "200");
        String jsonStr = builder.toJson(jsonElement);


        return jsonStr;
    }

    public String modelToJson(GameModel gm) {
        JsonElement jsonElement = builder.toJsonTree(gm);
        jsonElement.getAsJsonObject().addProperty("messageCode", "201");
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;
    }

    public String modelToJson(IslandsWrapper iw) {
        JsonElement jsonElement = builder.toJsonTree(iw);
        jsonElement.getAsJsonObject().addProperty("messageCode", "202");
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;    }

    public String modelToJson(CloudWrapper cw) {
        JsonElement jsonElement = builder.toJsonTree(cw);
        jsonElement.getAsJsonObject().addProperty("messageCode", "203");
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;    }

    public String modelToJson(Shop s) {
        JsonElement jsonElement = builder.toJsonTree(s);
        jsonElement.getAsJsonObject().addProperty("messageCode", "204");
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;    }

    public String modelToJson(Player p) {
        JsonElement jsonElement = builder.toJsonTree(p);
        jsonElement.getAsJsonObject().addProperty("messageCode", "205");
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;    }

    public String modelToJson(Sack s) {
        JsonElement jsonElement = builder.toJsonTree(s);
        jsonElement.getAsJsonObject().addProperty("messageCode", "206");
        String jsonStr = builder.toJson(jsonElement);

        return jsonStr;    }

    @Deprecated
    public static String eventToJson(GameEvent event) {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        return gson.toJson(event);
    }

}
