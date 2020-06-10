package plantenApp;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    public void initialize() throws SQLException {

    }

    public void click_NaarZoekscherm(MouseEvent mouseEvent) {
        // waar alleen de oud-student kan gebruik van maken. hij kan alleen planten zoeken, meer niets.
        LoginMethods.loadScreen(anchorPane, getClass(), "view/Zoekscherm.fxml");
    }

    public void click_ProfielBeheren(MouseEvent mouseEvent) {

        LoginMethods.loadScreen(anchorPane, getClass(), "view/BeheerGebruikers.fxml");
    }

    public void click_RegistratiesBeheren(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/BeheerRegistraties.fxml");
    }

    public void click_GebruikersBeheren(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/BeheerGebruikers.fxml");
    }

    public void clicked_ToevoegenPlant(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/PlantToevoegen.fxml");
    }

    public void click_PlantZoekWijzig(MouseEvent mouseEvent) {
        // waar student / docent meer bevoegdheden hebben op het zoekscherm.
        LoginMethods.loadScreen(anchorPane, getClass(), "view/Zoekscherm");
    }

    public void click_PlantAanvraagBeheren(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(),"view/BeheeBehandelingPlant.fxml");
    }

    public void click_ImporterenStudenten(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(),"view/ImporterenStudenten.fxml");
    }

    //methodes

    public void setButtons() {
        if (LoginMethods.userLoggedIn.getRol().equals("admin")) {
            btnZoekScherm.setVisible(false);
        }

        if (LoginMethods.userLoggedIn.getRol().equals("docent")) {
            btnZoekScherm.setVisible(false);
        }

        if (LoginMethods.userLoggedIn.getRol().equals("student")) {
            btnZoekScherm.setVisible(false);
            btnImporterenStudenten.setVisible(false);
        }

        if (LoginMethods.userLoggedIn.getRol().equals("oud-student")) {
            btnZoekScherm.setVisible(true);
            btnImporterenStudenten.setVisible(false);
        }

    }

}
