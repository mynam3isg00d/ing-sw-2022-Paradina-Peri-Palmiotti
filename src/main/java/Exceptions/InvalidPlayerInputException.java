package Exceptions;

public class InvalidPlayerInputException extends Exception {
    @Override
    public String toString() {
        return "Invalid player input";
    }
}
