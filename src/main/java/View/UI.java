package View;

import Model.*;

public abstract class UI {

    private String playerID;

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public abstract void display();

    public abstract void updateModel(Board b);
    public abstract void updateModel(GameModel gm);
    public abstract void updateModel(IslandsWrapper iw);
    public abstract void updateModel(CloudWrapper cw);
    public abstract void updateModel(Shop s);
    public abstract void updateModel(Player p);
    public abstract void updateModel(Sack s);

}
