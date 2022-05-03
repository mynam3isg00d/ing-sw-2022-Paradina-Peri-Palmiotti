package Exceptions;

public class InsufficientCoinsException extends Exception{
    @Override
    public String toString() {
        return "Insufficient coins";
    }
}

