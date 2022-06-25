package View.CLI;

import java.util.ArrayList;

/**
 * Displays an empty CLI
 */
public class CLIEmpty extends CLIElement {

    public CLIEmpty(int x, int y) {
        lines = new ArrayList<>();

        for(int i=0; i<y; i++) {
            lines.add(" ".repeat(x));
        }
    }

}
