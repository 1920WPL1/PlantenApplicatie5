
package plantenApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControllerLogin {
    // Scherm: Inloggen

    private Connection dbConnection;
    public TextField txtEmail;
    public TextField txtWachtwoord;
    private GebruikerDAO gebruikerDAO;

    // Scherm: Registreren Student
    public TextField txtVivesMail;
    public TextField txtVoornaamStudent;
    public TextField txtAchternaamStudent;

    public Label lblGelijkeWW;
    public Label lblWachtwoordCorrectie;


    public PasswordField pfWachtwoordStudent;
    public PasswordField pfStudentWachtwoordHerhalen;


    /**
     * Author Bart Maes
     * <p>
     * bij opstarten connectie en gebruikerDao aanroepen
     */
    public void initialize() throws SQLException {
        dbConnection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(dbConnection);

    }

    /**
     * Author Bart Maes
     *
     * @param mouseEvent
     * @Return overgang van het login naar homescreen
     * controleren op email adres
     */
    public void clicked_Login(MouseEvent mouseEvent) throws Exception {
        String sEmail = txtEmail.getText();
        String sWachtwoord = txtWachtwoord.getText();

        if (sEmail.isEmpty() && sWachtwoord.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Gelieve alle velden in te vullen",
                    "Ongeldige ingave", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //controleer of gebruiker in systeem zit
            Gebruiker user = gebruikerDAO.getByEmail(txtEmail.getText());

            //user bestaat niet in database
            if (user == null) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                JOptionPane.showConfirmDialog(null, "Het opgegeven emailadres is geen VIVES-account. Wenst u een aanvraag te doen om toegang te krijgen tot de applicatie?", "Emailadres niet gekend", dialogButton, JOptionPane.INFORMATION_MESSAGE);
            } else {
                //controleren of gebruiker reeds geregistreerd is  --> hiervoor nog extra veld "geregistreerd" nodig in tabel gebruiker

                //indien ja, controle of opgegeven wachtwoord klopt


                //indien nee, geef melding dat ze zich eerst moeten registreren

                loadScreen(mouseEvent, "view/Zoekscherm.fxml");
            }
        }

    }

    public void Clicked_Registreer(MouseEvent mouseEvent) {
        /*
            //validatie emailadres nodig bij registratie
            if (isValid(sEmail) == false) {
                JOptionPane.showMessageDialog(null, "Gelieve een geldig emailadres in te geven",
                        "Ongeldig emailadres", JOptionPane.INFORMATION_MESSAGE);
            } else {
            */
    }

    /**
     * Author Bart Maes
     *
     * @param email
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
     * Author Bart Maes
     *
     * @param event
     * @param screenName
     * @Return loading screen
     * laden van scherm in aparte methode
     */
    public void loadScreen(MouseEvent event, String screenName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(screenName));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Scherm: Inloggen
    public void clicked_Versturen(MouseEvent mouseEvent) {
        loadScreen(mouseEvent,"view/Zoekscherm.fxml");
    }

    // Scherm: Wachtwoord vergeten
    public void clicked_MailVersturen(MouseEvent mouseEvent) {
    }

    // Scherm: Wachtwoord vergeten
    public void click_WwVergeten(MouseEvent mouseEvent) {

    }

    // Scherm: Beheren Registraties
    public void clicked_GoedkeurenAanvraag(MouseEvent mouseEvent) {
    }

    // Scherm: Beheren Registraties
    public void clicked_VerwijderAanvraag(MouseEvent mouseEvent) {
    }

    /**
     * Author Bart Maes
     *
     * @param mouseEvent
     * @Return overgang van het beheer gebruiker naar zoekscherm
     */
    public void click_Terug(MouseEvent mouseEvent) {
        loadScreen(mouseEvent, "view/Zoekscherm.fxml");
    }

    public void Click_SaveGebruiker(MouseEvent mouseEvent) {

    }

    public void Click_VerwijderGebruiker(MouseEvent mouseEvent) {

    }

    public void Click_wijzigWachtwoord(MouseEvent mouseEvent) {

    }


    public void click_BeheerGebruikerProfiel(MouseEvent mouseEvent) {

    }

    /**
     * Author Matthias Vancoillie
     *
     * @param
     * @Return overgang en werking Registratie Student
     */

    // Valideren van een wachtwoord
    /*
    Minstens 10 karakters
    Minstens 1 hoofdletter
    Minstens 1 kleine letter
    Minstens 1 nummer
     */

    public boolean validateWachtwoord(String sWachtwoordStudent) {
        if (sWachtwoordStudent.length() > 9 )
        {
            if (checkPass(sWachtwoordStudent)) {
                return true;
            } else {
                return false;
            }

        } else {
            lblWachtwoordCorrectie.setText("Wachtwoord is te klein");
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

    // Scherm voor het registreren van een student
    public void clicked_RegistrerenStudent(MouseEvent mouseEvent) throws SQLException {
        // knop om de aanvraag op registratie in te dienen voor de student.

        // De ingevoerde velden binnen het scherm ophalen.
        String sVivesMail = txtVivesMail.getText();
        String sVoornaamStudent = txtVoornaamStudent.getText();
        String sAchternaamStudent = txtAchternaamStudent.getText();

        String sWachtwoordStudent = pfWachtwoordStudent.getText();
        String sWachtwoordHerhalenStudent = pfStudentWachtwoordHerhalen.getText();

        // if-else aanmaken als controle wanneer velden niet zijn ingevuld door de student.
        if (sVivesMail.isEmpty() && sVoornaamStudent.isEmpty() && sAchternaamStudent.isEmpty() && sWachtwoordStudent.isEmpty() && sWachtwoordHerhalenStudent.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Gelieve alle velden in te vullen voor je registratie", "Ongeldige ingave", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // controleren als de student al in de database aanwezig is.
            Gebruiker student = gebruikerDAO.getByEmail(txtVivesMail.getText());

            // Student is niet gekend in de database
            if (student == null) {
                int dialogButton = JOptionPane.showConfirmDialog(null, "Het opgegeven vives e-mail is nog niet gekend. Wenst u een aanvraag te doen?", "Vives e-mail niet gekend", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                // yes of no in een  if-else structuur gaan verwerken
                if (dialogButton == JOptionPane.YES_NO_OPTION) {
                    loadScreen(mouseEvent, "view/AanvraagToegang.fxml");
                } else {
                    loadScreen(mouseEvent, "view/Inloggen.fxml");
                }
            }

    }

}
    public void clicked_AnnulerenRegistreren(MouseEvent mouseEvent) {
        // wanneer de gebruiker de registratie annuleert wilt dit zeggen dat hij / zij al een werkend account in bezig heeft.
        // hiermee worden ze dan terug gestuurd naar het inlogscherm
        loadScreen(mouseEvent,"view/Inloggen.fxml");
    }


    // scherm: Aanvraag Toegang Registraties de gebruikers

    public void click_VerzendAanvraag(MouseEvent mouseEvent) {
    }

    public void click_AnnuleerAanvraag(MouseEvent mouseEvent) {
    }
}

