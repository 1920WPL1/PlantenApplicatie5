package plantenApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import plantenApp.java.model.Gebruiker;
import javafx.stage.Stage;
import plantenApp.java.dao.GebruikerDAO;

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

    private Gebruiker user;

    public void click_NaarZoekscherm(MouseEvent mouseEvent) {
        // waar alleen de oud-student kan gebruik van maken. hij kan alleen planten zoeken, meer niets.
        loadScreen(mouseEvent, "view/Zoekscherm.fxml");
    }

    public void click_ProfielBeheren(MouseEvent mouseEvent) {

    }

    public void click_RegistratiesBeheren(MouseEvent mouseEvent) {
        loadScreen(mouseEvent,"view/BeheerRegistraties.fxml");
    }

    public void click_GebruikersBeheren(MouseEvent mouseEvent) {
        loadScreen(mouseEvent,"view/BeheerGebruikers.fxml");
    }

    public void clicked_ToevoegenPlant(MouseEvent mouseEvent) {
        loadScreen(mouseEvent, "view/PlantToevoegen.fxml");
    }

    public void click_PlantZoekWijzig(MouseEvent mouseEvent) {
        // waar student / docent meer bevoegdheden hebben op het zoekscherm.
        loadScreen(mouseEvent, "view/Zoekscherm");
    }

    public void click_PlantAanvraagBeheren(MouseEvent mouseEvent) {
    }

    //methodes
    public void setUser(Gebruiker user){
        this.user = user;
    }

    public void setButtons() {
        if(user.getRol().equals("admin")) {
            btnZoekScherm.setVisible(false);
        }

    }

}
