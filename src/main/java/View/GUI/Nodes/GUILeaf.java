package View.GUI.Nodes;

import View.GUI.GUI;

public abstract class GUILeaf {

    protected GUI gui;
    public void connectGUI(GUI gui) {this.gui = gui;}
    protected void sendEvent(String event) {
        gui.sendEvent(event);
    }
}
