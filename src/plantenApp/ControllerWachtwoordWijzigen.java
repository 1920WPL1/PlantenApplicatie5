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
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author Bart Maes
 * controller voor wachtwoord vergeten
 */

public class ControllerWachtwoordWijzigen {

    public Button btnVeranderWachtwoord;
    public PasswordField pfWachtwoord;
    public PasswordField pfWwHerhalen;
    public Label lblCheckPassword;
    public AnchorPane anchorPane;

    public static Gebruiker user;
    private GebruikerDAO gebruikerDAO;

    public void initialize() throws SQLException {
        //connecties
        Connection dbConnection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(dbConnection);

        //enkel eerste wachtwoord controleren, controle of beide gelijk zijn, gebeurt bij versturen
        LoginMethods.checkPassword(pfWachtwoord, lblCheckPassword);
    }

    //verificatie werd de pagina ervoor al gebeurd, dus hier moet enkel nog gecontroleerd worden op de twee wachtwoord-velden
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
                //indien alles ok, mag de wijziging gebeuren
                //@author Bart Maes
                LoginMethods.createPassword(gebruikerDAO, user, sWw);
                JOptionPane.showMessageDialog(null, "Uw wachtwoord is succesvol gewijzigd","Wachtwoord gewijzigd", JOptionPane.INFORMATION_MESSAGE);
                LoginMethods.loadScreen(anchorPane, getClass(), "view/Inloggen.fxml");
            }
        }
    }

    //ga terug naar home screen
    public void click_home(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/Inloggen.fxml");
    }




}
