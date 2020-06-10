package plantenApp.java.dao;
import plantenApp.java.model.Gebruiker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**@author Bart Maes*/
public class GebruikerDAO implements Queries {
    private static final String GETGEBRUIKERBYID = "SELECT * FROM gebruiker WHERE gebruiker_id = ?";
    private Connection dbConnection;
    private PreparedStatement stmtSelectGebruikerByEmail;
    private PreparedStatement stmtSelectGebruikerById;
    /** @Author Jasper */
    private PreparedStatement stmtSelectGebruikersByFullName;
    private PreparedStatement stmtSetGebruikerById;
    private PreparedStatement stmtSetWachtwoordHash;
    private PreparedStatement stmtDeleteGebruikerById;

    public GebruikerDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        stmtSelectGebruikerById = dbConnection.prepareStatement(GETGEBRUIKERBYID);

        stmtSelectGebruikerByEmail = dbConnection.prepareStatement(GETGEBRUIKERBYEMAILADRES);
        stmtSelectGebruikersByFullName = dbConnection.prepareStatement(GETGEBRUIKERSBYFULLNAME);
        stmtSetGebruikerById = dbConnection.prepareStatement(SETGEBRUIKERBYID);
        stmtSetWachtwoordHash = dbConnection.prepareStatement(SETWACHTWOORDHASH);
        stmtDeleteGebruikerById = dbConnection.prepareStatement(DELETEGEBRUIKERBYID);

    }

    /**@author Bart Maes
     * @return alle gebruikers
     */
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
                                rs.getInt("aanvraag_status"),
                                rs.getInt("geregistreerd"),
                                rs.getBytes("wachtwoord_hash"),
                                rs.getBytes("salt")
                        );
                gebruikersList.add(gebruiker);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GebruikerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gebruikersList;
    }

    /**@author Bart Maes
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
                    rs.getInt("aanvraag_status"),
                    rs.getInt("geregistreerd"),
                    rs.getBytes("wachtwoord_hash"),
                    rs.getBytes("salt")
            );
        }
        return user;
    }

    /**@author Matthias Vancoillie
     * @param gebruiker_id
     * @return gebruiker_id, voornaam, achternaam, email
     */
    public Gebruiker getById(int gebruiker_id) throws SQLException {
        Gebruiker user = null;
        stmtSelectGebruikerById.setInt(1,gebruiker_id);
        ResultSet rs = stmtSelectGebruikerById.executeQuery();

        if (rs.next()) {
            user = new Gebruiker(
                    rs.getInt("gebruiker_id"),
                    rs.getString("voornaam"),
                    rs.getString("achternaam"),
                    rs.getString("email")
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
                                rs.getInt("aanvraag_status"),
                                rs.getInt("geregistreerd"),
                                rs.getBytes("wachtwoord_hash"),
                                rs.getBytes("salt")
                        );
                gebruikersList.add(gebruiker);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GebruikerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gebruikersList;
    }

    /**@author Jasper, Bart
     * @param id : id van gebruiker om nieuwe wachtwoord_hash in te stellen
     * @param hash : nieuwe wachtwoord_hash
     * @return 1 bij gewijzigd wachtwoord, 0 bij fout
     * @throws SQLException
     */
    public void setWachtWoordHash(int id, byte[] hash, byte[] salt) throws SQLException {
        stmtSetWachtwoordHash.setBytes(1, hash);
        stmtSetWachtwoordHash.setBytes(2, salt);
        stmtSetWachtwoordHash.setInt(3, id);
        //aanpassing Bart Maes:
        stmtSetWachtwoordHash.executeUpdate();
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
        //return stmtSetWachtwoordHash.executeUpdate();
        //aanpassing Bart Maes (verkeerde statement hierboven?):
        return stmtSetGebruikerById.executeUpdate();
    }

    /**@Author Jasper
     * @param id gebruiker om te verwijderen
     * @return 1 => verwijdering, 0 = geen verwijdering uitgevoerd
     * @throws SQLException
     */
    public int deleteGebruikerById(int id) throws SQLException {
        stmtDeleteGebruikerById.setInt(1, id);
        return stmtDeleteGebruikerById.executeUpdate();
    }
}