package Network;

import Controller.Game;
import Events.GameEvent;
import Model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonFactory {

    private Gson builder;

    public JsonFactory() {
        builder = new GsonBuilder().setPrettyPrinting().create();
    }


    public static String modelToJson(Object o) {
        Gson builder = new GsonBuilder().setPrettyPrinting().create();
        if (o instanceof Board) {
            return "200" + builder.toJson((Board) o);
        } else if (o instanceof GameModel) {
            return "201" + builder.toJson((GameModel) o);
        }
        if (o instanceof IslandsWrapper) {
            return "202" + builder.toJson((IslandsWrapper) o);
        }
        if (o instanceof CloudWrapper) {
            return "203" + builder.toJson((CloudWrapper) o);
        }
        if (o instanceof Shop) {
            return "204" + builder.toJson((Shop) o);
        } else if (o instanceof Player) {
            return "205" + builder.toJson((Player) o);
        }
        if (o instanceof Sack) {
            return "206" + builder.toJson((Sack) o);
        } else {
            return "400";
        }

    }

    public String modelToJson(Board b) {
        String s = builder.toJson(b, Board.class);
        return "200" + s;
    }

    public String modelToJson(GameModel gm) {
        return "201" + builder.toJson(gm, GameModel.class);
    }

    public String modelToJson(IslandsWrapper iw) {
        return "202" + builder.toJson(iw, IslandsWrapper.class);
    }

    public String modelToJson(CloudWrapper cw) {
        return "203" + builder.toJson(cw, CloudWrapper.class);
    }

    public String modelToJson(Shop s) {
        return "204" + builder.toJson(s, Shop.class);
    }

    public String modelToJson(Player p) {
        return "205" + builder.toJson(p, Player.class);
    }

    public String modelToJson(Sack s) {
        return "201" + builder.toJson(s, Sack.class);
    }

    @Deprecated
    public static String eventToJson(GameEvent event) {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        return gson.toJson(event);
    }

}
