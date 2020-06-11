package plantenApp;

import javafx.event.ActionEvent;
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

public class ControllerLogin {
    // Scherm: Inloggen
    private Connection dbConnection;
    private GebruikerDAO gebruikerDAO;
    public TextField txtEmail;
    public TextField txtWachtwoord;
    public Label lblValidateEmail;
    public Label lblWachtwoordVergeten;
    public AnchorPane anchorPane;
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
     *
     * @param actionEvent
     * @Return overgang van het login naar homescreen
     * controleren op email adres
     */
    public void clicked_Login(ActionEvent actionEvent) throws Exception {
        //ingegeven email en wachtwoord in variabelen steken
        String sEmail = txtEmail.getText();
        String sWachtwoord = txtWachtwoord.getText();

        //controle op lege velden
        if ((sEmail.trim().isEmpty() || sWachtwoord.trim().isEmpty())) {
            JOptionPane.showMessageDialog(null, "Gelieve alle velden in te vullen",
                    "Ongeldige ingave", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                //controleer of gebruiker in systeem zit
                user = gebruikerDAO.getByEmail(txtEmail.getText());

                //voor als iemand zich probeert in te loggen, ook al is hij/zij nog niet geregistreerd
                //user bestaat niet in database
                if (user == null) {
                    LoginMethods.OptionDialog("Het opgegeven emailadres is niet gekend in ons systeem. Wenst u een aanvraag te doen om toegang te krijgen tot de applicatie?",
                            "Emailadres niet gekend", anchorPane, getClass(), "view/AanvraagToegang.fxml", "view/Inloggen.fxml");
                } else {
                    //eerst controleren of de gebruiker al geregistreerd is (ze moeten eerst wachtwoord aanmaken)
                    if (user.isGeregistreerd() == 1) {
                        //indien geregistreerd, dan controleren of het wachtwoord klopt
                        if (!LoginMethods.CheckPasswordCorrect(user, sWachtwoord)) {
                            JOptionPane.showMessageDialog(null, "Het opgegeven wachtwoord klopt niet.",
                                    "Ongeldig wachtwoord", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            //indien wachtwoord klopt, user gaan opslaan in LoginMethods en daarna hoofdscherm laden
                            LoginMethods.userLoggedIn = user;
                            LoginMethods.loadScreen(anchorPane, getClass(), "view/HoofdScherm.fxml");
                        }
                    } else {
                        //indien niet geregistreerd, gepaste melding geven
                        LoginMethods.OptionDialog("U bent nog niet geregistreerd. Wenst u zich te registreren?",
                                "Nog niet geregistreerd", anchorPane, getClass(), "view/Registreren.fxml", "view/Inloggen.fxml");
                    }
                }
            } catch (SQLException e)
            {
                JOptionPane.showMessageDialog(null, "Geen verbinding met de server \r\n Contacteer uw systeembeheer indien dit probleem blijft aanhouden","Geen verbinding", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //ga naar registreerscherm
    public void Clicked_Registreer(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/Registreren.fxml");
    }

    //ga naar scherm voor wachtwoord vergeten
    public void click_WwVergeten(MouseEvent mouseEvent) throws Exception {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/WachtwoordVergeten.fxml");
    }
}

