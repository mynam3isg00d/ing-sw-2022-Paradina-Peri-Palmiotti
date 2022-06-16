package Observer.View.GUI.Controllers;

import Model.GameModel;
import Model.Phase;
import Observer.View.GUI.Nodes.GUILeaf;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PickWizardController extends GUILeaf {

    @FXML
    ImageView w0, w1, w2, w3;
    @FXML
    Text text;
    @FXML
    Pane main;

    public void pickWizard(MouseEvent mouseEvent) {
        sendEvent("choose wizard " + ((Node)mouseEvent.getTarget()).getId().replace("w",""));
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        ScaleTransition st = new ScaleTransition(Duration.millis(50), (Node)mouseEvent.getTarget());
        st.setToX(1.2);
        st.setToY(1.2);
        st.play();
    }

    public void mouseExited(MouseEvent mouseEvent) {
        ScaleTransition st = new ScaleTransition(Duration.millis(50), (Node)mouseEvent.getTarget());
        st.setToX(1);
        st.setToY(1);
        st.play();
    }

    public void update(GameModel gm) {
        if (gm.getGamePhase().equals(Phase.SETUP)) {
            boolean[] wizards = gm.getPickedWizards();
            String currPlayer = gm.getCurrentPlayer().getName();

            text.setText(currPlayer + ", choose your Wizard!");
            w0.setVisible(!wizards[0]);
            w1.setVisible(!wizards[1]);
            w2.setVisible(!wizards[2]);
            w3.setVisible(!wizards[3]);
        } else {
            main.setVisible(false);
        }
    }
}
