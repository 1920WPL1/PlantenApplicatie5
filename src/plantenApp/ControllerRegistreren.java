package plantenApp;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;
import plantenApp.java.model.LoginMethods;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class ControllerRegistreren {
    private GebruikerDAO gebruikerDAO;
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
     * bij laden connectie en gebruikerDao aanroepen
     */
    public void initialize() throws SQLException {
        Connection dbConnection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(dbConnection);
        user = LoginMethods.userLoggedIn; // ingelogde gebruiker ophalen

        //alle checks op e-mail in 1 listener
        txtEmail.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //als de focus op het veld weg is, wordt de controle uitgevoerd
                //als het geen geldig e-mailadres is, wordt dit in de label eronder weergegeven
                if (!LoginMethods.isValid(txtEmail.getText())) {
                    lblValidateEmail.setText("Geen geldig emailadres");
                    txtEmail.setStyle("-fx-border-color: red;");
                    lblValidateEmail.setTextFill(Color.RED);
                } else {
                    //als het e-mailadres wel geldig is, clear bovenstaande settings
                    lblValidateEmail.setText("");
                    txtEmail.setStyle("-fx-border-color: none;");
                    //controleer of gebruiker in systeem zit
                    try {
                        user = gebruikerDAO.getByEmail(txtEmail.getText());
                        //hier ook al realtime controleren op een aantal basis checks
                        //indien gebruiker niet bestaat, direct doorverwijzen naar aanvraagformulier (geen nut van nog wachtwoord in te vullen)
                        if (user == null) {
                            LoginMethods.OptionDialog("Het opgegeven emailadres is niet gekend in ons systeem. Wenst u een aanvraag te doen om toegang te krijgen tot de applicatie?",
                                    "Emailadres niet gekend", anchorPane, getClass(), "view/AanvraagToegang.fxml", "view/Inloggen.fxml");
                        } else {
                            //indien gebruiker wel al bestaat, eerst controleren of deze nog niet geregistreerd is (indien geregistreerd, is dit scherm niet nuttig)
                            if (user.isGeregistreerd() == 1) {
                                JOptionPane.showMessageDialog(null, "U bent reeds geregistreerd. U kan onmiddellijk inloggen.", "Reeds geregistreerd", JOptionPane.INFORMATION_MESSAGE);
                                LoginMethods.loadScreen(anchorPane, getClass(), "view/Inloggen.fxml");
                            }
                            //indien gebruiker al bestaat en al een aanvraag heeft gedaan
                            else {
                                //controleren of er al een aanvraag gebeurd is. Zo ja, terugsturen naar inlogscherm.
                                if (user.isAanvraag_status() == 1) {
                                    JOptionPane.showMessageDialog(null, "U heeft reeds een aanvraag ingediend, maar deze is nog in behandeling.", "Aanvraag in behandeling.", JOptionPane.INFORMATION_MESSAGE);
                                    LoginMethods.loadScreen(anchorPane, getClass(), "view/Inloggen.fxml");
                                }
                            }
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Geen verbinding met de server \r\n Contacteer uw systeembeheer indien dit probleem blijft aanhouden","Geen verbinding", JOptionPane.ERROR_MESSAGE);
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
    public void clicked_Registreren(ActionEvent actionEvent) throws Exception {
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
                    try{
                        LoginMethods.createPassword(gebruikerDAO, user, sWw);
                        JOptionPane.showMessageDialog(null, "U bent succesvol geregistreerd", "Registratie succesvol!", JOptionPane.INFORMATION_MESSAGE);
                        LoginMethods.loadScreen(anchorPane, getClass(), "view/Inloggen.fxml");
                    } catch (SQLException e){
                        JOptionPane.showMessageDialog(null, "Geen verbinding met de server \r\n Contacteer uw systeembeheer indien dit probleem blijft aanhouden","Geen verbinding", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    /**
     * @author Bart Maes
     * Annuleren van registratie
     */
    // hiermee worden ze terug gestuurd naar het inlogscherm
    public void clicked_Annuleren(ActionEvent actionEvent) {
        LoginMethods.OptionDialog("Bent u zeker dat u de registratie wilt annuleren?",
                "Annuleren", anchorPane, getClass(), "view/Inloggen.fxml", "view/Registreren.fxml");
    }
}
