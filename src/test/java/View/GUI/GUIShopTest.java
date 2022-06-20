package View.GUI;

import Model.Player;
import Model.Sack;
import Model.Shop;
import View.GUI.Controllers.ShopController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUIShopTest extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/fxml/Shop.fxml"));
        Scene scene = new Scene(root.load());
        ShopController shopController = root.getController();

        List<Player> p = new ArrayList<>();

        p.add(new Player("Davide", 0));
        p.add(new Player("Samuele", 1));
        p.add(new Player("Cugola", 2));
        for(Player p0 : p) p0.setPlayerID(p0.getName());

        Shop s = new Shop(p);
        s.initShop(new Sack(120));
        shopController.update(s);

        s = new Shop(p);
        s.initShop(new Sack(120));
        shopController.update(s);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
