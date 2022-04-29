package Events;

import java.util.ArrayList;
import java.util.List;

public class MoveMotherNatureEvent extends GameEvent{

    private Object numberOfSteps;

    public Object getNumberOfSteps() {
        return numberOfSteps;
    }

    @Override
    public void parseInput(String input) {
        numberOfSteps = input;
    }

    @Override
    public List<Object> getInput() {
        List<Object> out = new ArrayList();
        out.add(numberOfSteps);
        return out;
    }
}
