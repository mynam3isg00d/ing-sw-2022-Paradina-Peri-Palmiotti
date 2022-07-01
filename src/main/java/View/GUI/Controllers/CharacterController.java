package View.GUI.Controllers;

import View.GUI.GUIClient;
import View.GUI.Nodes.GUILeaf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

public abstract class CharacterController extends GUILeaf {

    private int cardIndex;

    protected final ObservableList<Integer> islandIdList = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
    protected final ObservableList<Integer> entranceIdList = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8);
    protected final ObservableList<Integer> cardIdList = FXCollections.observableArrayList(0,1,2,3,4,5);
    protected final ObservableList<Integer> studentColorList = FXCollections.observableArrayList(0,1,2,3,4);

    public void sendOk(ActionEvent actionEvent, Pane main) {
        String toSend = "buy character " + cardIndex;
        for(Node n : main.getChildren()) {
            if (n instanceof ChoiceBox) {
                ChoiceBox cb = (ChoiceBox) n;
                toSend = toSend + " " + cb.getValue();
            }
        }
        toSend = toSend.replace("null", "");
        System.out.println(toSend);
        sendEvent(toSend);
    }

    public void connectGUI(GUIClient gui, int cardIndex) {
        connectGUI(gui);
        this.cardIndex = cardIndex;
    }
}
