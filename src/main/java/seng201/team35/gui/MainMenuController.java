package seng201.team35.gui;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import seng201.team35.GameManager;
import seng201.team35.models.Tower;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;

/**
 * Controller for the Main Menu.fxml window
 * @author nsr36, msh254
 */
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
    private TextArea modifiertext1; // make a label
    @FXML
    private TextArea modifiertext2;
    @FXML
    private TextArea modifiertext3;
    @FXML
    private Button modifier2button;
    @FXML
    private Button modifier3button;
    @FXML
    private Label noModifierWarning;
    private final List<String> modifiers = Arrays.asList(new String[]{"Tower Speed Increase 10%", "Tower Speed Increase 5%", "Tower Speed Decrease 5%",
            "Tower Speed Decrease 10%", "Cart Number Decrease by 2", "Cart Number Decrease by 1", "Cart Number Increase by 1", "Cart Number Increase by 2", "Cart Fill Amount Decrease 10%"
    , "Cart Fill Amount Increase 5%", "Cart Fill Amount Increase 10%", "Cart Fill Amount Decrease 5%"});

    private Boolean hasmodifierbeenselected = false;
    private String modifiername;
    private GameManager gameManager;
    public MainMenuController(GameManager x) { gameManager = x; }
    public void initialize() {
        Random rng = new Random();
        int randomModifier1 = rng.nextInt(0,4);
        int randomModifier2 = rng.nextInt(4,8);
        int randomModifier3 = rng.nextInt(8,12);
        modifiertext1.setText(modifiers.get(randomModifier1));
        modifiertext2.setText(modifiers.get(randomModifier2));
        modifiertext3.setText(modifiers.get(randomModifier3));
    }
    @FXML
    public void shopclicked() {
        gameManager.mainMenuToShopScreen();
    }
    @FXML
    public void inventoryclicked() {
        gameManager.mainMenuToInventoryScreen();
    }
    @FXML
    public void nextroundclicked() {
        System.out.println("NextRoundClicked");
        if (hasmodifierbeenselected == true) {
            System.out.println("ModifierBeenSelected");
            gameManager.mainMenuToGameScreen();
        }
        else {
            System.out.println("ModifierNotSelected");
            System.out.println(modifiers);
            noModifierWarning.setText("Please select a Modifier");
        }
    }
    @FXML
    public void modifier1clicked() {
        hasmodifierbeenselected = true;
        modifiername = modifiertext1.getText();
        System.out.println(modifiername);
        System.out.println("Modifier 1 has been selected");

    }
    @FXML
    public void modifier2clicked() {
        hasmodifierbeenselected = true;
        modifiername = modifiertext2.getText();
        System.out.println(modifiername);
        System.out.println("Modifier 2 has been selected");
    }
    @FXML
    public void modifier3clicked() {
        hasmodifierbeenselected = true;
        modifiername = modifiertext3.getText();
        System.out.println(modifiername);
        System.out.println("Modifier 3 has been selected");
    }
}
