package Exceptions;

public class EmptyElementException extends Exception {
    public String toString() {
        return "Element is already empty, can't remove any items";
    }
}
