package Exceptions;

public class NoSuchPlayerException extends Exception {
    @Override
    public String toString() {
        return "No Such Player, mon frere";
    }
}
