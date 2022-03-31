package Model;
import View.*;

public class Model {
    private View view;
    private Game controller;

    private IslandsWrapper islandModel;

    public Model() {
        //initializes all the models
        islandModel = new IslandsWrapper();
        //cloudModel = new CloudModel();
    }

    public void connectViews(View v) {
        islandModel.connectView(v.getIslandView());
        //cloudModel.connectView();
        //boardModel.connectView();
    }

    public IslandsWrapper getIslandModel() {
        return islandModel;
    }
}
