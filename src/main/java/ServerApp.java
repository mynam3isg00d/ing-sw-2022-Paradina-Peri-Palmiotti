import Network.Server;

public class ServerApp {

    public static void main() {
        Server server;
        try {
            server = new Server();
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CANNOT RUN THE SERVER KITTEMMUORT");
        }
    }
}
