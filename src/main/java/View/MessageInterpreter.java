package View;

import Model.*;
import Util.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static org.fusesource.jansi.Ansi.ansi;

public class MessageInterpreter {

    private UI ui;
    Gson b;

    public MessageInterpreter(UI ui) {
        this.ui = ui;
        this.b = new GsonBuilder().create();
    }

    public void interpret(String message) {
        String code = message.substring(0, 3);
        switch (code.charAt(0)) {
            case '1':
                //Simple message, cut the code and print as such
                System.out.println(new Message(message.substring(3)).toString());
                break;
            case '3':
                //Command message
                switch (code.substring(1)) {
                    case "00":
                        //Clear screen
                        System.out.print( ansi().eraseScreen() );
                        break;
                    case "01":
                        //Set client id
                        ui.setPlayerID( message.substring(3) );
                        break;
                }
                break;
            case '4':
                //Error message, cut the code and print as such
                System.out.println(new Message(message.substring(3), true).toString());
                break;
            case '2':
                //Json message, deserialize
                String json = message.substring(3);
                switch (code.substring(1)) {
                    case "00":
                        //Board
                        Board board = b.fromJson(json, Board.class);
                        ui.updateModel(board);
                        break;
                    case "01":
                        //GameModel
                        GameModel gameModel = b.fromJson(json, GameModel.class);
                        ui.updateModel(gameModel);
                        break;
                    case "02":
                        //IslandsWrapper
                        IslandsWrapper islandsWrapper = b.fromJson(json, IslandsWrapper.class);
                        ui.updateModel(islandsWrapper);
                        break;
                    case "03":
                        CloudWrapper cloudWrapper = b.fromJson(json, CloudWrapper.class);
                        ui.updateModel(cloudWrapper);
                        break;
                    case "04":
                        Shop shop = b.fromJson(json, Shop.class);
                        ui.updateModel(shop);
                        break;
                    case "05":
                        Player player = b.fromJson(json, Player.class);
                        ui.updateModel(player);
                        break;
                    case "06":
                        Sack sack = b.fromJson(json, Sack.class);
                        ui.updateModel(sack);
                        break;
                }
                break;
            default:
                //Default to simple message
                System.out.println(message);
        }
    }

}
