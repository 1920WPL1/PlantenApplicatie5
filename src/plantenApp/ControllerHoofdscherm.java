package plantenApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import plantenApp.java.dao.GebruikerDAO;

public class ControllerHoofdscherm {
    public Button btnZoekScherm;
    public Button btnProfielBeheren;
    public Button btnRegistratiesBeheren;
    public Button btnGebruikersBeheren;
    public Button btnToevoegenPlant;
    public Button btnPlantZoekWijzig;
    public Button btnPlantenAanvraag;

    private GebruikerDAO gebruikerDAO;

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
     * @param  mouseEvent
     * @Return overgangen / werking Hoofdscherm
     */

    /*
    * Rollen binnen de applicatie:
    * Student
    * Oud-student
    * Docent
    * Admin
    * */

    // Enable knoppen voor rollen
    // om de knoppen dan weer op enable te krijgen wanneer de rol in functie zich op het hoofscherm bevindt werken we met - btnButton.setDisable(false).


    // Disable knoppen voor rollen
    // om de knoppen te onklikbaar te maken werken we met - btnButton.setDisable(isDisabled)

    // eerst verwijzen we aan de hand van MouseEvent de schermen door uit het hoofdscherm.


    public void click_NaarZoekscherm(MouseEvent mouseEvent) {
        // waar alleen de oud-student kan gebruik van maken. hij kan alleen planten zoeken, meer niets.
        loadScreen(mouseEvent, "view/Zoekscherm.fxml");
    }

    public void click_ProfielBeheren(MouseEvent mouseEvent) {
        loadScreen(mouseEvent, "view/BeheerGebruikers.fxml");
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
        loadScreen(mouseEvent,"view/BeheeBehandelingPlant.fxml");
    }
}
