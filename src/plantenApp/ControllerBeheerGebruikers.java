package plantenApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ControllerBeheerGebruikers {

    public ComboBox<String> cmbGebruikerRol;
    public Label lblNaamGebruiker;
    public Label lblVNaamGebruiker;
    public Label lblEmailGebruiker;
    public ListView lstGebruikersLijst;
    public TextField txtZoekFGebruiker;
    public Button btnWijzigGebruiker;
    public Button btnGebruikerVerwijderen;
    public Button btnZoekScherm;

    private Connection connection;
    private ObservableList<Gebruiker> gebruikers;
    private ObservableList<Gebruiker> gebruikersFound;

    // documentatie listviews:  https://docs.oracle.com/javafx/2/ui_controls/list-view.htm

    /**@Author Jasper
     * @apiNote instellen van connection en weergave ListView
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        this.connection = Database.getInstance().getConnection();

        // Tonen van naam gebruikers ipv 'gebruiker' object
        // door CellFactory van ListView aan te passen zodat hij ListCells aanmaakt met eigen invulling voor updateItem( item, bool)
        lstGebruikersLijst.setCellFactory(param -> new ListCell<Gebruiker>(){
            @Override
            protected void updateItem(Gebruiker gebruiker, boolean isEmpty){
                super.updateItem(gebruiker, isEmpty);
                if(gebruiker == null || isEmpty){
                    setText(null);
                } else{
                    setText(gebruiker.getVoornaam() + " " + gebruiker.getAchternaam());
                }
            }
        });
    }

    /**@Author Jasper
     * @apiNote Zoeken van gebruiker op voornaam en achternaam. Resultaat verschijnt in ListView
     * @param actionEvent
     */
    public void enter_zoekGebruikers(ActionEvent actionEvent) throws SQLException {
        List<Gebruiker> listGebruikersFound =
                new GebruikerDAO(connection).getGebruikersByFullName(txtZoekFGebruiker.getText());
        // gebruikers is een ObservableList en listGebruikersFound wordt gebruikt om hem te vullen
        gebruikers = FXCollections.observableList(listGebruikersFound);
        lstGebruikersLijst.setItems(gebruikers);
    }

    public void clicked_wijzigGebruiker(MouseEvent mouseEvent) {

    }

    public void clicked_VerwijderenGebruiker(MouseEvent mouseEvent) {
    }

    public void clicked_NaarZoekscherm(MouseEvent mouseEvent) {
    }

}
