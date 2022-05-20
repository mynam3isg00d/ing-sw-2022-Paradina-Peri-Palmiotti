package Events;

public class PickStudentsFromCloudEvent extends GameEvent {

    private int cloudIndex;

    public PickStudentsFromCloudEvent() {
        eventId = "0005";
    }

    public PickStudentsFromCloudEvent(String playerID, int cloudIndex) {
        eventId = "0005";
        this.playerId = playerID;
        this.cloudIndex = cloudIndex;
    }

    public int getCloudIndex() {
        return cloudIndex;
    }

    @Override
    public void parseInput(String input) {
        cloudIndex = Integer.parseInt(input);
    }

}
