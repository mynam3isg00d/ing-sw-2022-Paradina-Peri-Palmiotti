package View.CLI;

import Model.CharacterCard;
import Model.Shop;
import Model.StudentCard;

import java.util.ArrayList;

public class CLIShop extends CLIElement {

    private Shop shop;
    private CLICharaterCard[] cardArray;

    public CLIShop() {
        lines = new ArrayList<>();
        cardArray = new CLICharaterCard[3];
        lines.add("+-------------------------------------+");
        lines.add("|                                     |");
        lines.add("|                                     |");
        lines.add("|                                     |");
        lines.add("|          Expert Game Only!          |");
        lines.add("|                                     |");
        lines.add("|                                     |");
        lines.add("|                                     |");
        lines.add("+-------------------------------------+");
    }

    public CLIShop(Shop shop) {
        this.shop = shop;
        cardArray = new CLICharaterCard[3];
        lines = new ArrayList<>();

        updateLines();
    }

    public void updateShop(Shop s) {
        this.shop = s;
        updateLines();
    }

    @Override
    public void displayLines(int x0, int y0) {
        super.displayLines(x0, y0);

        CharacterCard[] carr = shop.getShop();
        for(int i=0; i<3; i++) {

            //God i wish i knew how dynamic types worked..
            if (carr[i].getCardID() == 0 || carr[i].getCardID() == 6 || carr[i].getCardID() == 10) {
                cardArray[i] = new CLIStudentCard((StudentCard)carr[i]);
            } else {
                cardArray[i] = new CLICharaterCard(carr[i]);
            }
            cardArray[i].displayLines(x0 + 2 + i * (1+cardArray[0].getX()), y0 + 1);
        }
    }

    private void updateLines() {
        int size = shop.getPlayerList().size();
        String[] p = new String[size];
        String[] c = new String[size];
        for(int i=0; i<size; i++) {
            p[i] = formatName(shop.getPlayerList().get(i).getName());
            c[i] = getNumString(shop.getPlayerCoins(shop.getPlayerList().get(i).getPlayerID()));
        }

        lines.clear();

        lines.add("+--Shop-------------------------------+");
        lines.add("|                                     |");
        lines.add("|                                     |");
        lines.add("|                                     |");
        lines.add("|                                     |");
        lines.add("|                                     |");
        lines.add("| " + p[0] + ": " + c[0] + "c  " + p[1] + ": " + c[1] + "c              |");
        switch(size) {
            case 2:
                lines.add("|                                     |");
                break;
            case 3:
                lines.add("| " + p[2] + ": " + c[2] + "c                          |");
                break;
            case 4:
                lines.add("| " + p[2] + ": " + c[2] + "c  " + p[3] + ": " + c[3] + "c              |");
                break;
        }
        lines.add("+-------------------------------------+");
    }

    private String getNumString(Integer i) {
        String s = "";
        if (i < 10) s = "0" + i.toString();
        if (i >= 10) s = i.toString();
        return s;
    }

    private String formatName(String name) {
        if(name.length() == 5) return name;
        if(name.length() > 5) return name.substring(0, 5);
        return name + " ".repeat(5 - name.length());
    }

}
