package Events;

import com.google.gson.Gson;

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


}
