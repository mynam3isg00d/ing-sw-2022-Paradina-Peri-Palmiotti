package Events;

public class MoveMotherNatureEvent extends GameEvent{

    private int numberOfSteps;

    public MoveMotherNatureEvent() {
        eventId = "0004";
    }

    public MoveMotherNatureEvent(String playerID, int numberOfSteps) {
        eventId = "0004";
        this.playerId = playerID;
        this.numberOfSteps = numberOfSteps;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    @Override
    public void parseInput(String input) {
        numberOfSteps = Integer.parseInt(input);
    }

}
