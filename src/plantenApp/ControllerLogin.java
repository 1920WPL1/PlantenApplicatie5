package plantenApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;
import plantenApp.java.model.LoginMethods;
import plantenApp.java.utils.JavaMailUtil;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class ControllerLogin {
    // Scherm: Inloggen
    private Connection dbConnection;
    private GebruikerDAO gebruikerDAO;
    public TextField txtEmail;
    public TextField txtWachtwoord;
    public Label lblValidateEmail;
    public AnchorPane anchorPane;
    private Gebruiker user;


    /**
     * Author Bart Maes
     * bij opstarten connectie en gebruikerDao aanroepen
     */
    public void initialize() throws SQLException {
        dbConnection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(dbConnection);
        //valideren of het een geldig emailadres is
        LoginMethods.checkEmail(txtEmail, lblValidateEmail);
    }

    /**
     * Author Bart Maes
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
                    if (!CheckPasswordCorrect(sWachtwoord)) {
                        JOptionPane.showMessageDialog(null, "Het opgegeven wachtwoord klopt niet.",
                                "Ongeldig wachtwoord", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        //indien wachtwoord klopt, gaan ze naar hoofdscherm
                        FXMLLoader loader = LoginMethods.getLoader(getClass(), "view/HoofdScherm.fxml");
                        Parent root = LoginMethods.getRoot(loader);
                        actionsOnLoadHoofdscherm(loader);
                        LoginMethods.getScreen(anchorPane, root);
                    }
                } else {
                    //indien niet geregistreerd, gepaste melding geven
                    LoginMethods.OptionDialog("U bent nog niet geregistreerd. Wenst u zich te registreren?",
                            "Nog niet geregistreerd", anchorPane, getClass(), "view/Registreren.fxml", "view/Inloggen.fxml");
                }
            }
        }
    }

    public void Clicked_Registreer(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/Registreren.fxml");
    }

    public void click_WwVergeten(MouseEvent mouseEvent) throws Exception {
        //LoginMethods.loadScreen(anchorPane, getClass(), "view/WachtwoordVergeten.fxml");
        JavaMailUtil.sendMail("bartms@hotmail.com");
    }

    // methodes

    /**
     * @param wachtwoord Ingevoerd wachtwoord vergelijken met het opgeslagen wachtwoord uit de database
     * @return true or false
     * @author Bart Maes
     */
    private boolean CheckPasswordCorrect(String wachtwoord) throws NoSuchAlgorithmException {
        //ophalen salt uit database
        byte[] saltDB = user.getSalt();

        //ingegeven wachtwoord hashen met dezelfde salt (als bij registratie)
        byte[] insertedPassword = LoginMethods.HashFromPassword(wachtwoord, saltDB);

        //opgeslagen wachtwoord uit database halen
        byte[] wwDB = user.getWachtwoord_hash();

        //ingegeven wachtwoord vergelijken met opgeslagen wachtwoord uit database
        return Arrays.equals(insertedPassword, wwDB);
    }


    //acties die moeten gebeuren bij het laden van het hoofdscherm
    public void actionsOnLoadHoofdscherm(FXMLLoader loader) {
        ControllerHoofdscherm controller = loader.getController();
        LoginMethods.userLoggedIn = user; // ingelogde gebruiker opslaan voor overige schermen
        controller.setButtons();
    }
}

