package plantenApp;

import javafx.scene.input.MouseEvent;
import plantenApp.java.model.Gebruiker;

import java.awt.*;
import java.sql.Connection;

public class ControllerBeheerProfiel {
    public Label lblVoornaamProfiel;
    public Label lblAchternaamProfiel;
    public Label lblEmailProfiel;

    public Button btnWachtwoordWijzigen;
    public Button btnTerugHoofdscherm;

    private Connection connection;
    private Gebruiker gebruiker;





    public void clicked_WWWijzigenProfiel(MouseEvent mouseEvent) {
        // het wijzigen van een wachtwoord van je profiel.
    }

    public void clicked_TerugHoofdscherm(MouseEvent mouseEvent) {
        // het terugkeren naar het hoofdscherm

    }
}
