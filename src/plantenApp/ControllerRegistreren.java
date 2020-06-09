package plantenApp;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;
import plantenApp.java.model.LoginMethods;

import javax.swing.*;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;

public class ControllerRegistreren {
    private GebruikerDAO gebruikerDAO;
    private Connection dbConnection;
    private Gebruiker user;

    public Button btnRegistreren;
    public Button btnAnnuleren;
    public TextField txtEmail;
    public PasswordField pfWachtwoord;
    public PasswordField pfWachtwoordHerhalen;
    public Label lblWachtwoordValidatie;
    public Label lblValidateEmail;
    public AnchorPane anchorPane;

    /**
     * @author Bart Maes
     * bij opstarten connectie en gebruikerDao aanroepen
     */
    public void initialize() throws SQLException {
        dbConnection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(dbConnection);

        txtEmail.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!LoginMethods.isValid(txtEmail.getText())) {
                    lblValidateEmail.setText("Geen geldig emailadres");
                    txtEmail.setStyle("-fx-border-color: red;");
                    lblValidateEmail.setTextFill(Color.RED);
                } else {
                    lblValidateEmail.setText("");
                    txtEmail.setStyle("-fx-border-color: none;");
                    //controleer of gebruiker in systeem zit
                    try {
                        user = gebruikerDAO.getByEmail(txtEmail.getText());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    if (user == null) {
                        LoginMethods.OptionDialiog("Het opgegeven emailadres is niet gekend in ons systeem. Wenst u een aanvraag te doen om toegang te krijgen tot de applicatie?",
                                "Emailadres niet gekend", anchorPane, getClass(), "view/AanvraagToegang.fxml", "view/Inloggen.fxml");
                    } else {
                        if(user.isGeregistreerd() == 1) {
                            JOptionPane.showMessageDialog(null, "U bent reeds geregistreerd. U kan onmiddellijk inloggen.", "Reeds geregistreerd", JOptionPane.INFORMATION_MESSAGE);
                            LoginMethods.loadScreen(anchorPane, getClass(), "view/Inloggen.fxml");
                        }
                        //hier nog checks op basis van aanvraag goedkeuring
                    }
                }
            }
        });

        //enkel wachtwoord controleren, controle of beide gelijk zijn, gebeurt bij versturen
        LoginMethods.checkPassword(pfWachtwoord, lblWachtwoordValidatie);
    }

    /**
     * @author Bart Maes, Matthias Vancoillie
     * registreren van een gebruiker
     */
    public void clicked_Registreren(MouseEvent mouseEvent) throws SQLException, NoSuchAlgorithmException {
        String sEmail = txtEmail.getText();
        String sWw = pfWachtwoord.getText();
        String sWw_herhaling = pfWachtwoordHerhalen.getText();

        //eerst controleren of de checks bij initialize OK zijn
        if (!lblValidateEmail.getText().trim().isEmpty() || !lblWachtwoordValidatie.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Gelieve eerst bovenstaande foutmeldingen in het rood te bekijken", "Ongeldige ingave", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //nogmaals check of alles ingevuld is (bv. als ze herhaal wachtwoord niet ingevuld hebben)
            if (sEmail.trim().isEmpty() || sWw.trim().isEmpty() || sWw_herhaling.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Gelieve alle velden in te vullen", "Ongeldige ingave", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //hier de controle of beide wachtwoorden gelijk zijn
                if (!sWw.equals(sWw_herhaling)) {
                    JOptionPane.showMessageDialog(null, "Beide wachtwoorden moeten gelijk zijn aan elkaar", "Ongeldige invage", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    //indien alles ok, mag de registratie gebeuren
                    //@author Bart Maes
                    byte[] salt = getSalt();
                    byte[] hashPassword = LoginMethods.HashFromPassword(sWw, salt);
                    //opslaan van hash en salt
                    gebruikerDAO.setWachtWoordHash(user.getGebruiker_id(), hashPassword, salt);

                    JOptionPane.showMessageDialog(null, "U bent succesvol geregistreerd","Registratie succesvol!", JOptionPane.INFORMATION_MESSAGE);
                    LoginMethods.loadScreen(anchorPane, getClass(), "view/Inloggen.fxml");
                }
            }
        }
    }

    /**
     * @author Bart Maes
     * Annuleren van registratie
     */
    // hiermee worden ze terug gestuurd naar het inlogscherm
    public void clicked_Annuleren(MouseEvent mouseEvent) {
        LoginMethods.OptionDialiog("Bent u zeker dat u de registratie wilt annuleren?",
                "Annuleren", anchorPane, getClass(), "view/Inloggen.fxml", "view/Registreren.fxml");
    }

    //methodes

    /**
     * @author Jasper, Bart Maes
     * random salt genereren
     */
    private static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
