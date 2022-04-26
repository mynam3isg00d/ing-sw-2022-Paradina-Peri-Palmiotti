package Events;

public class GameEvent {

    private Move move;
    private String input;

    public GameEvent(Move move, String input) {
        this.move = move;
        this.input = input;
    }

    public Move getMove() {
        return move;
    }

    public String getInput() {
        return input;
    }
}
