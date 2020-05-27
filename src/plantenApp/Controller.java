package plantenApp;



import plantenApp.java.dao.Database;
import plantenApp.java.dao.InfoTablesDAO;
import plantenApp.java.model.InfoTables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;


public class Controller {
    public ComboBox<String> cboType;
    public ComboBox cboFamilie;
    public TextField txtNaam;
    public Spinner nudMinBloeiHoogte;
    public Spinner nudMinBladHoogte;
    public Spinner nudMaxBloeiHoogte;
    public Spinner nudMaxBladHoogte;
    public ComboBox cboHabitat;
    public CheckBox chkZand;
    public CheckBox chkLeem;
    public CheckBox chkKlei;
    public Slider slrBezonning;
    public Slider slrVochtbehoefte;
    public Button btnZoek;
    public ComboBox<String> cboBladgrootte;
    public Button btnminimize;
    public Button btnExit;
    public Button btnMaximize;
    public ComboBox<String> cboRatio;
    public ComboBox<String> cboSpruitFenologie;
    public ImageView imgAar;
    public ImageView imgBredePluim;
    public ImageView imgEtage;
    public ImageView imgKnop;
    public ImageView imgScherm;
    public ImageView imgSmallePluim;
    public ImageView imgMargriet;
    public ImageView imgPlatScherm;
    public ComboBox<String> cboBladvorm;
    public RadioButton rdbBloeiwijze1;
    public RadioButton rdbBloeiwijze2;
    public RadioButton rdbBloeiwijze3;
    public RadioButton rdbBloeiwijze4;
    public RadioButton rdbBloeiwijze5;
    public RadioButton rdbBloeiwijze6;
    public RadioButton rdbBloeiwijze7;
    public RadioButton rdbBloeiwijze8;
    public CheckBox chkHabitus1;
    public CheckBox chkHabitus4;
    public CheckBox chkHabitus7;
    public CheckBox chkHabitus10;
    public CheckBox chkHabitus13;
    public CheckBox chkHabitus2;
    public CheckBox chkHabitus5;
    public CheckBox chkHabitus8;
    public CheckBox chkHabitus11;
    public CheckBox chkHabitus14;
    public CheckBox chkHabitus3;
    public CheckBox chkHabitus6;
    public CheckBox chkHabitus9;
    public CheckBox chkHabitus12;
    public CheckBox chkHabitus15;
    public Spinner cboFrequentie;
    public ComboBox<String> cboMaand;
    public ComboBox cboBehandeling;
    public CheckBox chkBezonning;
    public CheckBox chkVochtbehoefte;
    public ListView lsvUitvoer;
    public ComboBox<String> cboReactie;
    public Slider slrOntwikkelingssnelheid;
    public CheckBox chkLevensvorm2;
    public CheckBox chkLevensvorm3;
    public CheckBox chkLevensvorm4;
    public CheckBox chkLevensvorm5;
    public CheckBox chkLevensvorm6;
    public CheckBox chkLevensvorm7;
    public CheckBox chkLevensvorm8;
    public CheckBox chkLevensvorm9;
    public CheckBox chkSocI;
    public CheckBox chkSocII;
    public CheckBox chkSocIII;
    public CheckBox chkSocIV;
    public CheckBox chkSocV;
    public CheckBox chkPollenwaarde;
    public CheckBox chkNectarwaarde;
    public Slider slrPollenwaarde;
    public Slider slrNectarwaarde;
    public CheckBox chkOntwikkelingsnelheid;
    public Slider slrVoedingsbehoefte;
    public CheckBox chkVoedingsbehoefte;
    public CheckBox chkLevensvorm1;
    private boolean maximized=false;
    private InfoTables infoTables;
    private Connection dbConnection;

    public void initialize() throws SQLException {
        dbConnection = Database.getInstance().getConnection();

        /*instellen exit, minimize en resize button*/
        ScreenControl();

        /*infotabel object aanmaken*/
        InfoTablesDAO infotablesDAO = new InfoTablesDAO(dbConnection);
        infoTables = infotablesDAO.getInfoTables();

        /*comboboxes vullen*/
        FillComboboxes(infoTables);


    }
    /**
     @param infotables -> lijst van alle lijsten van gegevens uit de naakte tabellen
     @author bradley
     Functie om comboboxes te vullen met alle gegevens uit de database
     */
    public void FillComboboxes(InfoTables infotables){
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
        cboMaand.getItems().addAll("Januari", "februari", "maart", "april", "mei", "juni", "juli","augustus","september", "oktober", "november", "december");
        //ratio
        cboRatio.getItems().addAll(infotables.getBloeiBladRatios());
        //spruitfenologie
        cboSpruitFenologie.getItems().addAll(infotables.getSpruitfenologieen());
        //reactie antagonistische omgeving
        cboReactie.getItems().addAll(infotables.getAntagonistischeOmgevingsReacties());
        //behandeling

    }

    public void Clicked_Search(MouseEvent mouseEvent) {

    }

    /**
     @author bradley
     Functie om scherm aan te passen tussen volledig scherm en normale grootte
     */
    public void resize(){
        if(maximized == true){
            btnMaximize.setOnAction(e ->
                    ( (Stage) ( (Button) e.getSource() ).getScene().getWindow() ).setMaximized(false));
            maximized=false;
        } else {
            btnMaximize.setOnAction(e ->
                    ( (Stage) ( (Button) e.getSource() ).getScene().getWindow() ).setMaximized(true));
            maximized=true;
        }
    }

    /**
     @author bradley
     Functie om scherm beheer knoppen te gebruiken
     */
    public void ScreenControl(){
        btnExit.setOnAction(e ->
                ((Stage) ((Button) e.getSource()).getScene().getWindow()).close()
        );

        btnminimize.setOnAction(e ->
                ((Stage) ((Button) e.getSource()).getScene().getWindow()).setIconified(true)
        );
        resize();
    }

    /**
     @author bradley
     wanneer hier op geklikt wordt, wordt de schermgrootte aangepast
     */
    public void Clicked_Resize(MouseEvent mouseEvent) {
        resize();
    }

    /**
     * @author Bradley Velghe
     * @param box
     * @param slider
     * @return true > slider is beweegbaar || false > slider is niet beweegbaar
     */
    public boolean Togledisable(CheckBox box, Slider slider){
        if(box.isSelected()){
            slider.setDisable(false);
            return true;
        } else {
            slider.setDisable(true);
            return false;
        }
    }

    public void Clicked_Bezonning(MouseEvent mouseEvent) {
        Togledisable(chkBezonning, slrBezonning);
    }

    public void Clicked_Vochtbehoefte(MouseEvent mouseEvent) {
        Togledisable(chkVochtbehoefte, slrVochtbehoefte);
    }

    public void Clicked_VoedingsBehoefte(MouseEvent mouseEvent) {
        Togledisable(chkVoedingsbehoefte, slrVoedingsbehoefte);
    }

    public void Clicked_Ontwikkelingssnelheid(MouseEvent mouseEvent) {
        Togledisable(chkOntwikkelingsnelheid, slrOntwikkelingssnelheid);
    }

    public void Clicked_Pollenwaarde(MouseEvent mouseEvent) {
        Togledisable(chkPollenwaarde, slrPollenwaarde);
    }

    public void Clicked_Nectarwaarde(MouseEvent mouseEvent) {
        Togledisable(chkNectarwaarde, slrNectarwaarde);
    }
}


