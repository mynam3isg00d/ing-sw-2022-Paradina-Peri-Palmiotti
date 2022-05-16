package View.CLI;

import Model.Assistant;

import java.util.ArrayList;

public class CLIAssistant extends CLIElement {

    private Assistant assistant;

    public CLIAssistant(Assistant assistant) {
        this.assistant = assistant;
        lines = new ArrayList<>();

        int m = assistant.getMotherNumber();
        int o = assistant.getOrderNumber();
        lines.clear();
        lines.add("[" + o + "|" + m + "]");
    }

}
