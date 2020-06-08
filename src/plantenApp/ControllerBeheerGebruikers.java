package plantenApp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;
import plantenApp.java.model.LoginMethods;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ControllerBeheerGebruikers {

    public ComboBox<String> cmbGebruikerRol;
    public ListView lstGebruikersLijst;
    public TextField txtZoekFGebruiker;
    public Button btnWijzigGebruiker;
    public Button btnGebruikerVerwijderen;
    public Button btnHoofdscherm;
    public TextField txtNaam;
    public TextField txtVoornaam;
    public TextField txtEmail;
    public Label lblMessage;

    private Connection connection;
    private ObservableList<Gebruiker> gebruikersFound;
    private Gebruiker gebruikerSelected = null;

    // documentatie listviews:  https://docs.oracle.com/javafx/2/ui_controls/list-view.htm

    /**@Author Jasper
     * @apiNote instellen van connection en weergave ListView
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        this.connection = Database.getInstance().getConnection();

        // Tonen van naam gebruikers ipv 'gebruiker' object
        // door CellFactory van ListView aan te passen zodat hij ListCells aanmaakt met eigen invulling voor updateItem( item, bool)
        lstGebruikersLijst.setCellFactory(param -> new ListCell<Gebruiker>() {
                    @Override
                    protected void updateItem(Gebruiker gebruiker, boolean isEmpty) {
                        super.updateItem(gebruiker, isEmpty);
                        if (gebruiker == null || isEmpty) {
                            setText(null);
                        } else {
                            setText(gebruiker.getVoornaam() + " " + gebruiker.getAchternaam());
                        }
                    }
                });

        // Combobox met rol vullen
        cmbGebruikerRol.setItems(FXCollections.observableArrayList("gast", "student", "docent", "admin"));

        // Details van geselecteerde gebruiker tonen en controls (on)aanpasbaar maken
        lstGebruikersLijst.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Gebruiker>() {
                @Override
                public void changed(ObservableValue<? extends Gebruiker> observableValue, Gebruiker oldSelect, Gebruiker newSelect ) {
                    if(newSelect != null) { // newSelect kan null zijn na nieuwe zoekopdracht met minder resultaten
                        gebruikerSelected = newSelect;
                        txtVoornaam.setText(newSelect.getVoornaam());
                        txtNaam.setText(newSelect.getAchternaam());
                        txtEmail.setText(newSelect.getEmail());
                        cmbGebruikerRol.getSelectionModel().select(newSelect.getRol());
                        txtNaam.setDisable(false);
                        txtVoornaam.setDisable(false);
                        txtEmail.setDisable(false);
                        cmbGebruikerRol.setDisable(false);
                    }
                    else{ // Niemand geselecteerd => controls terug onaanpasbaar maken
                        gebruikerSelected = null;
                        txtVoornaam.setText("");
                        txtNaam.setText("");
                        txtEmail.setText("");
                        cmbGebruikerRol.getSelectionModel().clearSelection();
                        txtNaam.setDisable(true);
                        txtVoornaam.setDisable(true);
                        txtEmail.setDisable(true);
                        cmbGebruikerRol.setDisable(true);
                    }
                }
            }
        );
    }

    public void refreshGebruikersFound() throws SQLException {
        List<Gebruiker> listGebruikersFound =
                new GebruikerDAO(connection).getGebruikersByFullName(txtZoekFGebruiker.getText());
        // gebruikers is een ObservableList en listGebruikersFound wordt gebruikt om hem te vullen
        gebruikersFound = FXCollections.observableList(listGebruikersFound);
        lstGebruikersLijst.setItems(gebruikersFound);
    }

    /**@Author Jasper
     * @apiNote Enter in zoekvak: zoek op voornaam en achternaam. Resultaat verschijnt in ListView
     * @param actionEvent
     */
    public void enter_zoekGebruikers(ActionEvent actionEvent) throws SQLException {
        if(!txtZoekFGebruiker.getText().equals("")) // om te vermijden dat je alle gebruikers zou tonen (= te veel)
        {
            refreshGebruikersFound();
        }
    }

    /**@Author Jasper
     * @param mouseEvent
     * @throws SQLException
     */
    public void clicked_wijzigGebruiker(MouseEvent mouseEvent) throws SQLException {
        if(gebruikerSelected == null)
        {
            lblMessage.setText("Gelieve een gebruiker te selecteren");
        } else{
            int iGeslaagd = new GebruikerDAO(connection).setGebruikerById(
                    gebruikerSelected.getId(), txtVoornaam.getText(), txtNaam.getText(), txtEmail.getText(), cmbGebruikerRol.getSelectionModel().getSelectedItem());
            String sResult = (iGeslaagd == 1) ? "Wijziging uitgevoerd" : "Wijziging niet uitgevoerd";
            lblMessage.setText(sResult);
        }
    }

    /**@Author Jasper
     * @param mouseEvent
     * @throws SQLException
     */
    public void clicked_VerwijderenGebruiker(MouseEvent mouseEvent) throws SQLException {
        if(gebruikerSelected == null)
        {
            lblMessage.setText("Gelieve een gebruiker te selecteren");
        } else{
            int iGeslaagd = new GebruikerDAO(connection).deleteGebruikerById(gebruikerSelected.getId());
            String sResult = (iGeslaagd == 1) ? "Gebruiker verwijderd" : "Gebruiker niet verwijderd";
            lblMessage.setText(sResult);
            if(iGeslaagd == 1){
                refreshGebruikersFound(); // gezochte gebruikers opnieuw ophalen => verwijderde gebruiker wordt niet meer getoond in lijst
            }
        }
    }

    public void clicked_NaarHoofdscherm(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(mouseEvent, getClass(),"view/Hoofdscherm.fxml");
    }
}