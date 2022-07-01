package Network.Messages;

/**
 * A message class used to communicate a player his identity on the server.
 * Sent from the server to the client as a Json Object.
 */
public class PlayerInfoMessage {
    private String name;
    private int playerNumber;
    private boolean expert;

    public PlayerInfoMessage(String n, int pn, String e) {
        name = n;
        playerNumber = pn;

        if (e.equals("Y")) {
            expert = true;
        } else {
            expert = false;
        }
    }

    public String getName() {
        return name;
    }

    public boolean isExpert() {
        return expert;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
