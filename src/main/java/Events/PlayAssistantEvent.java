package Events;

public class PlayAssistantEvent extends GameEvent {

    private int playedAssistant;

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
