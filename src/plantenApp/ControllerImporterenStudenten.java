package plantenApp;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import plantenApp.java.model.LoginMethods;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerImporterenStudenten {

    public AnchorPane anchorPane;
    public Button btnOpen;
    public Label lblFileSelected;

    private FileChooser chooser; // kiezen van Excel file
    private File fileSelected; // gekozen file
    private Desktop desktop; // bevat methode voor openen gekozen file

    public void initialize(){
        desktop = Desktop.getDesktop();
        chooser = new FileChooser();
    }

    /* click events */
    public void clicked_btnOpen(MouseEvent mouseEvent) {
        configureFileChooser(chooser);
        fileSelected = chooser.showOpenDialog(anchorPane.getScene().getWindow());
        if (fileSelected != null) {
            lblFileSelected.setText(fileSelected.getName());
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Open bestand met studenten");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel", "*.xlsx*","*.xls")
        );
    }

    public void clicked_Terug(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane,getClass(),"view/HoofdScherm.fxml");
    }

    public void clicked_Importeren(MouseEvent mouseEvent) {
        // TODO
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
