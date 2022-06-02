package View.GUI.Nodes;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AssistantCard extends ImageView {

    private int index;
    private double sizex, sizey;

    public AssistantCard(int cardId, int index, double sizex, double sizey) {
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
            System.out.println("play assistant " + index);
        });
    }

}
