package Exceptions;

public class MergeException extends Exception{
    @Override
    public String toString() {
        return "Merge cannot happen, the two island have different influences";
    }
}
