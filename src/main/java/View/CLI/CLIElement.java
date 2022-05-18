package View.CLI;

import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public abstract class CLIElement {

    protected List<String> lines;

    public int getX() {
        return lines.get(0).length();
    }
    public int getY() {
        return lines.size();
    }

    public void displayLines(int x, int y) {
        System.out.print(ansi().cursor(0,0).cursorRight(x).cursorDown(y));
        int len = lines.get(0).length();
        for(String l : lines) {
            System.out.print(ansi().render(l));
            System.out.print(ansi().cursorDown(1).cursorLeft(len));
        }
    }
}
