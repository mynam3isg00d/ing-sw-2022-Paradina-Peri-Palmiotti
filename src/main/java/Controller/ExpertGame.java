package Controller;

import Model.Player;
import View.RemoteView;

import java.util.List;

public class ExpertGame extends Game {

    private CharacterController characterController;

    public ExpertGame(List<Player> players) {
        super(players);

        //init characterController
    }

    public void addObserversToModelComponents(RemoteView rv) {
        super.addObserversToModelComponents(rv);
        characterController.addObserverToModel(rv);
    }
}
