package Events;

import java.util.Arrays;
import java.util.List;

public class BuyPlayCharacterEvent extends GameEvent {

    private int cardID;
    private List<String> parameters;

    public BuyPlayCharacterEvent() {
        eventId = "0006";
    }

    public BuyPlayCharacterEvent(String playerID, int cardID, List<String> parameters) {
        eventId = "0006";
        this.playerId = playerID;
        this.cardID = cardID;
        this.parameters = parameters;
    }

    public int getCardID() {
        return cardID;
    }

    public List<String> getParameters() {
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
