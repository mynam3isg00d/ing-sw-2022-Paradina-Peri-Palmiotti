package View.GUI.Nodes;

import View.GUI.Controllers.PlayerController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AssistantCard extends ImageView {

    private PlayerController handController;
    private String playerID;
    private int index;
    private double sizex, sizey;

    //Assistant in hand
    public AssistantCard(PlayerController hand, int cardId, int index, double sizex, double sizey) {
        this.handController = hand;
        this.sizex = sizex;
        this.sizey = sizey;
        this.setFitWidth(sizex); this.setFitHeight(sizey);
        this.setImage(new Image("/graphics/wooden_pieces/Cards/Assistente (" + cardId + ").png"));
        this.index = index;

        this.setOnMouseEntered(mouseEvent -> {
            this.setTranslateY(-80);
            this.setStyle(this.getStyle() + "; -fx-effect: dropshadow(gaussian, lime, 6, 0.3, 0, 0)");
        });

        this.setOnMouseExited(mouseEvent -> {
            this.setTranslateY(0);
            this.setStyle(this.getStyle().replace("; -fx-effect: dropshadow(gaussian, lime, 6, 0.3, 0, 0)", ""));
        });

        this.setOnMouseClicked(mouseEvent -> {
            hand.sendEvent("play assistant " + index);
        });
    }
}
