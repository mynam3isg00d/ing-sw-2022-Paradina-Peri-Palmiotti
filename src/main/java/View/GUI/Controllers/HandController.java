package View.GUI.Controllers;

import Model.Assistant;
import Model.Hand;
import Model.Player;
import View.GUI.Nodes.AssistantCard;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

import java.util.List;

public class HandController {

    @FXML
    private FlowPane hand;

    public void update(Player p) {
        Hand h = p.getHand();
        List<Assistant> ass = h.getHand();

        hand.getChildren().removeAll();
        for(int i=0; i<ass.size(); i++) {
            hand.getChildren().add(new AssistantCard(ass.get(i).getOrderNumber(), i, 100, 150));
        }
    }
}
