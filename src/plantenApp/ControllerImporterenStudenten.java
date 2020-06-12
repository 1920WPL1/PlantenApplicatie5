package plantenApp;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.LoginMethods;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerImporterenStudenten {

    public AnchorPane anchorPane;
    public Button btnOpen;
    public Label lblFileSelected;

    private FileChooser chooser; // kiezen van CSV file
    private File fileSelected; // gekozen file
    private Desktop desktop; // bevat methode voor openen gekozen file
    private Connection connection;
    private GebruikerDAO gebruikerDAO;

    public void initialize() throws SQLException {
        desktop = Desktop.getDesktop();
        chooser = new FileChooser();
        connection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(connection);
    }

    /* click events */
    public void clicked_btnOpen(MouseEvent mouseEvent) {
        configureFileChooser(chooser);
        fileSelected = chooser.showOpenDialog(anchorPane.getScene().getWindow());
        if (fileSelected != null) {
            lblFileSelected.setText(fileSelected.getName());
        } else{
            lblFileSelected.setText("Geen CSV bestand geselecteerd");
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Open bestand met studenten");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV", "*.csv*")
        );
    }

    public void clicked_Terug(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane,getClass(),"view/HoofdScherm.fxml");
    }

    public void clicked_Importeren(MouseEvent mouseEvent) throws SQLException {
        try{
            if(fileSelected != null){
                gebruikerDAO.importGebruikersfromCsv(fileSelected.getPath());
                JOptionPane.showMessageDialog(null,"Importeren succesvol", "Importeren studenten", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null,"Gelieve een CSV bestand te selecteren", "Importeren studenten", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Geen verbinding met de server \r\n Contacteer uw systeembeheer indien dit probleem blijft aanhouden","Geen verbinding", JOptionPane.ERROR_MESSAGE);
        }
    }

    /* hulp functies */
    private void openFile(File file) {
        EventQueue.invokeLater(() -> {
            try {
                desktop.open(file);
            } catch (IOException ex) {
                Logger.getLogger(ControllerImporterenStudenten.
                        class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        });
    }

}
