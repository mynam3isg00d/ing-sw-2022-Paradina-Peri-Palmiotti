package Events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuyPlayCharacterEvent extends GameEvent {

    public BuyPlayCharacterEvent() {
        eventId = "0006";
    }
    private int cardID;
    private List<Object> parameters;

    public int getCardID() {
        return cardID;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    @Override
    public void parseInput(String input) {

        String[] words = input.split("\\W+");

        cardID = Integer.parseInt(words[0]);
        parameters = Arrays.asList(words);

        //Removes cardID, which is not one of the parameters
        parameters.remove(0);
    }

}
