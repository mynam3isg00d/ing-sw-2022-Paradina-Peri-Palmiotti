package Events;

import java.util.ArrayList;
import java.util.List;

public class PlayAssistantEvent extends GameEvent {

    public int playedAssistant;

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

}
