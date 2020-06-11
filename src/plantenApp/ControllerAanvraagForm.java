package plantenApp;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
     * @Author Bart Maes
     * bij opstarten connectie en gebruikerDao aanroepen
     */
    public void initialize() throws SQLException {
        dbConnection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(dbConnection);
        //valideren of het een geldig emailadres is
        LoginMethods.checkEmail(txtEmail, lblValidateEmail);
    }

    /**
     * @Author Bart Maes
     * controles bij versturen van aanvraag + wegschrijven in database
     */

    public void click_VerzendAanvraag(ActionEvent actionEvent){
        String sVoornaam = txtVoornaam.getText();
        String sAchternaam = txtAchternaam.getText();
        String sEmail = txtEmail.getText();

        //controle of de realtime check op e-mail nog een foutmelding geeft of niet
        if (!lblValidateEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Gelieve eerst een geldig e-mailadres in te vullen", "Ongeldig e-mailadres", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //nogmaals check of alles ingevuld is (vooral de check op voornaam en achternaam zijn belangrijk, e-mail is eigenlijk al gecheckt)
            if (sVoornaam.trim().isEmpty() || sAchternaam.trim().isEmpty() || sEmail.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Gelieve alle velden in te vullen", "Ongeldige ingave", JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    //controleren of user bestaat
                    user = gebruikerDAO.getByEmail(sEmail);

                    //enkel de aanvraag uitvoeren als de persoon nog niet in database zit
                    //als de gebruiker wel al in de database zit (ook al is aanvraag in behandeling) is een aanvraag niet nuttig
                    if (user == null) {
                        //hier aanvraag uitvoeren
                        int iGelukt = gebruikerDAO.insertAanvraag(sEmail, sVoornaam, sAchternaam);
                        //pop-up als de aanvraag goed is verzonden
                        if (iGelukt == 1) {
                            JOptionPane.showMessageDialog(null, "Uw aanvraag werd succesvol verzonden. Wij houden u verder op de hoogte via mail.", "Aanvraag succesvol verzonden.", JOptionPane.INFORMATION_MESSAGE);
                            LoginMethods.loadScreen(anchorPane, getClass(), "view/Inloggen.fxml");
                        } else {
                            JOptionPane.showMessageDialog(null, "Er is een fout opgetreden bij het versturen van uw aanvraag. Gelieve opnieuw te proberen.", "Fout opgetreden!", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        //voor het geval er toch een ander e-mailadres ingegeven wordt (dan waarop gecheckt werd vooraleer naar dit scherm door te sturen) die wel al bestaat in de database
                        JOptionPane.showMessageDialog(null, "Uw bent reeds gekend in ons systeem. U hoeft geen aanvraag in te dienen.", "Aanvraag overbodig.", JOptionPane.INFORMATION_MESSAGE);
                        LoginMethods.loadScreen(anchorPane, getClass(), "view/Inloggen.fxml");
                    }
                } catch (SQLException e){
                    JOptionPane.showMessageDialog(null, "Geen verbinding met de server \r\n Contacteer uw systeembeheer indien dit probleem blijft aanhouden","Geen verbinding", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * @Author Bart Maes
     * bij annuleren, terug naar inlogscherm
     */
    public void click_AnnuleerAanvraag(ActionEvent actionEvent) {
        LoginMethods.OptionDialog("Bent u zeker dat u de aanvraag wilt annuleren?",
                "Annuleren", anchorPane, getClass(), "view/Inloggen.fxml", "view/AanvraagToegang.fxml");
    }
}
