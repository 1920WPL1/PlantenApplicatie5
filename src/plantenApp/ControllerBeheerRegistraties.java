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
import plantenApp.java.utils.JavaMailUtil;

import javax.swing.*;
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
    public AnchorPane anchorPane;

    private Connection connection;
    private GebruikerDAO gebruikerDAO;
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
        gebruikerDAO = new GebruikerDAO(connection);
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
        try{
            List<Gebruiker> listAanvragenFound = gebruikerDAO.getAllGebruikersInAanvraag();
            // aanvragenFound is een ObservableList en listAanvragenFound wordt gebruikt om hem te vullen
            aanvragenFound = FXCollections.observableList(listAanvragenFound);
            lstAanvraagRegistraties.setItems(aanvragenFound);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Geen verbinding met de server \r\n Contacteer uw systeembeheer indien dit probleem blijft aanhouden","Geen verbinding", JOptionPane.ERROR_MESSAGE);
        }
    }

    /* @Author Jasper */
    public void clicked_Goedkeuren(ActionEvent actionEvent){
        if(aanvraagSelected == null){
            lblMessage.setText("Gelieve een aanvraag te selecteren");
        } else{
            try{
                String rolSelected = (String) cmbGebruikerRol.getSelectionModel().getSelectedItem();
                // aanvraag_status 2 = goedgekeurd
                int iGeslaagd = gebruikerDAO.setGebruikerAanvraagStatusEnRol(aanvraagSelected.getGebruiker_id(), 2, rolSelected);

                //@author Bart Maes
                //bevestiginsmail dat de gebruiker zijn aanvraag is goedgekeurd, anders weet de gebruiker niet wanneer hij/zij zich kan registreren
                if(iGeslaagd == 1) {
                    JavaMailUtil.sendMail(aanvraagSelected.getEmail(), "Uw aanvraag is goedgekeurd", "Beste " + aanvraagSelected.getVoornaam() + ", \r\n\nUw aanvraag voor toegang tot de Plantenapplicatie van VIVES is goedgekeurd. \n" +
                            "\nU kan zich registreren de volgende keer dat u de applicatie opent. \n" +
                            "\n" +
                            "Met vriendelijke groeten, \n" +
                            "\n" +
                            "Het VIVES-plantenteam");
                }

                // instellen label message
                String sMessage = (iGeslaagd == 1) ? "Aanvraag goedgekeurd" : "Aanvraag niet goedgekeurd";
                lblMessage.setText(sMessage);
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Geen verbinding met de server \r\n Contacteer uw systeembeheer indien dit probleem blijft aanhouden","Geen verbinding", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Er kon geen mail verstuurd worden","Fout", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /* @Author Jasper */
    public void clicked_Afwijzen(ActionEvent actionEvent) throws Exception {
        // gebruiker wordt verwijderd uit database
        if(aanvraagSelected == null){
            lblMessage.setText("Gelieve een aanvraag te selecteren");
        } else{
            try{
                int iGeslaagd = gebruikerDAO.deleteGebruikerById(aanvraagSelected.getGebruiker_id());

                //@author Bart Maes
                //bevestiginsmail dat de gebruiker zijn aanvraag is afgekeurd
                if(iGeslaagd == 1) {
                    JavaMailUtil.sendMail(aanvraagSelected.getEmail(), "Uw aanvraag is afgekeurd", "Beste " + aanvraagSelected.getVoornaam() + ", \r\n\nUw aanvraag voor toegang tot de Plantenapplicatie van VIVES is helaas afgekeurd. \n" +
                            "\nMet vriendelijke groeten, \n" +
                            "\n" +
                            "Het VIVES-plantenteam");
                }

                String sResult = (iGeslaagd == 1) ? "Aanvraag verwijderd" : "Aanvraag niet verwijderd";
                lblMessage.setText(sResult);

                if(iGeslaagd == 1){
                    refreshAanvragenFound(); // gezochte aanvragen opnieuw ophalen => verwijderde aanvraag wordt niet meer getoond in lijst
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
