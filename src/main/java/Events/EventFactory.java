package Events;

public class EventFactory {
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
}
