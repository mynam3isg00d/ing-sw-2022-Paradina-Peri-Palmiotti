package Exceptions;

public class IllegalAssistantException extends Exception{
    public String toString() {
        return "You can't play this assistant: it has already been played by another player";
    }
}