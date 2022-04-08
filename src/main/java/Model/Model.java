//TODO: do we really need this? probably but it's useless for now.

package Model;
import Controller.Game;
import View.*;

public class Model {
    private View view;
    private Game controller;

    private IslandsWrapper islandModel;

    public Model() {
        //initializes all the models
        islandModel = new IslandsWrapper();
        //cloudModel = new CloudModel();
        //boards will be initialized by boardsController
        
    }

    public void connectViews(View v) {
    }

    public IslandsWrapper getIslandModel() {
        return islandModel;
    }
}
