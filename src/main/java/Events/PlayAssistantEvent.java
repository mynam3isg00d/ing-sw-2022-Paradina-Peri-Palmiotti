package Events;

import Controller.Game;

import java.util.ArrayList;
import java.util.List;

public class PlayAssistantEvent extends GameEvent {

    public Object playedAssistant;

    public void parseInput(String input){
        this.playedAssistant = input;
    }

    public List<Object> getInput(){
        List<Object> out = new ArrayList();
        out.add(playedAssistant);
        return out;
    }
}
