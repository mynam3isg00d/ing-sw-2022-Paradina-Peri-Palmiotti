package Util;

import Model.NoTileCard;
import Model.Shop;
import Model.StudentCard;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ShopDeserializer implements JsonDeserializer<Shop> {
    @Override
    public Shop deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Gson b = new GsonBuilder().serializeNulls().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
        Shop ret = b.fromJson(jsonElement, Shop.class);
        for(int i=0; i<3; i++) {
            JsonObject obj = jsonElement.getAsJsonObject()
                                        .get("shop")
                                        .getAsJsonArray()
                                        .get(i)
                                        .getAsJsonObject();

            if(obj.has("MAX_STUDENTS")) {
                StudentCard card = b.fromJson(obj, StudentCard.class);
                ret.setCard(card, i);
            } else if (obj.has("MAX_NOTILES")) {
                NoTileCard card = b.fromJson(obj, NoTileCard.class);
                ret.setCard(card, i);
            }
        }

        return ret;
    }
}
