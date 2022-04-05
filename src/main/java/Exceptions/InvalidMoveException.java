package Exceptions;

public class InvalidMoveException extends Exception{
    //hint contains a message that will be shown to the player, in order to tell him which part of his move is invalid
    final private String hint;

    public InvalidMoveException(String m) {
        hint = m;
    }

    public String getMessage() {
        return "EX: Invalid Move!!! (Signals a move which is prohibited by the game rules)" + "\n" + hint;
    }
}
