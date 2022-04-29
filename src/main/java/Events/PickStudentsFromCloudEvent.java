package Events;

import Controller.Game;

import java.util.ArrayList;
import java.util.List;

public class PickStudentsFromCloudEvent extends GameEvent {

    private Object cloud;

    @Override
    public void parseInput(String input) {
        cloud = input;
    }

    @Override
    public List<Object> getInput() {
        List<Object> out = new ArrayList();
        out.add(cloud);
        return out;
    }
}
