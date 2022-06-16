package Observer.View.CLI;

import java.util.ArrayList;

public class CLIFirework extends CLIElement {
    //lmao

    public CLIFirework(int c) {
        lines = new ArrayList<>();

        String color;
        switch (c) {
            case 0: color = "red"; break;
            case 1: color = "yellow"; break;
            case 2: color = "blue"; break;
            case 3: color = "green"; break;
            case 4: color = "magenta"; break;
            case 5: color = "cyan"; break;
            default: color = "white"; break;
        }

        lines.add("         ");
        lines.add("@|" + color + "     .    |@");
        lines.add("@|" + color + "   . | .  |@");
        lines.add("@|" + color + "    \\|/  |@");
        lines.add("@|" + color + "  .---o---.|@");
        lines.add("@|" + color + "    /|\\  |@");
        lines.add("@|" + color + "    ' | '  |@");
        lines.add("@|" + color + "     '    |@");
    }
}
