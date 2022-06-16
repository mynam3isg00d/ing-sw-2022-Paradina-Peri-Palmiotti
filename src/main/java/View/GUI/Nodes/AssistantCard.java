package View.GUI.Nodes;

import View.GUI.Controllers.PlayerController;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.util.Random;

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
            this.setStyle(this.getStyle() + "; -fx-effect: dropshadow(gaussian, lime, 6, 0.3, 0, 0)");

            AudioClip card = new AudioClip(this.getClass().getResource("/sfx/card.wav").toExternalForm());
            card.setRate(0.75 + new Random().nextDouble()/4);
            card.setVolume(0.75 + new Random().nextDouble()/2);
            card.play();


            TranslateTransition tt = new TranslateTransition(Duration.millis(200), this);
            tt.setToY(-80);
            tt.play();

            /*
            this.setTranslateY(-80);

             */
        });

        this.setOnMouseExited(mouseEvent -> {
            this.setStyle(this.getStyle().replace("; -fx-effect: dropshadow(gaussian, lime, 6, 0.3, 0, 0)", ""));
            TranslateTransition tt = new TranslateTransition(Duration.millis(200), this);
            tt.setToY(0);
            tt.play();
        });

        this.setOnMouseClicked(mouseEvent -> {
            hand.sendEvent("play assistant " + index);
        });
    }
}
