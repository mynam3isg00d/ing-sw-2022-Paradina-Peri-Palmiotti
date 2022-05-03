package Network;

import Events.GameEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonFactory {
    public static String toJson(GameEvent event) {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();

        Gson gson = builder.create();

        return gson.toJson(event);
    }
}
