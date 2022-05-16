package View.CLI;

import Exceptions.EmptySackException;
import Model.CloudWrapper;
import Model.Sack;
import org.fusesource.jansi.AnsiConsole;
import org.junit.jupiter.api.Test;

import static org.fusesource.jansi.Ansi.ansi;
import static org.junit.jupiter.api.Assertions.*;

class CLICloudWrapperTest {

    @Test
    void displayTest() throws Exception {

        Sack s = new Sack(120);

        CloudWrapper cw = new CloudWrapper(2, 3);
        CloudWrapper cw1 = new CloudWrapper(3, 4);
        CloudWrapper cw2 = new CloudWrapper(4, 3);

        cw.fillCloud(s.draw(3), 0);
        cw.fillCloud(s.draw(3), 1);

        cw1.fillCloud(s.draw(4), 0);
        cw1.fillCloud(s.draw(4), 1);
        cw1.fillCloud(s.draw(4), 2);

        cw2.fillCloud(s.draw(3), 0);
        cw2.fillCloud(s.draw(3), 1);
        cw2.fillCloud(s.draw(3), 2);
        cw2.fillCloud(s.draw(3), 3);

        CLICloudWrapper clicw = new CLICloudWrapper(cw);
        CLICloudWrapper clicw1 = new CLICloudWrapper(cw1);
        CLICloudWrapper clicw2 = new CLICloudWrapper(cw2);


        AnsiConsole.systemInstall();
        System.out.print( ansi().eraseScreen() );
        clicw.display(0, 0);
        clicw1.display(20, 0);
        clicw2.display(40, 0);

        while (true);
    }

}