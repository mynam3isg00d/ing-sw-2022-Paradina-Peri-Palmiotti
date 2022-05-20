package Events;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public static GameEvent eventFromJson(String jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, PlayAssistantEvent.class);
    }

    public static String stringToEventJson(String playerID, String line) {
        Gson b = new GsonBuilder().serializeNulls().create();

        //Regex patterns
        Pattern wizard = Pattern.compile("^(choose wizard )[0123]");

        if (wizard.matcher(line).find()) {
            int wint = Integer.parseInt(line.split(" ")[2]);
            ChooseWizardEvent event = new ChooseWizardEvent(playerID, wint);
            return b.toJson(event);
        }

        return line;
    }

}
