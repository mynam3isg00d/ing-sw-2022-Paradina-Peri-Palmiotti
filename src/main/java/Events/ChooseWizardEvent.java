package Events;

public class ChooseWizardEvent extends GameEvent{
    private int wizardID;

    public ChooseWizardEvent() {
        eventId = "0000";
    }

    public ChooseWizardEvent(String playerID, int wizardID) {
        eventId = "0000";
        this.playerId = playerID;
        this.wizardID = wizardID;
    }

    public int getWizardID() {
        return wizardID;
    }

    @Override
    public void parseInput(String input) {
        wizardID = Integer.parseInt(input);
    }

}
