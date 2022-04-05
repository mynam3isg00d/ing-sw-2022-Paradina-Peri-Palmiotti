package Exceptions;

public class FullTableException extends Exception{
    public String getMessage() {
        return "The table is full";
    }
}
