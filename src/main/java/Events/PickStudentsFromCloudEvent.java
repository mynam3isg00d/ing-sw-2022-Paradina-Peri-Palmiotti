package Events;

public class PickStudentsFromCloudEvent extends GameEvent {

    private int cloudIndex;

    public int getCloudIndex() {
        return cloudIndex;
    }

    @Override
    public void parseInput(String input) {
        cloudIndex = Integer.parseInt(input);
    }

}
