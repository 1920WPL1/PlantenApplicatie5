package plantenApp;

import javafx.scene.control.Button;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;
import plantenApp.java.model.LoginMethods;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControllerRegistreren {
    public Button btnRegistreren;
    public Button btnAnnuleren;
    public TextField txtVoornaam;
    public TextField txtAchternaam;
    public TextField txtEmail;
    public PasswordField pfWachtwoord;
    public PasswordField pfWachtwoordHerhalen;

    private Connection dbConnection;
    private GebruikerDAO gebruikerDAO;
    private Gebruiker user;

    /**
     * @author Bart Maes
     * bij opstarten connectie en gebruikerDao aanroepen
     */
    public void initialize() throws SQLException {
        dbConnection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(dbConnection);
    }

    /**
     * @author Bart Maes
     * checks bij registratie
     */
    public void clicked_Registreren(MouseEvent mouseEvent) throws SQLException, NoSuchAlgorithmException {
        String sEmail = txtEmail.getText();
        String sVoornaam = txtVoornaam.getText();
        String sAchternaam = txtAchternaam.getText();
        String sWw = pfWachtwoord.getText();
        String sWw_herhaling = pfWachtwoordHerhalen.getText();

        //hieronder moeten er nog extra checks gebeuren

        //controleer of gebruiker in systeem zit
        user = gebruikerDAO.getByEmail(txtEmail.getText());

        if (user == null) {
            JOptionPane.showConfirmDialog(null,
                    "Het opgegeven emailadres is niet gekend in ons systeem. Wenst u een aanvraag te doen om toegang te krijgen tot de applicatie?",
                    "Emailadres niet gekend", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } else {

            byte[] salt = getSalt();
            byte[] hashPassword = LoginMethods.HashFromPassword(sWw, salt);
            //opslaan van hash en salt
            gebruikerDAO.setWachtWoordHash(user.getID(), hashPassword, salt);

            JOptionPane.showMessageDialog(null, "U bent succesvol geregistreerd",
                    "Registratie succesvol!", JOptionPane.INFORMATION_MESSAGE);
            LoginMethods.loadScreen(mouseEvent, getClass(), "view/Inloggen.fxml");
        }

    }

    public void clicked_Annuleren(MouseEvent mouseEvent) {
    }

    /**
     * @param email
     * @author Bart Maes
     * @Return true or false
     * validatie emailadres
     */
    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    /**
     * @return random salt
     * salt in aparte methode bepalen
     * @author Bart Maes
     */

    private static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
