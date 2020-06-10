package plantenApp;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;
import plantenApp.java.model.LoginMethods;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class ControllerAanvraagForm {
    public TextField txtVoornaam;
    public TextField txtAchternaam;
    public TextField txtEmail;
    public Button btnVerzend;
    public Button btnAnnuleer;
    public Label lblValidateEmail;
    public AnchorPane anchorPane;

    private Connection dbConnection;
    private GebruikerDAO gebruikerDAO;
    private Gebruiker user;

    /**
     * Author Bart Maes
     * bij opstarten connectie en gebruikerDao aanroepen
     */
    public void initialize() throws SQLException {
        dbConnection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(dbConnection);
        //valideren of het een geldig emailadres is
        LoginMethods.checkEmail(txtEmail, lblValidateEmail);
    }

    public void click_VerzendAanvraag(MouseEvent mouseEvent) throws SQLException {
        String sVoornaam = txtVoornaam.getText();
        String sAchternaam = txtAchternaam.getText();
        String sEmail = txtEmail.getText();

        if (!lblValidateEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Gelieve eerst een geldig e-mailadres in te vullen", "Ongeldig e-mailadres", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //nogmaals check of alles ingevuld is (vooral de check of voornaam en achternaam zijn belangrijk, e-mail is al gecheckt)
            if (sVoornaam.trim().isEmpty() || sAchternaam.trim().isEmpty() || sEmail.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Gelieve alle velden in te vullen", "Ongeldige ingave", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //controleren of user bestaat
                user = gebruikerDAO.getByEmail(sEmail);

                if (user == null) {
                    //hier aanvraag uitvoeren
                    int iGelukt = gebruikerDAO.insertAanvraag(sEmail, sVoornaam, sAchternaam);
                    if(iGelukt == 1) {
                        JOptionPane.showMessageDialog(null, "Uw aanvraag werd succesvol verzonden", "Aanvraag succesvol verzonden.", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Er is een fout opgetreden bij het versturen van uw aanvraag. Gelieve opnieuw te proberen.", "Fout opgetreden!", JOptionPane.INFORMATION_MESSAGE);
                    }

                } else {
                    //controleren of er al een aanvraag gebeurd is
                    if (user.isAanvraag_status() == 0) {
                        JOptionPane.showMessageDialog(null, "U heeft reeds een aanvraag ingediend, maar deze is helaas afgekeurd.", "Aanvraag afgekeurd.", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if (user.isAanvraag_status() == 1) {
                            JOptionPane.showMessageDialog(null, "U heeft reeds een aanvraag ingediend, maar deze is nog in behandeling.", "Aanvraag in behandeling.", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            //aanvraag_goedgekeurd == 2
                            JOptionPane.showMessageDialog(null, "U heeft reeds een aanvraag ingediend en deze is reeds goedgekeurd. Gelieve u te registreren.", "Aanvraag goedgekeurd.", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                }


            }
        }
    }

    public void click_AnnuleerAanvraag(MouseEvent mouseEvent) {
        LoginMethods.OptionDialog("Bent u zeker dat u de aanvraag wilt annuleren?",
                "Annuleren", anchorPane, getClass(), "view/Inloggen.fxml", "view/AanvraagToegang.fxml");
    }
}
