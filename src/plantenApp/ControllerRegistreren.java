package plantenApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;

import javax.swing.*;
import java.sql.SQLException;

public class ControllerRegistreren {
    private GebruikerDAO gebruikerDAO;

    public Button btnRegistrerenStudent;
    public Button btnAnnulerenRegistreren;
    public TextField txtVoornaamStudent;
    public TextField txtAchternaamStudent;
    public TextField txtVivesMail;

    public PasswordField pfWachtwoordStudent;
    public PasswordField pfStudentWachtwoordHerhalen;

    public Label lblGelijkeWW;
    public Label lblWachtwoordValidatie;

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
        if (sWachtwoordStudent.length() > 9) {
            if (checkPass(sWachtwoordStudent)) {
                return true;
            } else {
                return false;
            }

        } else {
            lblWachtwoordValidatie.setText("Wachtwoord is te klein");
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

    // valideren van vives e-mail



    public void clicked_RegistrerenStudent(MouseEvent mouseEvent) throws SQLException {
        // Scherm voor het registreren van een student
            // knop om de aanvraag op registratie in te dienen voor de student.

            // De ingevoerde velden binnen het scherm ophalen.
            String sVivesMail = txtVivesMail.getText();
            String sVoornaamStudent = txtVoornaamStudent.getText();
            String sAchternaamStudent = txtAchternaamStudent.getText();

            String sWachtwoordStudent = pfWachtwoordStudent.getText();
            String sWachtwoordHerhalenStudent = pfStudentWachtwoordHerhalen.getText();



            // if-else aanmaken als controle wanneer velden niet zijn ingevuld door de student.

            if (sVivesMail.isEmpty() && sVoornaamStudent.isEmpty() && sAchternaamStudent.isEmpty()) {
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

            // if-else aanmaken voor wachtwoord
        if (sWachtwoordStudent.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Gelieve iets in te vullen als wachtwoord", "Ongeldige ingave", JOptionPane.INFORMATION_MESSAGE);
        } else {
            validateWachtwoord(sWachtwoordStudent);
        }

        // if-else als wachtwoorden gelijk zijn
        if (sWachtwoordStudent != sWachtwoordHerhalenStudent)
        {
            lblGelijkeWW.setText("De wachtwoorden zijn gelijk");

        } else {
            JOptionPane.showMessageDialog(null, "Wachtwoorden zijn niet gelijk", "Ongeldige invage", JOptionPane.INFORMATION_MESSAGE);
            // als de wachtwoorden niet gelijk zijn dan worden de ingevoerde velden weer leeg.
            /*
            pfWachtwoordStudent.setText(null);
            pfStudentWachtwoordHerhalen.setText(null);

             */
        }

        }

    public void clicked_AnnulerenRegistreren(MouseEvent mouseEvent) {
        // wanneer de gebruiker de registratie annuleert wilt dit zeggen dat hij / zij al een werkend account in bezig heeft.
        // hiermee worden ze dan terug gestuurd naar het inlogscherm
        loadScreen(mouseEvent,"view/Inloggen.fxml");
    }
}
