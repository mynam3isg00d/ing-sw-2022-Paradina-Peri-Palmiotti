package Network.Messages;

/**
 * A generic message class, with a boolean attribute in case it's an error.
 * Sent from the server to the client via Json Serialization.
 */
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

    public String getMessage() {
        return message;
    }
}
