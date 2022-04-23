package Events;

public class EventFactory {
    public static GameEvent getGenericEvent() throws Exception {
        return new GameEvent(0);
    }
}
