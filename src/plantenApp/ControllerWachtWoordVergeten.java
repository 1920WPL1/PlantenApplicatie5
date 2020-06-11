package plantenApp;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

/**
 * @Author Bart Maes
 * controller voor wachtwoord vergeten
 */

public class ControllerWachtWoordVergeten {
    public TextField txtEmail;
    public Button btnVerstuurEmail;
    public Button btnVerifyCode;
    public Label lblEmail;
    public Label lblVerifyCode;
    public TextField txtVerifyCode;
    public Label lblValideerEmail;
    public AnchorPane anchorPane;

    private GebruikerDAO gebruikerDAO;
    private Connection dbConnection;
    private Gebruiker user;

    private int randomCode;

    public void initialize() throws SQLException {
        //connecties maken
        dbConnection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(dbConnection);

        //verify controls disablen bij opstart
        setVerifyControls(false);

        //realtime check op e-mailadres
        LoginMethods.checkEmail(txtEmail, lblValideerEmail);
    }

    //bij het bevestigen van e-mailadres
    public void clicked_MailVersturen(ActionEvent actionEvent) throws Exception {
        //de gebruiker zoeken adhv ingegeven e-mailadres
        user = gebruikerDAO.getByEmail(txtEmail.getText());

        //controle of de realtime check op e-mail nog een foutmelding geeft of niet
        if (!lblValideerEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Gelieve eerst een geldig e-mailadres in te geven", "Ongeldige e-mailadres", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //als gebruiker niet bestaat of nog niet geregistreerd is, mag hij/zij niet op "wachtwoord vergeten" klikken, want er bestaat dan nog geen wachtwoord --> eerst registreren/aanvraag
            if (user == null || user.isGeregistreerd() != 1) {
                JOptionPane.showMessageDialog(null, "Het opgegeven emailadres is niet geregistreed en kan geen wachtwoord wijzigen. Gelieve eerst te registreren.", "Emailadres niet geregistreerd", JOptionPane.INFORMATION_MESSAGE);
                LoginMethods.loadScreen(anchorPane, getClass(), "view/Registreren.fxml");
            } else {
                //indien het gaat om een geregistreerde gebruiker, heeft deze het recht om via "wachtwoord vergeten" het wachtwoord te wijzigen
                //dan wordt een random code opgehaald
                getRandomVerificationCode();
                //de code wordt via mail verstuurd
                JavaMailUtil.sendMail(user.getEmail(), "Uw verificatiecode voor het wijzigen van uw wachtwoord", "Beste " + user.getVoornaam() + ", \r\n\nDit is uw persoonlijke verificatiecode: " + randomCode +
                        ". \r\n\nGelieve dit in te geven in de applicatie om uw wachtwoord te kunnen wijzigen.\r\n\nMet vriendelijke groeten, \r\n\nHet VIVES-plantenteam");
                //bevestiging dat er een mail is verstuurd, zodat de gebruiker zijn mails kan controleren
                JOptionPane.showMessageDialog(null, "Verificatiecode is naar uw e-mailadres verstuurd", "Verificatiecode verstuurd", JOptionPane.INFORMATION_MESSAGE);
                //verify controls zichtbaar maken
                setVerifyControls(true);
                //de bovenstaande controls disablen / hiden
                btnVerstuurEmail.setVisible(false);
                txtEmail.setDisable(true);
                lblEmail.setVisible(false);
            }
        }
    }

    //random nummer genereren voor een verificatiecode
    private void getRandomVerificationCode() {
        Random r = new Random();
        randomCode = r.nextInt(999999);
    }

    //in aparte methode om dubbele code te vermijden
    //alles voor het verifiëren van de code visible maken of hiden
    private void setVerifyControls(Boolean b) {
        lblVerifyCode.setVisible(b);
        txtVerifyCode.setVisible(b);
        btnVerifyCode.setVisible(b);
    }

    //adhv de doorgestuurde code, kan de gebruiker zichzelf verifiëren
    public void clicked_VerifyCode(ActionEvent actionEvent) {
        //controleren of de ingegeven code gelijk is aan de gegenereerde code
        if (Integer.parseInt(txtVerifyCode.getText()) == randomCode) {
            //pas als de codes gelijk zijn aan elkaar, wordt de gebruiker doorgestuurd naar de pagina om zijn/haar wachtwoord te wijzigen
            //voor het volgende scherm moeten we ook weten over welke user het gaat (aangezien daar e-mailadres niet nogmaals wordt gevraagd), dus slaan we dit hier alvast op
            ControllerWachtwoordWijzigen.user = user;
            LoginMethods.loadScreen(anchorPane, getClass(), "view/WachtwoordWijzigen.fxml");
        } else {
            //als de codes niet overeenkomen, dan wordt de gepaste melding weergegeven
            JOptionPane.showMessageDialog(null, "Ingegeven code klopt niet. Gelieve de code in uw mail opnieuw te controleren.", "Ongeldige code", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void click_home(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/Inloggen.fxml");
    }
}