package seng201.team35.gui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import seng201.team35.services.CounterService;

import java.awt.*;
public class MainMenuController {
    @FXML
    private Button shopbuttonmainmenu;
    @FXML
    private Button inventorybuttonmainmenu;
    @FXML
    private Button nextroundbuttonmainmenu;
    @FXML
    private Button modifier1button;
    @FXML
    private Button modifier2button;
    @FXML
    private Button modifier3button;
    private Boolean hasmodifierbeenselected = false;
    @FXML
    public void shopclicked() {
        // transiiton to shop screen
    }
    @FXML
    public void inventoryclicked() {
        //transition to inventory screen
    }
    @FXML
    public void nextroundclicked() {
        if (hasmodifierbeenselected = true) {
            // transition to next round
        }
        else {
            // implelement a warning to select a Modifier
        }
    }
    @FXML
    public void modifier1clicked() {
        hasmodifierbeenselected = true;
    }
}
