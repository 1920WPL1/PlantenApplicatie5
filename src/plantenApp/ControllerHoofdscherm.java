package plantenApp;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import plantenApp.java.model.Gebruiker;

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
    }

    public void click_ProfielBeheren(MouseEvent mouseEvent) {
    }

    public void click_RegistratiesBeheren(MouseEvent mouseEvent) {
    }

    public void click_GebruikersBeheren(MouseEvent mouseEvent) {
    }

    public void clicked_ToevoegenPlant(MouseEvent mouseEvent) {
    }

    public void click_PlantZoekWijzig(MouseEvent mouseEvent) {
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
