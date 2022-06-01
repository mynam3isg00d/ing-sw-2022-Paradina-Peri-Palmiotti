package Events;

import java.util.List;

public abstract class GameEvent {
    protected String playerId;

    //a 4-digits unique id used to cast events to the right sub-class
    protected String eventId;

    public String getEventId() { return eventId; }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public abstract void parseInput(String input);

}