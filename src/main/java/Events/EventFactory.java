package Events;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class EventFactory {

    //?
    public static GameEvent getEvent(Move eventType){
        switch(eventType){
            case A:
                return new PlayAssistantEvent();
            case B:
                return new MoveStudentToDiningEvent();
            case C:
                return new MoveStudentToIslandEvent();
            case D:
                return new MoveMotherNatureEvent();
            case E:
                return new PickStudentsFromCloudEvent();
            case F:
                return new BuyPlayCharacterEvent();
            case G:
                return new ChooseWizardEvent();
        }
        return null;
    }

    @Deprecated
    public static GameEvent eventFromJson(String jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, PlayAssistantEvent.class);
    }

    public static String stringToEventJson(String playerID, String line) {
        //TODO: SHOP EVENT ????
        Gson b = new GsonBuilder().serializeNulls().create();

        //Regex patterns
        Pattern wizard = Pattern.compile("^(choose wizard \\d*)$");
        Pattern assistant = Pattern.compile("^(play assistant \\d*)$");
        Pattern moveToDining = Pattern.compile("^(move student \\d* to dining)$");
        Pattern moveToIsland = Pattern.compile("^(move student \\d* to island \\d*)$");
        Pattern moveMotherNature = Pattern.compile("^(move mother nature \\d*)$");
        Pattern pickCloud = Pattern.compile("^(pick cloud \\d*)$");
        Pattern buyCharacter = Pattern.compile("^(buy character \\d*)");

        if (wizard.matcher(line).find()) {
            int wint = Integer.parseInt(line.split(" ")[2]);
            ChooseWizardEvent event = new ChooseWizardEvent(playerID, wint);
            return b.toJson(event);
        }

        if (assistant.matcher(line).find()) {
            int aint = Integer.parseInt(line.split(" ")[2]);
            PlayAssistantEvent event = new PlayAssistantEvent(playerID, aint);
            return b.toJson(event);
        }

        if (moveToDining.matcher(line).find()) {
            int dint = Integer.parseInt(line.split(" ")[2]);
            MoveStudentToDiningEvent event = new MoveStudentToDiningEvent(playerID, dint);
            return b.toJson(event);
        }

        if (moveToIsland.matcher(line).find()) {
            int sint = Integer.parseInt(line.split(" ")[2]);
            int iint = Integer.parseInt(line.split(" ")[5]);
            MoveStudentToIslandEvent event = new MoveStudentToIslandEvent(playerID, sint, iint);
            return b.toJson(event);
        }

        if (moveMotherNature.matcher(line).find()) {
            int mint = Integer.parseInt(line.split(" ")[3]);
            MoveMotherNatureEvent event = new MoveMotherNatureEvent(playerID, mint);
            return b.toJson(event);
        }

        if (pickCloud.matcher(line).find()) {
            int cint = Integer.parseInt(line.split(" ")[2]);
            PickStudentsFromCloudEvent event = new PickStudentsFromCloudEvent(playerID, cint);
            return b.toJson(event);
        }

        if(buyCharacter.matcher(line).find()) {
            int chint = Integer.parseInt(line.split(" ")[2]);
            List<String> plist = Arrays.asList(line.split(" "));
            plist = plist.subList(3, plist.size());
            BuyPlayCharacterEvent event = new BuyPlayCharacterEvent(playerID, chint, plist);
            return b.toJson(event);
        }

        return line;
    }

    public static GameEvent jsonToEvent(String json) {
        GameEvent e = null;
        Gson b = new GsonBuilder().serializeNulls().create();

        JsonObject messageAsJsonObject = b.fromJson(json, JsonObject.class);
        String code = messageAsJsonObject.get("eventId").getAsString();

        switch (code) {
            case "0000":
                e = b.fromJson(json, ChooseWizardEvent.class);
                break;
            case "0001":
                e = b.fromJson(json, PlayAssistantEvent.class);
                break;
            case "0002":
                e = b.fromJson(json, MoveStudentToDiningEvent.class);
                break;
            case "0003":
                e = b.fromJson(json, MoveStudentToIslandEvent.class);
                break;
            case "0004":
                e = b.fromJson(json, MoveMotherNatureEvent.class);
                break;
            case "0005":
                e = b.fromJson(json, PickStudentsFromCloudEvent.class);
                break;
            case "0006":
                e = b.fromJson(json, BuyPlayCharacterEvent.class);
                break;
        }

        return e;
    }

}
