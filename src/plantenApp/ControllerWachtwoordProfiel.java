package plantenApp;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;
import plantenApp.java.model.LoginMethods;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class ControllerWachtwoordProfiel {

    public Button btnVeranderWachtwoord;
    public Button btnBevestigWw;
    public PasswordField pfWachtwoord;
    public PasswordField pfWwHerhalen;
    public PasswordField pfOldPassword;
    public Label lblCheckNewPassword;
    public AnchorPane anchorPane;

    public Gebruiker user;
    private GebruikerDAO gebruikerDAO;

    /**
     * @author Bart Maes
     * bij laden connectie en gebruikerDao aanroepen
     */
    public void initialize() throws SQLException {
        Connection dbConnection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(dbConnection);
        user = LoginMethods.userLoggedIn;
        enableNewPassword(false);
    }

    /**
     * @author Bart Maes
     * bevestigen van het huidige wachtwoord
     */
    public void Click_bevestigWachtwoord(ActionEvent actionEvent) throws NoSuchAlgorithmException {
        String sPW = pfOldPassword.getText();
        //controleren of het ingegeven wachtwoord klopt met wat in de database zit
        if (!LoginMethods.CheckPasswordCorrect(user, sPW)) {
            JOptionPane.showMessageDialog(null, "Het opgegeven wachtwoord klopt niet.",
                    "Ongeldig wachtwoord", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //indien wachtwoord klopt, worden de velden voor het nieuwe wachtwoord weergegeven en wordt de bovenstaande velden bewerkt
            enableNewPassword(true);
            btnBevestigWw.setVisible(false);
            pfOldPassword.setDisable(true);
            //enkel eerste wachtwoord controleren, controle of beide gelijk zijn, gebeurt bij wijzigen
            LoginMethods.checkPassword(pfWachtwoord, lblCheckNewPassword);
        }
    }

    /**
     * @author Bart Maes
     * wachtwoord effectief wijzigen
     */

    public void Click_wijzigWachtwoord(ActionEvent actionEvent) throws Exception {
        String sWw = pfWachtwoord.getText();
        String sWw_herhaling = pfWwHerhalen.getText();

        //controle op lege velden
        if (sWw.trim().isEmpty() || sWw_herhaling.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Gelieve alle velden in te vullen", "Ongeldige ingave", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //hier de controle of beide wachtwoorden gelijk zijn
            if (!sWw.equals(sWw_herhaling)) {
                JOptionPane.showMessageDialog(null, "Beide wachtwoorden moeten gelijk zijn aan elkaar", "Ongeldige invage", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //hier controleren of het nieuw ingegeven wachtwoord niet hetzelfde is als het 'oude'/huidige wachtwoord, want dan moet er niets van update gebeuren
                if(sWw.equals(pfOldPassword.getText())) {
                    JOptionPane.showMessageDialog(null, "Het nieuwe wachtwoord is gelijk aan het oude wachtwoord. Er wordt niets veranderd.", "Geen wijzigingen", JOptionPane.INFORMATION_MESSAGE);
                    //indien dit het geval is, dan worden de twee wachtwoord-velden leeg gemaakt om eventueel een nieuw wachtwoord in te geven
                    pfWachtwoord.clear();
                    pfWwHerhalen.clear();
                } else {
                    //indien alles ok, mag de registratie gebeuren
                    //@author Bart Maes
                    LoginMethods.createPassword(gebruikerDAO, user, sWw);
                    JOptionPane.showMessageDialog(null, "Uw wachtwoord is succesvol gewijzigd","Wachtwoord gewijzigd", JOptionPane.INFORMATION_MESSAGE);
                    LoginMethods.loadScreen(anchorPane, getClass(), "view/HoofdScherm.fxml");
                }
            }
        }
    }

    //in aparte methode om dubbele code te vermijden
    //alles voor nieuw wachtwoord in te stellen visible maken of hiden
    private void enableNewPassword(Boolean b) {
        btnVeranderWachtwoord.setVisible(b);
        pfWachtwoord.setVisible(b);
        pfWwHerhalen.setVisible(b);
    }

    //ga terug naar inlogscherm
    public void click_home(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/HoofdScherm.fxml");
    }
}
