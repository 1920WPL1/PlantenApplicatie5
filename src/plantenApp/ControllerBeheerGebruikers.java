package plantenApp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;
import plantenApp.java.model.LoginMethods;

import javax.swing.*;
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
    public AnchorPane anchorPane;

    private Connection connection;
    private GebruikerDAO gebruikerDAO;
    private ObservableList<Gebruiker> gebruikersFound;
    private Gebruiker gebruikerSelected = null;

    // documentatie listviews:  https://docs.oracle.com/javafx/2/ui_controls/list-view.htm

    /* @Author Jasper */
    public void initialize() throws SQLException {
        this.connection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(connection);

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
    /* @Author Jasper */

    public void refreshGebruikersFound(){
        try{
            List<Gebruiker> listGebruikersFound =
                    gebruikerDAO.getGebruikersByFullName(txtZoekFGebruiker.getText());
            // gebruikers is een ObservableList en listGebruikersFound wordt gebruikt om hem te vullen
            gebruikersFound = FXCollections.observableList(listGebruikersFound);
            lstGebruikersLijst.setItems(gebruikersFound);
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Geen verbinding met de server \r\n Contacteer uw systeembeheer indien dit probleem blijft aanhouden","Geen verbinding", JOptionPane.ERROR_MESSAGE);
        }
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
     * @param actionEvent
     */
    public void clicked_wijzigGebruiker(ActionEvent actionEvent){
        if(gebruikerSelected == null)
        {
            lblMessage.setText("Gelieve een gebruiker te selecteren");
        } else{
            try{
                int iGeslaagd = gebruikerDAO.setGebruikerById(
                        gebruikerSelected.getGebruiker_id(), txtVoornaam.getText(), txtNaam.getText(), txtEmail.getText(), cmbGebruikerRol.getSelectionModel().getSelectedItem());
                String sResult = (iGeslaagd == 1) ? "Wijziging uitgevoerd" : "Wijziging niet uitgevoerd";
                lblMessage.setText(sResult);
                refreshGebruikersFound();
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Geen verbinding met de server \r\n Contacteer uw systeembeheer indien dit probleem blijft aanhouden","Geen verbinding", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**@Author Jasper
     * @param actionEvent
     */
    public void clicked_VerwijderenGebruiker(ActionEvent actionEvent){
        if(gebruikerSelected == null)
        {
            lblMessage.setText("Gelieve een gebruiker te selecteren");
        } else{
            try{
                int iGeslaagd = gebruikerDAO.deleteGebruikerById(gebruikerSelected.getGebruiker_id());
                String sResult = (iGeslaagd == 1) ? "Gebruiker verwijderd" : "Gebruiker niet verwijderd";
                lblMessage.setText(sResult);
                if(iGeslaagd == 1){
                    refreshGebruikersFound(); // gezochte gebruikers opnieuw ophalen => verwijderde gebruiker wordt niet meer getoond in lijst
                }
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Geen verbinding met de server \r\n Contacteer uw systeembeheer indien dit probleem blijft aanhouden","Geen verbinding", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void click_home(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/HoofdScherm.fxml");
    }
}