package View.CLI;

import Model.Assistant;

import java.util.ArrayList;

/**
 * Displays the assistants in the CLI
 */
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

    public CLIAssistant(Assistant assistant, boolean masked) {
        this.assistant = assistant;
        lines = new ArrayList<>();

        int m = assistant.getMotherNumber();
        int o = assistant.getOrderNumber();
        lines.clear();
        if (masked) {
            lines.add("[?|?]");
        } else {
            lines.add("[" + o + "|" + m + "]");
        }
    }

}
