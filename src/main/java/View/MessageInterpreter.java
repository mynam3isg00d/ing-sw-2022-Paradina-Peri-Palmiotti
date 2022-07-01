package View;

import Exceptions.UnknownMessageException;
import Model.*;
import Network.Messages.Message;
import Util.ShopDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Interpreter for messages sent via json files
 * Used by the client to interpet messages sent by the server, calls methods of the UI of the update of the model
 * or the display of errors or messages
 */
public class MessageInterpreter {

    private UI ui;
    private Gson b;

    public MessageInterpreter(UI ui) {
        this.ui = ui;
        this.b = new GsonBuilder().registerTypeAdapter(Shop.class, new ShopDeserializer()).create();
    }

    /**
     * @param message The json file containing the message
     * @throws UnknownMessageException The message is not among the expected ones
     */
    public void interpret(String message) throws UnknownMessageException{
        JsonObject messageAsJsonObject = b.fromJson(message, JsonObject.class);
        int code = messageAsJsonObject.get("messageCode").getAsInt();
        switch (code/100) {
            case 1:
                switch (code%100) {
                    case 0:
                        //Simple message, cut the code and print as such
                        System.out.println(new Message(messageAsJsonObject.get("message").getAsString()));
                        break;
                }
                break;
            case 3:
                //game init messages
                switch (code%100) {
                    case 1:
                        //Set client id
                        ui.setPlayerID( messageAsJsonObject.get("message").getAsString() );
                        ui.init();
                        break;
                }
                break;
            case 4:
                //Error message, cut the code and print as such
                ui.renderError(new Message(messageAsJsonObject.get("message").getAsString(), true));
                break;
            case 2:
                //Json message, deserialize
                String json = b.toJson(messageAsJsonObject);
                switch (code%100) {
                    case 0:
                        //Board
                        Board board = b.fromJson(json, Board.class);
                        ui.updateModel(board);
                        break;
                    case 1:
                        //GameModel
                        GameModel gameModel = b.fromJson(json, GameModel.class);
                        ui.updateModel(gameModel);
                        break;
                    case 2:
                        //IslandsWrapper
                        IslandsWrapper islandsWrapper = b.fromJson(json, IslandsWrapper.class);
                        ui.updateModel(islandsWrapper);
                        break;
                    case 3:
                        CloudWrapper cloudWrapper = b.fromJson(json, CloudWrapper.class);
                        ui.updateModel(cloudWrapper);
                        break;
                    case 4:
                        Shop shop = b.fromJson(json, Shop.class);
                        ui.updateModel(shop);
                        break;
                    case 5:
                        Player player = b.fromJson(json, Player.class);
                        ui.updateModel(player);
                        break;
                    case 6:
                        Sack sack = b.fromJson(json, Sack.class);
                        ui.updateModel(sack);
                        break;
                }
                break;
            default:
                throw new UnknownMessageException();
        }
    }

}
