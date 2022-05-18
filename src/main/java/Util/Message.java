package Util;

public class Message {
    private String message;
    private boolean isError;

    public Message(String message) {
        this.message = message;
        isError = false;
    }

    public Message(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    public String toString() {
        if (isError)
            return "@|red ERROR: " + message + "|@";
        return message;
    }
}
