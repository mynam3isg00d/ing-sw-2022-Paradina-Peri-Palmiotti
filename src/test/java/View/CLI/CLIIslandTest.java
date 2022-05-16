package View.CLI;

import Model.Island;
import org.fusesource.jansi.AnsiConsole;
import org.junit.jupiter.api.Test;

import static org.fusesource.jansi.Ansi.ansi;


class CLIIslandTest {

    @Test
    void displayTest() {
        Island island = new Island();
        CLIIsland clii = new CLIIsland(island, 0);

        AnsiConsole.systemInstall();
        System.out.print( ansi().eraseScreen() );
        clii.displayLines(0, 0);
    }
}