package Events;

import java.util.ArrayList;
import java.util.List;

public class BuyPlayCharacterEvent extends GameEvent {

    private Object character;

    public Object getCharacter() {
        return character;
    }

    @Override
    public void parseInput(String input) {
        character = input;
    }

    @Override
    public List<Object> getInput() {
        List<Object> out = new ArrayList();
        out.add(character);
        return out;
    }
}
