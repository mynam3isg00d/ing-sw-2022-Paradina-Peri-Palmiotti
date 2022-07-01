package View.GUI.Nodes;

import View.GUI.GUIClient;

public abstract class GUILeaf {

    protected GUIClient gui;
    public void connectGUI(GUIClient gui) {this.gui = gui;}
    protected void sendEvent(String event) {
        gui.sendEvent(event);
    }
}
