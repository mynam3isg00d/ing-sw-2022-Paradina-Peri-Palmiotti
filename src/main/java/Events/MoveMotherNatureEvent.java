package Events;

public class MoveMotherNatureEvent extends GameEvent{
    private String playerId;
    private int numberOfSteps;

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public String getPlayerId() {
        return playerId;
    }
    @Override
    public void parseInput(String input) {
        numberOfSteps = Integer.parseInt(input);
    }

}
