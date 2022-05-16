package View.CLI;

import Model.IslandsWrapper;
import org.fusesource.jansi.AnsiConsole;
import org.junit.jupiter.api.Test;

import static org.fusesource.jansi.Ansi.ansi;
import static org.junit.jupiter.api.Assertions.*;

class CLIIslandWrapperTest {

    @Test
    void displayTest() {

        IslandsWrapper islandsWrapper = new IslandsWrapper();
        CLIIslandWrapper cliiw = new CLIIslandWrapper(islandsWrapper);

        AnsiConsole.systemInstall();
        System.out.print( ansi().eraseScreen() );
        cliiw.display(0, 0);
        while (true);
    }

}