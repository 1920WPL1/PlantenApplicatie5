package plantenApp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ControllerBeheerRegistraties {
    public ListView lstAanvraagRegistraties;
    public Button btnAanvraagGoedkeuren;
    public Button btnAanvraagAfwijzen;
    public Button btnHoofdscherm;
    public ComboBox cmbGebruikerRol;
    public Label lblMessage;
    public Label lblNaam;
    public Label lblVoornaam;
    public Label lblEmail;

    private Connection connection;
    private ObservableList<Gebruiker> aanvragenFound;
    private Gebruiker aanvraagSelected = null;

    /*@Author Jasper*/
    /* Aanvragen zijn Gebruiker objecten met voornaam, naam en email*/


    /**@Author Jasper
     * @apiNote instellen van connection en combobox en Cellfactory / Listener van ListView
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        this.connection = Database.getInstance().getConnection();

        // Gebruikers in aanvraag ophalen en tonen
        refreshAanvragenFound();

        // Tonen van email aanvragen ipv 'gebruiker' object
        // door CellFactory van ListView aan te passen zodat hij ListCells aanmaakt met eigen invulling voor updateItem( item, bool)
        lstAanvraagRegistraties.setCellFactory(param -> new ListCell<Gebruiker>() {
            @Override
            protected void updateItem(Gebruiker gebruiker, boolean isEmpty) {
            super.updateItem(gebruiker, isEmpty);
            if (gebruiker == null || isEmpty) {
                setText(null);
            } else {
                setText(gebruiker.getEmail());
            }
            }
        });

        // Combobox met rol vullen
        cmbGebruikerRol.setItems(FXCollections.observableArrayList("gast", "student", "docent", "admin"));

        // Toevoegen Listener aan Listview om details van geselecteerde aanvraag te tonen/verbergen en combobox rol (on)aanpasbaar te maken
        lstAanvraagRegistraties.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Gebruiker>() {
                @Override
                public void changed(ObservableValue<? extends Gebruiker> observableValue, Gebruiker oldSelect, Gebruiker newSelect ) {
                    if(newSelect != null) { // newSelect kan null zijn door verwijdering aanvraag
                        aanvraagSelected = newSelect;
                        lblVoornaam.setText(newSelect.getVoornaam());
                        lblNaam.setText(newSelect.getAchternaam());
                        lblEmail.setText(newSelect.getEmail());
                        lblMessage.setText("");
                        cmbGebruikerRol.setDisable(false);
                        cmbGebruikerRol.getSelectionModel().select(newSelect.getRol());
                    }
                    else{ // Geen aanvraag geselecteerd => labels en combobox aanpassen
                        aanvraagSelected = null;
                        lblVoornaam.setText("");
                        lblNaam.setText("");
                        lblEmail.setText("");
                        lblMessage.setText("Geen gebruiker geselecteerd");
                        cmbGebruikerRol.getSelectionModel().clearSelection();
                        cmbGebruikerRol.setDisable(true);
                    }
                }
            }
        );
    }
    /* @Author Jasper */
    private void refreshAanvragenFound() throws SQLException {
        List<Gebruiker> listAanvragenFound =
                new GebruikerDAO(connection).getAllGebruikersInAanvraag();
        // aanvragenFound is een ObservableList en listAanvragenFound wordt gebruikt om hem te vullen
        aanvragenFound = FXCollections.observableList(listAanvragenFound);
        lstAanvraagRegistraties.setItems(aanvragenFound);
    }

    public void clicked_Goedkeuren(MouseEvent mouseEvent) {

    }

    public void clicked_Afwijzen(MouseEvent mouseEvent) {
    }

    public void clicked_NaarHoofdscherm(MouseEvent mouseEvent) {
    }
}
