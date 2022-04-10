package Exceptions;

public class AssistantMissingException extends Exception{
    @Override
    public String toString() {
        return "No assistant found for that number";
    }
}