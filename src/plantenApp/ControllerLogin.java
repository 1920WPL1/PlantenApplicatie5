package plantenApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class ControllerLogin {
    // Scherm: Inloggen
    private Connection dbConnection;
    private GebruikerDAO gebruikerDAO;
    public TextField txtEmail;
    public TextField txtWachtwoord;
    private Gebruiker user;

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
            user = gebruikerDAO.getByEmail(txtEmail.getText());

            //user bestaat niet in database
            if (user == null) {
                int dialogButton = JOptionPane.showConfirmDialog(null,
                        "Het opgegeven emailadres is niet gekend in ons systeem. Wenst u een aanvraag te doen om toegang te krijgen tot de applicatie?",
                        "Emailadres niet gekend", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (dialogButton == JOptionPane.YES_OPTION) {
                    LoginMethods.loadScreen(mouseEvent, getClass() ,"view/AanvraagToegang.fxml");
                }

            } else {
                if (user.isGeregistreerd() == 1) {
                    if (!CheckPasswordCorrect(sWachtwoord)) {
                        JOptionPane.showMessageDialog(null, "Het opgegeven wachtwoord klopt niet.",
                                "Ongeldig wachtwoord", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        //indien wachtwoord klopt, gaan ze naar hoofdscherm
                        FXMLLoader loader = LoginMethods.getLoader(getClass(), "view/HoofdScherm.fxml");
                        Parent root = LoginMethods.getRoot(loader);
                        actionsOnLoadHoofdscherm(loader);
                        LoginMethods.getScreen(mouseEvent, root);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "U bent nog niet geregistreerd",
                            "Gelieve eerst te registreren.", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        }
    }


    public void Clicked_Registreer(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(mouseEvent, getClass() ,"view/Registreren.fxml");
    }

    public void click_WwVergeten(MouseEvent mouseEvent) {
        ;
    }

    // methodes

    /**
     * @param wachtwoord Ingevoerd wachtwoord
     * @return
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



    public void actionsOnLoadHoofdscherm(FXMLLoader loader) {
        ControllerHoofdscherm controller = loader.getController();
        controller.setUser(user);
        controller.setButtons();
    }

}

