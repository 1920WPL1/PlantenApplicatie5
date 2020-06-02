package plantenApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class controller_login {
    // Scherm: Inloggen

    /**
     * Author Bart
     * @param mouseEvent
     * @Return overgang van het login naar zoekscherm
     */
    public void clicked_Login(MouseEvent mouseEvent) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/Zoekscherm.fxml"));
            Scene scene = new Scene(root); Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            window.setScene(scene); window.show();
        } catch(Exception e) {
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
     * Author Bart
     * @param mouseEvent
     * @Return overgang van het beheer gebruiker naar zoekscherm
     */
    public void click_Terug(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/Zoekscherm.fxml"));
            Scene scene = new Scene(root); Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            window.setScene(scene); window.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void Click_SaveGebruiker(MouseEvent mouseEvent) {

    }

    public void Click_VerwijderGebruiker(MouseEvent mouseEvent) {

    }

    public void Click_wijzigWachtwoord(MouseEvent mouseEvent) {

    }
}
