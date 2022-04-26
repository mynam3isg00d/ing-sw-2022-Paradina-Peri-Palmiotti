import Network.Client;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) {
        Client c = new Client("127.0.0.1", 42069);

        try {
            c.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
