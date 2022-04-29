package Events;

import java.util.ArrayList;
import java.util.List;

public class ChooseWizardEvent extends GameEvent{

    private Object wizard;

    @Override
    public void parseInput(String input) {
        wizard = input;
    }

    @Override
    public List<Object> getInput() {
        List<Object> out = new ArrayList();
        out.add(wizard);
        return out;
    }
}
