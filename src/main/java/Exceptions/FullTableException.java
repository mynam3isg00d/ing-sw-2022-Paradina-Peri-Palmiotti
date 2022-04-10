package Exceptions;

public class FullTableException extends Exception{
    public String toString() {
        return "The table is full";
    }
}
