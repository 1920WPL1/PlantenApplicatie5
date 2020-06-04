package plantenApp;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import plantenApp.java.dao.AbiotischeFactorenDAO;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.InfoTablesDAO;
import plantenApp.java.dao.PlantDAO;
import plantenApp.java.model.InfoTables;
import plantenApp.java.model.Plant;

import java.sql.Connection;
import java.sql.SQLException;


public class Controller {
    public ComboBox<String> cboType;
    public ComboBox cboFamilie;
    public ComboBox<String> cboBladgrootte;
    public ComboBox<String> cboRatio;
    public ComboBox<String> cboSpruitFenologie;
    public ComboBox<String> cboBladvorm;
    public ComboBox<String> cboMaand;
    public ComboBox<String> cboReactie;
    public Button btnNaarZoekScherm;
    private InfoTables infoTables;
    private Connection dbConnection;

    public void initialize() throws SQLException {
        dbConnection = Database.getInstance().getConnection();

        // dit is stukje test code van Siebe
        AbiotischeFactorenDAO abiotischeFactorenDAO = new AbiotischeFactorenDAO(dbConnection);
        PlantDAO plantDAO = new PlantDAO(dbConnection);
        Plant test = plantDAO.getPlantById(1);
        test.setAbiotischeFactoren(abiotischeFactorenDAO.getById(1));


        /*infotabel object aanmaken*/
        InfoTablesDAO infotablesDAO = new InfoTablesDAO(dbConnection);
        infoTables = infotablesDAO.getInfoTables();

        /*comboboxes vullen*/
        FillComboboxes(infoTables);


    }

    /**
     * @param infotables -> lijst van alle lijsten van gegevens uit de naakte tabellen
     * @author bradley
     * Functie om comboboxes te vullen met alle gegevens uit de database
     */
    public void FillComboboxes(InfoTables infotables) {
        //type
        System.out.println(infotables.getTypes().toString());
        cboType.getItems().addAll(infotables.getTypes());
        //familie
        cboFamilie.getItems().addAll(infotables.getFamilies());
        //bladgrootte
        cboBladgrootte.getItems().addAll(infotables.getBladgroottes());
        //bladvorm
        cboBladvorm.getItems().addAll(infotables.getBladvormen());
        //Levensvorm

        //BehandelingMaand
        cboMaand.getItems().addAll("Januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober", "november", "december");
        //ratio
        cboRatio.getItems().addAll(infotables.getBloeiBladRatios());
        //spruitfenologie
        cboSpruitFenologie.getItems().addAll(infotables.getSpruitfenologieen());
        //reactie antagonistische omgeving
        cboReactie.getItems().addAll(infotables.getAntagonistischeOmgevingsReacties());
        //behandeling

    }

    /**
     * @param box
     * @param slider
     * @return true > slider is beweegbaar || false > slider is niet beweegbaar
     * @author Bradley Velghe
     */
    public boolean Togledisable(CheckBox box, Slider slider) {
        if (box.isSelected()) {
            slider.setDisable(false);
            return true;
        } else {
            slider.setDisable(true);
            return false;
        }
    }
}


