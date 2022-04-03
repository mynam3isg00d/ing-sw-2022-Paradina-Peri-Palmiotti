package Exceptions;

public class AssistantMissingException extends Exception{
    public String getMessage() {
        return "No assistant found for that number";
    }
}