package View.GUI.Nodes;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class StudentTile extends ImageView {

    private int id;
    private boolean draggable;

    private double startDragX;
    private double startDragY;

    public StudentTile(Integer color, boolean draggable, int id) {
        this.draggable = draggable;
        this.id = id;
        this.setStyle("-fx-effect: dropshadow(gaussian, #000000, 1, 0.3, 1, 1);");
        switch (color) {
            case 0:
                this.setImage(new Image("/graphics/wooden_pieces/student_yellow.png"));
                break;
            case 1:
                this.setImage(new Image("/graphics/wooden_pieces/student_blue.png"));
                break;
            case 2:
                this.setImage(new Image("/graphics/wooden_pieces/student_green.png"));
                break;
            case 3:
                this.setImage(new Image("/graphics/wooden_pieces/student_red.png"));
                break;
            case 4:
                this.setImage(new Image("/graphics/wooden_pieces/student_pink.png"));
                break;
        }

        if(this.draggable) {
            //Define drag methods

            this.setOnDragDetected(mouseEvent -> {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putString("id:" + id);
                db.setContent(content);

                mouseEvent.consume();
            });

            //TODO: follow mouse cursor
        }
    }

    public StudentTile(Integer color, boolean draggable, int id, double size) {
        this.draggable = draggable;
        this.id = id;
        this.setFitHeight(size); this.setFitWidth(size);
        this.setStyle("-fx-effect: dropshadow(gaussian, #000000, 1, 0.3, 1, 1);");
        switch (color) {
            case 0:
                this.setImage(new Image("/graphics/wooden_pieces/student_yellow.png"));
                break;
            case 1:
                this.setImage(new Image("/graphics/wooden_pieces/student_blue.png"));
                break;
            case 2:
                this.setImage(new Image("/graphics/wooden_pieces/student_green.png"));
                break;
            case 3:
                this.setImage(new Image("/graphics/wooden_pieces/student_red.png"));
                break;
            case 4:
                this.setImage(new Image("/graphics/wooden_pieces/student_pink.png"));
                break;
        }

        if(this.draggable) {
            //Define drag methods
            this.setOnDragDetected(mouseEvent -> {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putString("id:" + id);
                db.setContent(content);

                mouseEvent.consume();
            });

            //TODO: follow mouse cursor
        }
    }

    public int getIndex() {return this.id;}
}
