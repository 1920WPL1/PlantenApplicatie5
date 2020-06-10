package plantenApp;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import plantenApp.java.dao.Database;
import plantenApp.java.dao.GebruikerDAO;
import plantenApp.java.model.Gebruiker;
import plantenApp.java.model.LoginMethods;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;


public class ControllerBeheerProfiel {
    public Label lblVoornaamProfiel;
    public Label lblAchternaamProfiel;
    public Label lblEmailProfiel;
    public AnchorPane anchorPane;

    public Button btnWachtwoordWijzigen;
    public Button btnTerugHoofdscherm;

    private GebruikerDAO gebruikerDAO;
    private Gebruiker user;

    /**@Author Matthias Vancoillie
     * @apiNote instellen van connection en beheerProfiel
     * @throws SQLException
     */

    // als de gebruiker op het scherm Profielbeheren zit is hij zowiezo al ingelogd.

    public void initialize() throws SQLException {
        // connectie maken met de database planten
        Connection dbConnection = Database.getInstance().getConnection();
        gebruikerDAO = new GebruikerDAO(dbConnection);
        beheerGebruiker();

    }

        // Werken met een select, waar men de laatste user id selecteert die heeft ingelogd.
        // Werken doen we met volgende query:  SELECT * FROM gebruiker WHERE gebruiker_id = ?

        // In een nieuwe methode in GebruikerDao sla je het resultaat op in Gebruiker object (zoals bij getAllGebruiker() )
        //In de controller roep je de methode op en sla je de gebruiker op als locale variabele,
        public void beheerGebruiker() throws SQLException {
            /*
            Label lVoornaam = lblVoornaamProfiel;
            Label lAchternaam = lblAchternaamProfiel;
            Label lEmail = lblEmailProfiel;
             */

           /*  kan dit? lblVoornaamProfiel.setText("voornaam".toString());
            gebruikerDAO.getById(lblVoornaamProfiel.setText());
            gebruikerDAO.getById(lblAchternaamProfiel.setText());
            gebruikerDAO.getById(lblEmailProfiel.setText());*/

    }


        //daarna kan je getNaam, getVoornaam en getEmail gebruiken van het gebruiker object
    /*
         gebruikerDAO.getVoornaam(lblVoornaamProfiel.getText());
         gebruikerDAO.getAchternaam(lblAchternaamProfiel.getText());
         gebruikerDAO.getEmail(lblEmailProfiel.getText());
    */


        // Het tonen van de naam, achternaam en e-mail van de gebruiker die is ingelogd.
        // deze worden getoond elk apart in een label
        // voornaam, achternaam, e-mail




    // Labels opvullen met de data van de ingelogde persoon
    // als er een andere gebruiker inlogt moeten zijn records verschijnen in het scherm.

    public void clicked_WWWijzigenProfiel(MouseEvent mouseEvent) {
        // als de gebruiker op deze knop drukt wordt hij naar het wijzig wachtwoord scherm gebracht
        LoginMethods.loadScreen(anchorPane, getClass(), "view/WachtwoordWijzigen.fxml");
    }

    public void clicked_TerugHoofdscherm(MouseEvent mouseEvent) {
        // het terugkeren naar het hoofdscherm
        // als de gebruiker zijn wachtwoord niet wil wijzigen / zijn gegevens heeft gecheckt.
        LoginMethods.loadScreen(anchorPane, getClass(), "view/Hoofdscherm.fxml");
    }
}
