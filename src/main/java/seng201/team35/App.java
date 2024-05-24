package seng201.team35;

import seng201.team35.gui.FXWindow;

/**
 * Default entry point class
 * @author msh254, nsr36
 */
public class App {
    /**
     * Entry point which runs the javaFX application
     * Due to how JavaFX works we must call FXWindow.launchWrapper() from here
     * @param args program arguments from command line
     */
    public static void main(String[] args) {
        FXWindow.launchWrapper(args);
    }
}
