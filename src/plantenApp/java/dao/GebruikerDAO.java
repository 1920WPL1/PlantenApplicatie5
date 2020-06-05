package plantenApp.java.dao;
import plantenApp.java.model.Gebruiker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**@author Bart*/
public class GebruikerDAO implements Queries {
    private Connection dbConnection;
    private PreparedStatement stmtSelectGebruikerByEmail;
    /** @Author Jasper */
    private PreparedStatement stmtSelectGebruikersByFullName;
    private PreparedStatement stmtSetGebruikerById;
    private PreparedStatement stmtSetWachtwoordHash;

    public GebruikerDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        stmtSelectGebruikerByEmail = dbConnection.prepareStatement(GETGEBRUIKERBYEMAILADRES);
        stmtSelectGebruikersByFullName = dbConnection.prepareStatement(GETGEBRUIKERSBYFULLNAME);
        stmtSetGebruikerById = dbConnection.prepareStatement(SETGEBRUIKERBYID);
        stmtSetWachtwoordHash = dbConnection.prepareStatement(SETWACHTWOORDHASH);
    }

    public List<Gebruiker> getAllGebruiker() {
        List<Gebruiker> gebruikersList = new ArrayList<>();
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(GETALLGEBRUIKERS);
            while (rs.next()) {
                Gebruiker gebruiker =
                        new Gebruiker(
                                rs.getInt("gebruiker_id"),
                                rs.getString("voornaam"),
                                rs.getString("achternaam"),
                                rs.getString("email"),
                                rs.getString("rol"),
                                rs.getDate("aanvraag_datum"),
                                rs.getInt("aanvraag_goedgekeurd"),
                                rs.getBoolean("geregistreerd"),
                                rs.getBytes("wachtwoord_hash")
                        );
                gebruikersList.add(gebruiker);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GebruikerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gebruikersList;
    }

    /**@author Bart
     * @param email -> email
     * @return gebruikersgegevens van emailadres
     */
    public Gebruiker getByEmail(String email) throws SQLException {
        Gebruiker user = null;
        stmtSelectGebruikerByEmail.setString(1, email);
        ResultSet rs = stmtSelectGebruikerByEmail.executeQuery();
        if (rs.next()) {
            user = new Gebruiker(
                    rs.getInt("gebruiker_id"),
                    rs.getString("voornaam"),
                    rs.getString("achternaam"),
                    rs.getString("email"),
                    rs.getString("rol"),
                    rs.getDate("aanvraag_datum"),
                    rs.getInt("aanvraag_goedgekeurd"),
                    rs.getBoolean("geregistreerd"),
                    rs.getBytes("wachtwoord_hash")
            );
        }
        return user;
    }


    /**@Author Jasper
     * @param search De zoekterm om te zoeken op voornaam of achternaam
     * @return List met gevonden gebruikers
     */
    public List<Gebruiker> getGebruikersByFullName(String search) {
        List<Gebruiker> gebruikersList = new ArrayList<>();
        try {
            stmtSelectGebruikersByFullName.setString(1, "%"+search+"%");
            stmtSelectGebruikersByFullName.setString(2, "%"+search+"%");
            ResultSet rs = stmtSelectGebruikersByFullName.executeQuery();
            while (rs.next()) {
                Gebruiker gebruiker =
                        new Gebruiker(
                                rs.getInt("gebruiker_id"),
                                rs.getString("voornaam"),
                                rs.getString("achternaam"),
                                rs.getString("email"),
                                rs.getString("rol"),
                                rs.getDate("aanvraag_datum"),
                                rs.getInt("aanvraag_goedgekeurd"),
                                rs.getBoolean("geregistreerd"),
                                rs.getBytes("wachtwoord_hash")
                        );
                gebruikersList.add(gebruiker);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GebruikerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gebruikersList;
    }

    /**@author Jasper
     * @param id : id van gebruiker om nieuwe wachtwoord_hash in te stellen
     * @param hash : nieuwe wachtwoord_hash
     * @return 1 bij gewijzigd wachtwoord, 0 bij fout
     * @throws SQLException
     */
    public int setWachtWoordHash(int id, byte[] hash) throws SQLException {
        stmtSetWachtwoordHash.setBytes(1, hash);
        stmtSetWachtwoordHash.setInt(2, id);
        return stmtSetWachtwoordHash.executeUpdate();
    }

    /**
     * @param id id van gebruiker om te wijzigen
     * @param voornaam
     * @param achternaam
     * @param email
     * @param rol
     * @return 1 => wijziging, 0 = geen wijziging uitgevoerd
     * @throws SQLException
     */
    public int setGebruikerById(int id, String voornaam, String achternaam, String email, String rol) throws SQLException {
        stmtSetGebruikerById.setString(1, voornaam);
        stmtSetGebruikerById.setString(2, achternaam);
        stmtSetGebruikerById.setString(3, email);
        stmtSetGebruikerById.setString(4, rol);
        stmtSetGebruikerById.setInt(5, id);
        return stmtSetWachtwoordHash.executeUpdate();
    }
}
