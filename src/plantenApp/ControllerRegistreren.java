package plantenApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerRegistreren {
    public Button btnRegistrerenStudent;
    public Button btnAnnulerenRegistreren;
    public TextField txtVoornaamStudent;
    public TextField txtAchternaamStudent;
    public TextField txtVivesMail;

    public PasswordField pfWachtwoordStudent;
    public PasswordField pfStudentWachtwoordHerhalen;

    public Label lblGelijkeWW;
    public Label lblWachtwoordValidatie;
    public Label lblEmailBoodschap;

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
     * Author Matthias Vancoillie
     *
     * @param
     * @Return overgang en werking Registratie Student
     */

    // Valideren van een e-mail
    public boolean validateEmail(String sVivesEmail) {
        boolean status = false;

        //[a-zA-Z0-9_+&*-]+
        String email_pattern = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9_+&*-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher = pattern.matcher(sVivesEmail);

       // if-else aanmaken om te kijken als het ingevoerde veld het pattern opvolgt.
        if (matcher.matches()) {
            status = true;

            //return pattern.matcher(email_pattern).matches();
            lblEmailBoodschap.setText("Dit is een geldig e-mailadres.");
        } else {
            status = false;
            lblEmailBoodschap.setText("Dit is geen geldig e-mailadres");
        }
        return status;

    }


    // Valideren van een wachtwoord
    /*
    Minstens 10 karakters
    Minstens 1 hoofdletter
    Minstens 1 kleine letter
    Minstens 1 nummer
     */
    public boolean validateWachtwoord(String sWachtwoordStudent) {
        if (sWachtwoordStudent.length() > 9) {

            if (checkPass(sWachtwoordStudent)) {
                return true;
            } else {
                return false;
            }

        } else {
            lblWachtwoordValidatie.setText("Wachtwoord is te klein");
            return false;
        }
    }

    public boolean checkPass(String sWachtwoordStudent) {

        boolean hasNum = false;
        boolean hasCap = false;
        boolean hasLow = false;

        char c;

        for (int i = 0; i < sWachtwoordStudent.length(); i++) {
            c = sWachtwoordStudent.charAt(i);
            if (Character.isDigit(c)) {
                hasNum = true;
            } else if (Character.isUpperCase(c)) {
                hasCap = true;
            } else if (Character.isLowerCase(c)) {
                hasLow = true;
            }
            if (hasNum && hasCap && hasLow) {
                return true;
            }

        }
        return false;
    }


    public void clicked_RegistrerenStudent(MouseEvent mouseEvent) throws SQLException {
        // Scherm voor het registreren van een student
            // knop om de aanvraag op registratie in te dienen voor de student.
            // knop om annulatie -> terug naar login
            // De ingevoerde velden binnen het scherm ophalen.
            String sVivesMail = txtVivesMail.getText();
            String sWachtwoordStudent = pfWachtwoordStudent.getText();
            String sWachtwoordHerhalenStudent = pfStudentWachtwoordHerhalen.getText();

            // if else aanmaken voor e-mail - isEmpty controle
        if (sVivesMail.isEmpty()){
            JOptionPane.showMessageDialog(null,"Gelieve een e-mail adres in te vullen");
        } else {

        }
            // if-else aanmaken voor wachtwoord
        if (sWachtwoordStudent.isEmpty() && sWachtwoordHerhalenStudent.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Gelieve iets in te vullen als wachtwoord", "Ongeldige ingave", JOptionPane.INFORMATION_MESSAGE);
        } else {
            validateWachtwoord(sWachtwoordStudent);
        }

        // if-else als wachtwoorden gelijk zijn
        if (sWachtwoordStudent != sWachtwoordHerhalenStudent)
        {
            lblGelijkeWW.setText("De wachtwoorden zijn gelijk");

        } else {
            JOptionPane.showMessageDialog(null, "Wachtwoorden zijn niet gelijk", "Ongeldige invage", JOptionPane.INFORMATION_MESSAGE);
        }

        // enable en disable button "registreren"
       btnRegistrerenStudent.setDisable(true);

        }

    public void EnableDisable_Button(String sVivesMail)
    {
        boolean isDisabled = (sVivesMail.isEmpty() || sVivesMail.trim().isEmpty()
                /* || (sWachtwoordStudent.isEmpty() || sWachtwoordStudent.trim().isEmpty() || (sWachtwoordHerhalenStudent.isEmpty() || sWachtwoordHerhalenStudent.trim().isEmpty() */);
        btnRegistrerenStudent.setDisable(isDisabled);
    }


    public void clicked_AnnulerenRegistreren(MouseEvent mouseEvent) {
        // wanneer de gebruiker de registratie annuleert wilt dit zeggen dat hij / zij al een werkend account in bezig heeft.
        // hiermee worden ze dan terug gestuurd naar het inlogscherm
         LoginMethods.loadScreen(mouseEvent, getClass(), "view/Inloggen.fxml");
    }
    /**
     * @author Bart Maes
     * checks bij registratie
     */
    public void clicked_Registreren(MouseEvent mouseEvent) throws SQLException, NoSuchAlgorithmException {
        String sEmail = txtVivesMail.getText();
        String sVoornaam = txtVoornaamStudent.getText();
        String sAchternaam = txtAchternaamStudent.getText();
        String sWw = pfWachtwoordStudent.getText();
        String sWw_herhaling = pfStudentWachtwoordHerhalen.getText();

        //hieronder moeten er nog extra checks gebeuren

        //controleer of gebruiker in systeem zit
        user = gebruikerDAO.getByEmail(txtVivesMail.getText());

        if (user == null) {
            JOptionPane.showConfirmDialog(null,
                    "Het opgegeven emailadres is niet gekend in ons systeem. Wenst u een aanvraag te doen om toegang te krijgen tot de applicatie?",
                    "Emailadres niet gekend", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } else {

            byte[] salt = getSalt();
            byte[] hashPassword = LoginMethods.HashFromPassword(sWw, salt);
            //opslaan van hash en salt
            gebruikerDAO.setWachtWoordHash(user.getId(), hashPassword, salt);

            JOptionPane.showMessageDialog(null, "U bent succesvol geregistreerd",
                    "Registratie succesvol!", JOptionPane.INFORMATION_MESSAGE);
            LoginMethods.loadScreen(mouseEvent, getClass(), "view/Inloggen.fxml");
        }

    }
    private static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public void click_ValideerMail(MouseEvent mouseEvent) {
        String emailAdres = txtVivesMail.getText();
        if (emailAdres.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Gelieve een geldig e-mail adres in te vullen");
        } else {
            // validatie voor e-mailadres
            validateEmail(emailAdres);

        }

    }



}
