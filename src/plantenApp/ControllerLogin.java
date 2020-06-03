
package plantenApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ControllerLogin {
    // Scherm: Inloggen

    private Connection dbConnection;
    public TextField txtEmail;
    public TextField txtWachtwoord;
    private GebruikerDAO gebruikerDAO;

    /**
     * Author Bart Maes
     *
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

        if(sEmail.isEmpty() && sWachtwoord.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Gelieve alle velden in te vullen",
                    "Ongeldige ingave", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //controleer of gebruiker in systeem zit
            Gebruiker user = gebruikerDAO.getByEmail(txtEmail.getText());

            //user bestaat niet in database
            if (user == null) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                JOptionPane.showConfirmDialog (null, "Het opgegeven emailadres is geen VIVES-account. Wenst u een aanvraag te doen om toegang te krijgen tot de applicatie?","Emailadres niet gekend",dialogButton, JOptionPane.INFORMATION_MESSAGE);
            } else {
                if(false) { //controleren of gebruiker reeds geregistreerd is  --> hiervoor nog extra veld "geregistreerd" nodig in tabel gebruiker

                }
                else{   //indien geregistreerd, controle of opgegeven wachtwoord klopt
                    // @author Jasper
                    // test: aanmaken hash van ingevoerd wachtwoord
                    byte[] hashFromLogin = HashFromPassword(sWachtwoord);
                    // test: opslaan van hash
                    gebruikerDAO.setWachtWoordHash(user.getGebruiker_id(), hashFromLogin);
                }

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

    /**@author Jasper
     * @param wachtwoord Wachtwoord om te hashen
     * @apiNote Versleuteling van het wachtwoord tot een hash met hash algoritme SHA-512 (= sterkste beschikbaar in Java)
     * @return hash van het wachtwoord : array van 80 bytes
     * @throws NoSuchAlgorithmException
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
        byte[] hashFunctionResult =  md.digest(wachtwoord.getBytes(StandardCharsets.UTF_8)); // wachtwoord toevoegen aan functie

        // resultaat hashfunctie is altijd 64 bytes bij algoritme SHA-512
        // 64 bytes hashfunctie + 16 bytes salt = 80 bytes hash
        byte[] hash = new byte [80];
        for(int i = 0 ; i < 15 ; i++){
            hash[i] = salt[i];
        }
        for(int i = 16 ; i < 79 ; i++){
            hash[i] = hashFunctionResult[i];
        }

        return hash;
    }

    /**@author Jasper
     * @param wachtwoord Ingevoerd wachtwoord
     * @param salt Eerste 16 bytes van hash in database
     * @return
     */
    private boolean CheckPasswordCorrect(String wachtwoord, byte[] salt){
        return true; // TODO: 3-6-2020  Ophalen salt uit hash uit database, controleren of salt + hashfunctie van wachtwoord+salt gelijk is aan de hash in database
    }


    /**
     * Author Bart Maes
     *
     * @param email
     * @Return true or false
     * validatie emailadres
     */

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
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
}

