package Exceptions;

public class InvalidMoveException extends Exception{
    public String getMessage() {
        return "EX: Invalid Move!!! (Signals a move which is prohibited by the game rules)";
    }
}
