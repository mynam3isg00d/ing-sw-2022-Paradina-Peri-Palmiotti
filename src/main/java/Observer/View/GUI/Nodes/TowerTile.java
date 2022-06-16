package Observer.View.GUI.Nodes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TowerTile extends ImageView {

    public TowerTile(int teamID, double size, boolean rotate) {
        this.setFitHeight(size); this.setFitWidth(size);
        this.setStyle("-fx-effect: dropshadow(gaussian, #000000, 1, 0.3, 1, 1);");
        if(rotate) this.setRotate(-90);
        switch(teamID) {
            case 0:
                this.setImage(new Image("/graphics/wooden_pieces/white_tower.png"));
                break;
            case 1:
                this.setImage(new Image("/graphics/wooden_pieces/black_tower.png"));
                break;
            case 2:
                this.setImage(new Image("/graphics/wooden_pieces/grey_tower.png"));
                break;
        }
    }
}
