package View.GUI.Controllers;

import Model.Assistant;
import Model.Hand;
import Model.Player;
import View.GUI.Nodes.AssistantCard;
import View.GUI.Nodes.GUILeaf;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.List;

public class PlayerController extends GUILeaf {

    private String name = null;
    private boolean interactable = false;
    @FXML
    private Pane main;
    @FXML
    private FlowPane hand;
    @FXML
    private ImageView assistantInPlay;

    public void initialize() {
        assistantInPlay.setVisible(false);
    }

    public void update(Player p) {
        this.name = p.getName();
        if (interactable) {
            if (p.getHand() != null) {
                Hand h = p.getHand();
                List<Assistant> ass = h.getHand();

                int prevSize = hand.getChildren().size();
                hand.getChildren().remove(0, prevSize);
                for(int i=0; i<ass.size(); i++) {
                    hand.getChildren().add(new AssistantCard(this, ass.get(i).getOrderNumber(), i, 100, 150));
                }
            }
        }

        if (p.getAssistantInPlay() == null) {
            assistantInPlay.setVisible(false);
        } else {
            assistantInPlay.setVisible(true);
            assistantInPlay.setImage(new Image("/graphics/wooden_pieces/Cards/Assistente (" + p.getAssistantInPlay().getOrderNumber() + ").png"));
        }
    }

    public void setInteractable(boolean b) {
        this.interactable = b;
    }

    public String getName() {
        return name;
    }

    public void setMouseTransparent(boolean b) {
        main.setMouseTransparent(b);
    }
}
