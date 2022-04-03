package Exceptions;

public class EmptySackException extends Exception{
    public String getMessage() {
        return "Sack is empty, trmo";
    }
}
