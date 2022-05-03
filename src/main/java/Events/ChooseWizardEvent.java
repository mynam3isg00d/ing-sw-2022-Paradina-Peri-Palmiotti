package Events;

public class ChooseWizardEvent extends GameEvent{
    public ChooseWizardEvent() {
        eventId = "0000";
    }
    private int wizardID;

    public int getWizardID() {
        return wizardID;
    }

    @Override
    public void parseInput(String input) {
        wizardID = Integer.parseInt(input);
    }

}
