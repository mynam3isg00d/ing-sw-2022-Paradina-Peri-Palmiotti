package View.GUI.Controllers;

import Model.CharacterCard;
import Model.NoTileCard;
import Model.Shop;
import Model.StudentCard;
import View.GUI.Nodes.GUILeaf;
import View.GUI.Nodes.StudentTile;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ShopController extends GUILeaf {

    private CharacterCard[] ccarr;
    @FXML
    private Pane main, player0, player1, player2, player3;

    @FXML
    private ImageView char0, char1, char2, bought0, bought1, bought2;

    @FXML
    private Text coin0, coin1, coin2, coin3, name0, name1, name2, name3;

    @FXML
    private GridPane stud0, stud1, stud2;

    public void initialize() {
        main.setVisible(false);
        player0.setVisible(false);
        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);
        stud0.setMouseTransparent(true); stud1.setMouseTransparent(true); stud2.setMouseTransparent(true);
        bought0.setVisible(false); bought1.setVisible(false); bought2.setVisible(false);
    }

    public void buyCharacter(MouseEvent mouseEvent) {
        int index = Integer.parseInt(((Node)mouseEvent.getTarget()).getId().replace("char", ""));
        try {
            Stage buyStage = new Stage();
            FXMLLoader nextLoader = new FXMLLoader(getClass().getResource("/fxml/Char" + ccarr[index].getCardID() + "Scene.fxml"));
            Parent nextRoot = nextLoader.load();
            CharacterController cc = nextLoader.getController();
            if (ccarr[index].getCardID() == 4) {
                ((GrandmaController) cc).updateNum(((NoTileCard)ccarr[index]).getNoTile());
            }
            cc.connectGUI(this.gui, index);
            buyStage.initModality(Modality.APPLICATION_MODAL);
            buyStage.setScene(new Scene(nextRoot));
            buyStage.show();
        } catch (IOException ignored) {}
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        ScaleTransition st = new ScaleTransition(Duration.millis(50), (Node)mouseEvent.getTarget());
        st.setToX(1.1);
        st.setToY(1.1);
        st.play();
    }

    public void mouseExited(MouseEvent mouseEvent) {
        ScaleTransition st = new ScaleTransition(Duration.millis(50), (Node)mouseEvent.getTarget());
        st.setToX(1);
        st.setToY(1);
        st.play();
    }

    public void update(Shop s) {
        main.setVisible(true);

        ccarr = s.getShop();

        char0.setImage(new Image("graphics/wooden_pieces/Cards/CarteTOT_front" + ccarr[0].getCardID() + ".jpg"));
        char1.setImage(new Image("graphics/wooden_pieces/Cards/CarteTOT_front" + ccarr[1].getCardID() + ".jpg"));
        char2.setImage(new Image("graphics/wooden_pieces/Cards/CarteTOT_front" + ccarr[2].getCardID() + ".jpg"));

        bought0.setVisible(ccarr[0].isIncremented());
        bought1.setVisible(ccarr[1].isIncremented());
        bought2.setVisible(ccarr[2].isIncremented());

        if (ccarr[0] instanceof StudentCard) {
            StudentCard sc = (StudentCard) ccarr[0];

            int prev = stud0.getChildren().size();
            stud0.getChildren().remove(0, prev);

            for(int i=0; i<sc.getMAX_STUDENTS(); i++) {
                stud0.add(new StudentTile(sc.peekStudents().get(i).getColorId(), false, false, -1, 30), i%2, i/2);
            }
        }

        if (ccarr[1] instanceof StudentCard) {
            StudentCard sc = (StudentCard) ccarr[1];

            int prev = stud1.getChildren().size();
            stud1.getChildren().remove(0, prev);

            for(int i=0; i<sc.getMAX_STUDENTS(); i++) {
                stud1.add(new StudentTile(sc.peekStudents().get(i).getColorId(), false, false, -1, 30), i%2, i/2);
            }
        }

        if (ccarr[2] instanceof StudentCard) {
            StudentCard sc = (StudentCard) ccarr[2];

            int prev = stud2.getChildren().size();
            stud2.getChildren().remove(0, prev);

            for(int i=0; i<sc.getMAX_STUDENTS(); i++) {
                stud2.add(new StudentTile(sc.peekStudents().get(i).getColorId(), false, false, -1, 30), i%2, i/2);
            }
        }

        if(s.getPlayerList().size() > 0) {
            player0.setVisible(true);
            name0.setText(s.getPlayerList().get(0).getName());
            coin0.setText("x" + s.getPlayerCoins(s.getPlayerList().get(0).getPlayerID()));
        }

        if(s.getPlayerList().size() > 1) {
            player1.setVisible(true);
            name1.setText(s.getPlayerList().get(1).getName());
            coin1.setText("x" + s.getPlayerCoins(s.getPlayerList().get(1).getPlayerID()));
        }

        if(s.getPlayerList().size() > 2) {
            player2.setVisible(true);
            name2.setText(s.getPlayerList().get(2).getName());
            coin2.setText("x" + s.getPlayerCoins(s.getPlayerList().get(2).getPlayerID()));
        }

        if(s.getPlayerList().size() > 3) {
            player3.setVisible(true);
            name3.setText(s.getPlayerList().get(3).getName());
            coin3.setText("x" + s.getPlayerCoins(s.getPlayerList().get(3).getPlayerID()));
        }
    }
}
