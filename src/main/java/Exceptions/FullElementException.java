package Exceptions;

public class FullElementException extends Exception {
    public String toString() {
        return "Element is full, can't add any more items";
    }
}
