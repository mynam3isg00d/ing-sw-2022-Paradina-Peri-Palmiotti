package View.GUI.Nodes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TowerTile extends ImageView {

    public TowerTile(int teamID, double size) {
        this.setFitHeight(size); this.setFitWidth(size);
        this.setStyle("-fx-effect: dropshadow(gaussian, #000000, 1, 0.3, 1, 1);");
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
