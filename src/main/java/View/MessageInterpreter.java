package View;

import Exceptions.UnknownMessageException;
import Model.*;
import Util.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import static org.fusesource.jansi.Ansi.ansi;

public class MessageInterpreter {

    private UI ui;
    Gson b;

    public MessageInterpreter(UI ui) {
        this.ui = ui;
        this.b = new GsonBuilder().create();
    }

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

            case 3:
                //game init messages
                switch (code%100) {
                    case 0:
                        //Game Started Message - @deprecated
                        System.out.print( ansi().eraseScreen() );
                        break;
                    case 1:
                        //Set client id
                        ui.setPlayerID( messageAsJsonObject.get("message").getAsString() );
                        System.out.print( ansi().eraseScreen() );
                        break;
                }
                break;
            case 4:
                //Error message, cut the code and print as such
                System.out.println(new Message(messageAsJsonObject.get("message").getAsString(), true));
                break;
            case 2:
                //Json message, deserialize
                String json = message.substring(3);
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
                //Default to simple message
                //System.out.println(message);
                throw new UnknownMessageException();
        }
    }

}
