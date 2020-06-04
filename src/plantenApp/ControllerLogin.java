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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ControllerLogin {
    // Scherm: Inloggen

    private Connection dbConnection;
    public TextField txtEmail;
    public TextField txtWachtwoord;
    public Button btnZoekScherm;
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
                int dialogButton = JOptionPane.showConfirmDialog(null,
                        "Het opgegeven emailadres Werd niet herkend in ons systeem. Wenst u een aanvraag te doen om toegang te krijgen tot de applicatie?",
                        "Emailadres niet gekend", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (dialogButton == JOptionPane.YES_OPTION) {
                    loadScreen(mouseEvent, "view/AanvraagToegang.fxml");
                }

            } else {
                if (false) { //controleren of gebruiker reeds geregistreerd is  --> hiervoor nog extra veld "geregistreerd" nodig in tabel gebruiker

                } else {   //indien geregistreerd, controle of opgegeven wachtwoord klopt
                    // @author Jasper
                    // test: aanmaken hash van ingevoerd wachtwoord
                    byte[] hashFromLogin = HashFromPassword(sWachtwoord);
                    // test: opslaan van hash
                    gebruikerDAO.setWachtWoordHash(user.getGebruiker_id(), hashFromLogin);
                }

                //indien wachtwoord klopt, controle op rol

                loadScreen(mouseEvent, "view/HoofdScherm.fxml");
                String rol = user.getRol();

                if (rol.equals("admin")) {
                    btnZoekScherm.setVisible(false);

                }


                //indien nee, geef melding dat ze zich eerst moeten registreren


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

    public void click_WwVergeten(MouseEvent mouseEvent) {

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

    // methodes

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
     * @param wachtwoord Wachtwoord om te hashen
     * @apiNote Versleuteling van het wachtwoord tot een hash met hash algoritme SHA-512 (= sterkste beschikbaar in Java)
     * @return hash van het wachtwoord : array van 80 bytes
     * @throws NoSuchAlgorithmException
     * @author Jasper
     * @apiNote Versleuteling van het wachtwoord tot een hash met hash algoritme SHA-512 (= sterkste beschikbaar in Java)
     */
    private byte[] HashFromPassword(String wachtwoord) throws NoSuchAlgorithmException {
        // Salt = 16 willekeurige bytes
        // Bij elke nieuw wachtwoord wordt een salt aangemaakt
        // Hash = salt + resultaat hashfunctie(salt + password)

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // MessageDigest = hash functie
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt); // salt toevoegen aan functie
        byte[] hashFunctionResult = md.digest(wachtwoord.getBytes(StandardCharsets.UTF_8)); // wachtwoord toevoegen aan functie

        // resultaat hashfunctie is altijd 64 bytes bij algoritme SHA-512
        // 64 bytes hashfunctie + 16 bytes salt = 80 bytes hash
        byte[] hash = new byte[80];
        for (int i = 0; i < 15; i++) {
            hash[i] = salt[i];
        }
        for (int i = 16; i < 79; i++) {
            hash[i] = hashFunctionResult[i];
        }

        return hash;
    }


    /**
     * @param wachtwoord Ingevoerd wachtwoord
     * @param salt       Eerste 16 bytes van hash in database
     * @return
     * @author Jasper
     */
    private boolean CheckPasswordCorrect(String wachtwoord, byte[] salt) {
        return true; // TODO: 3-6-2020  Ophalen salt uit hash uit database, controleren of salt + hashfunctie van wachtwoord+salt gelijk is aan de hash in database
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

