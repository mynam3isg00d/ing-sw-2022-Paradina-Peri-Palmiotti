package Exceptions;

public class UnknownMessageException extends Exception{
    public String toString() {
        return "Unknown message code or wrong message format";
    }
}
