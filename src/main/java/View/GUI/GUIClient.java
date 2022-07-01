package View.GUI;

import Events.EventFactory;
import Exceptions.UnknownMessageException;
import Network.Messages.PlayerInfoMessage;
import View.GUI.Controllers.GameConfigController;
import View.GUI.Controllers.GameSceneController;
import View.GUI.Controllers.IpController;
import View.MessageInterpreter;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GUIClient extends Application {

    private Stage stage;
    private GUIAdapter guiAdapter;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isAlive = true;

    private class RemoteInput implements Runnable {

        private String id;
        private BufferedReader in;
        private MessageInterpreter messageInterpreter;

        public RemoteInput (BufferedReader inputSource) {
            this.in = inputSource;
            this.messageInterpreter = new MessageInterpreter(guiAdapter);
        }

        public void run() {
            while(isAlive) {
                try {
                    String line = in.readLine();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                messageInterpreter.interpret(line);
                            } catch (UnknownMessageException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        FXMLLoader nextLoader = new FXMLLoader(getClass().getResource("/fxml/ServerDied.fxml"));
                        Parent nextRoot = nextLoader.load();
                        stage.getScene().setRoot(nextRoot);
                    } catch (IOException e1) {
                        System.out.println("failed to load fxml");
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //1) Get IP
        //2) Build PlayerInfoMessage
        //3) Waiting room
        //4) Connection

        //1)
        stage = primaryStage;
        FXMLLoader root = new FXMLLoader(getClass().getResource("/fxml/IpChoice.fxml"));
        Scene scene = new Scene(root.load());
        IpController ipController = root.getController();
        ipController.connectGUI(this);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void connectIP(String ip) {
        try {
            Socket clientSocket = new Socket(ip, 42069);
            System.out.println("Connection OK!");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //IP CONNECTED, change scene!
            FXMLLoader nextLoader = new FXMLLoader(getClass().getResource("/fxml/GameConfig.fxml"));
            Parent nextRoot = nextLoader.load();
            stage.getScene().setRoot(nextRoot);
            GameConfigController gameConfigController = nextLoader.getController();
            gameConfigController.connectGUI(this);

        } catch (IOException e) {
            System.out.println("Error, try again pls");
        }
    }

    public void sendPlayerInfoMessage(PlayerInfoMessage pim) {
        //2)
        try {
            out.println(new GsonBuilder().serializeNulls().create().toJson(pim));
        } catch (Exception e) {System.out.println("Uh, oh something went wrong");}

        try {
            FXMLLoader nextLoader = new FXMLLoader(getClass().getResource("/fxml/GameScene.fxml"));
            Parent nextRoot = nextLoader.load();
            stage.getScene().setRoot(nextRoot);
            GameSceneController gameSceneController = nextLoader.getController();
            guiAdapter = new GUIAdapter(this, gameSceneController);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new GUIClient.RemoteInput(in)).start();
    }

    public void sendEvent(String event) {
        event = EventFactory.stringToEventJson(guiAdapter.getPlayerID(), event);
        out.println(event);
        out.flush();
    }

    @Override
    public void stop(){
        System.out.println("closing...");
        isAlive = false;

        //It doesn't matter what this message is, it just needs it to pass through the in.nextLine() and
        //let RemoteInput's run end normally.
        out.println("close");
        out.flush();
    }
}
