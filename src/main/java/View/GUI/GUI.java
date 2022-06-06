package View.GUI;

import Events.EventFactory;
import Exceptions.UnknownMessageException;
import Network.Client;
import Network.Messages.Message;
import Network.Messages.PlayerInfoMessage;
import Util.HelpInterpreter;
import View.CLI.CLI;
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
import java.util.Scanner;

public class GUI extends Application {

    private Stage stage;
    private GUIAdapter guiAdapter;
    private PrintWriter out;
    private BufferedReader in;

    //TODO AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    //                               REMOVE!!!!!!!!!!!!!!!
    private class LocalInput implements Runnable {
        private PrintWriter out;
        private Scanner in;

        public LocalInput(PrintWriter out) {
            this.out = out;
            in = new Scanner(System.in);
        }

        public void asyncSend(String message) {
            out.println(message);
        }

        public void run() {
            while(true) {
                String line = in.nextLine();
                line = EventFactory.stringToEventJson(guiAdapter.getPlayerID(), line);
                out.println(line);
                out.flush();
            }
        }
    }

    private class RemoteInput implements Runnable {

        private String id;
        private BufferedReader in;
        private MessageInterpreter messageInterpreter;

        public RemoteInput (BufferedReader inputSource) {
            this.in = inputSource;
            this.messageInterpreter = new MessageInterpreter(guiAdapter);
        }

        public void run() {
            while(true) {
                try {
                    String line = in.readLine();

                    //I found this on stack overflow lmfaoooooooooooo
                    //It breaks without this
                    //No, I do not know why it works
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
                }
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //1) Get IP
        //2) Build PIM
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

        new Thread(new GUI.RemoteInput(in)).start();

        //TODO: Remove, there's no need
        new Thread(new GUI.LocalInput(out)).start();
    }

    public void sendEvent(String event) {
        event = EventFactory.stringToEventJson(guiAdapter.getPlayerID(), event);
        out.println(event);
        out.flush();
    }
}
