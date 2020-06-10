package plantenApp;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import plantenApp.java.model.LoginMethods;


public class ControllerWachtWoordVergeten {
    public TextField txtEmail;
    public Button btnVerstuurEmail;
    public Button btnTerug;
    public AnchorPane anchorPane;

    public void clicked_MailVersturen(MouseEvent mouseEvent) {

    }

    public void clicked_Terug(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/HoofdScherm.fxml");
    }
}