package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seng201.team35.services.CounterService;

/**
 * Controller for the gameSetup.fxml window
 * @author nsr36
 */
public class SetupScreenController {
    @FXML
    private Label defaultLabel;

    @FXML
    private Button defaultButton;

    private CounterService counterService;

    /**
     * Initialize the window
     *
     * @param stage Top level container for this window
     */
    public void init(Stage stage) {
        counterService = new CounterService();
    }

    /**
     * Method to call when our counter button is clicked
     *
     */
    @FXML
    public void nameChanged() {

    }

    @FXML
    public void gameSetupComplete() {

    }
}
