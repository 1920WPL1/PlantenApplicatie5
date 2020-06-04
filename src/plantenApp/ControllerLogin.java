package plantenApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
    public Button btnZoekScherm;
    private GebruikerDAO gebruikerDAO;


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
                int dialogButton = JOptionPane.showConfirmDialog(null,
                        "Het opgegeven emailadres is geen VIVES-account. Wenst u een aanvraag te doen om toegang te krijgen tot de applicatie?",
                        "Emailadres niet gekend", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (dialogButton == JOptionPane.YES_OPTION) {
                    loadScreen(mouseEvent, "view/AanvraagToegang.fxml");
                }

            } else {
                //controleren of gebruiker reeds geregistreerd is  --> hiervoor nog extra veld "geregistreerd" nodig in tabel gebruiker

                //indien ja, controle of opgegeven wachtwoord klopt

                //indien wachtwoord klopt, controle op rol

                loadScreen(mouseEvent, "view/HoofdScherm.fxml");
                String rol = user.getRol();

                if(rol.equals("admin")) {
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
     * Author Bart Maes
     *
     * @param mouseEvent
     * @Return overgang van het homescreen naar zoekscherm
     */
    // hoofdscherm
    public void click_NaarZoekscherm(MouseEvent mouseEvent) {
        loadScreen(mouseEvent, "view/Zoekscherm.fxml");
    }

    public void click_ProfielBeheren(MouseEvent mouseEvent) {
    }

    public void click_RegistratiesBeheren(MouseEvent mouseEvent) {
    }

    public void click_GebruikersBeheren(MouseEvent mouseEvent) {
    }

    public void clicked_ToevoegenPlant(MouseEvent mouseEvent) {
    }

    public void click_PlantZoekWijzig(MouseEvent mouseEvent) {
    }

    public void click_PlantAanvraagBeheren(MouseEvent mouseEvent) {
    }

    public void click_VerzendAanvraag(MouseEvent mouseEvent) {
    }

    public void click_AnnuleerAanvraag(MouseEvent mouseEvent) {
        int dialogButton = JOptionPane.showConfirmDialog(null,
                "Bent u zeker dat u de aanvraag wilt annuleren?",
                "Annuleren", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (dialogButton == JOptionPane.YES_OPTION) {
            loadScreen(mouseEvent, "view/Inloggen.fxml");
        }
    }
}

