package Events;

public class PlayAssistantEvent extends GameEvent {

    private int playedAssistant;

    public PlayAssistantEvent() {
        eventId = "0001";
    }
    public PlayAssistantEvent(String playerID, int playedAssistant) {
        eventId = "0001";
        this.playerId = playerID;
        this.playedAssistant = playedAssistant;
    }

    public int getPlayedAssistant() {
        return playedAssistant;
    }

    public void parseInput(String input){
        try{
            this.playedAssistant = Integer.parseInt(input);
        }catch (NumberFormatException e){
            e.printStackTrace();;
        }
    }

    public void setPlayedAssistant(int playedAssistant) {
        this.playedAssistant = playedAssistant;
    }

}
