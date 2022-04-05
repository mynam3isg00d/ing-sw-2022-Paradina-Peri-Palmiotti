package Exceptions;

public class NoSuchPlayerException extends Exception {
    @Override
    public String getMessage() {
        return "No Such Player, mon frere";
    }
}
