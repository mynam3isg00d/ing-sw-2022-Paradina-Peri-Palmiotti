package View.GUI;

import Model.*;
import Network.Messages.Message;
import View.GUI.Controllers.GameSceneController;
import View.UI;

public class GUIAdapter extends UI {

    private GameSceneController gameSceneController;
    private GUI gui;

    public GUIAdapter(GUI gui, GameSceneController gameSceneController) {
        this.gui = gui;
        this.gameSceneController = gameSceneController;
    }

    @Override
    public void display() {

    }

    @Override
    public void init() {
        gameSceneController.connectGUI(gui);
    }

    @Override
    public void updateModel(Board b) {
        if(b.getPlayerID().equals(this.getPlayerID())) {
            gameSceneController.updateMainBoard(b);
        } else {
            gameSceneController.updateBoard(b);
        }
    }

    @Override
    public void updateModel(GameModel gm) {
        gameSceneController.updateGameModel(gm);
    }

    @Override
    public void updateModel(IslandsWrapper iw) {
        gameSceneController.updateIslandsWrapper(iw);
    }

    @Override
    public void updateModel(CloudWrapper cw) {
        gameSceneController.updateCloudWrapper(cw);
    }

    @Override
    public void updateModel(Shop s) {
        gameSceneController.updateShop(s);
    }

    @Override
    public void updateModel(Player p) {
        if(p.getPlayerID().equals(this.getPlayerID())) {
            gameSceneController.updateMainPlayer(p);
        } else {
            gameSceneController.updatePlayer(p);
        }
    }

    @Override
    public void updateModel(Sack s) {

    }

    @Override
    public void renderError(Message m) {
        gameSceneController.renderError(m.getMessage());
    }
}
