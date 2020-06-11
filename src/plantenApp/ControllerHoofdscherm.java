package plantenApp;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import plantenApp.java.model.Gebruiker;
import plantenApp.java.model.LoginMethods;

import java.sql.SQLException;

/**
 * @author Bart Maes
 */

public class ControllerHoofdscherm {
    public Button btnZoekScherm;
    public Button btnProfielBeheren;
    public Button btnRegistratiesBeheren;
    public Button btnGebruikersBeheren;
    public Button btnToevoegenPlant;
    public Button btnPlantZoekWijzig;
    public Button btnPlantenAanvraag;
    public AnchorPane anchorPane;
    public Button btnImporterenStudenten;

    private Gebruiker user;

    public void initialize() throws SQLException {
        //ingelogde user ophalen
        user = LoginMethods.userLoggedIn;
        //buttons juist zetten volgens de rol van de ingelogde user
        setButtons();
    }

    //ga naar zoekscherm
    public void click_NaarZoekscherm(ActionEvent actionEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/Zoekscherm.fxml");
    }

    //ga naar scherm voor het beheren van het profiel, waar een gebruikere eventueel ook zijn/haar wachtwoord kan resetten
    public void click_ProfielBeheren(ActionEvent actionEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/BeheerProfiel.fxml");
    }

    //ga naar scherm voor het beheren van de aanvragen
    public void click_RegistratiesBeheren(ActionEvent actionEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/BeheerRegistraties.fxml");
    }

    //ga naar het scherm voor het beheren van de gebruikers
    public void click_GebruikersBeheren(ActionEvent actionEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/BeheerGebruikers.fxml");
    }

    //ga naar het scherm voor het toevoegen van een plant
    public void clicked_ToevoegenPlant(ActionEvent actionEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/PlantToevoegen.fxml");
    }

    //ga naar het zoekscherm
    public void click_PlantZoekWijzig(ActionEvent actionEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/Zoekscherm.fxml");
    }

    //ga naar het scherm voor het beheren van plantaanvragen
    public void click_PlantAanvraagBeheren(ActionEvent actionEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(),"view/BeheeBehandelingPlant.fxml");
    }

    public void click_ImporterenStudenten(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(),"view/ImporterenStudenten.fxml");
    }

    //methodes

    //hier wordt op basis van de rol van de ingelogde user alle buttons goed gezet: hiden en eventueel wat herschikken van de buttons
    private void setButtons() {
        if (user.getRol().equals("docent")) {
            btnRegistratiesBeheren.setVisible(false);
            btnGebruikersBeheren.setVisible(false);
        }

        if (user.getRol().equals("student")) {
            disableBeheerButtons();
            btnToevoegenPlant.setLayoutY(321);
            btnToevoegenPlant.setLayoutX(188.5);
        }

        if (user.getRol().equals("gast")) {
            disableBeheerButtons();
            btnPlantZoekWijzig.setVisible(false);
            btnToevoegenPlant.setVisible(false);
            btnZoekScherm.setLayoutY(250);
            btnZoekScherm.setLayoutX(188.5);
        }
    }

    //de buttons ivm beheer nog eens apart gestoken, omdat dit al voor 2 rollen verborgen moet zijn
    private void disableBeheerButtons() {
        btnPlantenAanvraag.setVisible(false);
        btnRegistratiesBeheren.setVisible(false);
        btnGebruikersBeheren.setVisible(false);
    }

}
