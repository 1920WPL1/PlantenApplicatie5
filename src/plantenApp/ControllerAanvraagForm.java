package plantenApp;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import plantenApp.java.model.LoginMethods;

import javax.swing.*;

public class ControllerAanvraagForm {
    public TextField txtVoornaam;
    public TextField txtAchternaam;
    public TextField txtEmail;
    public TextArea txtRedenAanvraag;
    public Button btnVerzendAanvraag;
    public Button btnAnnuleerAanvraag;


    public void click_VerzendAanvraag(MouseEvent mouseEvent) {

    }

    public void click_AnnuleerAanvraag(MouseEvent mouseEvent) {
        int dialogButton = JOptionPane.showConfirmDialog(null,
                "Bent u zeker dat u de aanvraag wilt annuleren?",
                "Annuleren", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (dialogButton == JOptionPane.YES_OPTION) {
            LoginMethods.loadScreen(mouseEvent, getClass() ,"view/Inloggen.fxml");
        }

    }
}
