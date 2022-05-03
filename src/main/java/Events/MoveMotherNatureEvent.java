package Events;

public class MoveMotherNatureEvent extends GameEvent{

    public MoveMotherNatureEvent() {
        eventId = "0004";
    }
    private int numberOfSteps;

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    @Override
    public void parseInput(String input) {
        numberOfSteps = Integer.parseInt(input);
    }

}
