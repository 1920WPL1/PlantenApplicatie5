package plantenApp.java.dao;
import plantenApp.java.model.Gebruiker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**@author Bart Maes*/
public class GebruikerDAO implements Queries {
    private Connection dbConnection;
    private PreparedStatement stmtSelectGebruikerByEmail;
    private PreparedStatement stmtSetWachtwoordHash;

    public GebruikerDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        stmtSelectGebruikerByEmail = dbConnection.prepareStatement(GETGEBRUIKERBYEMAILADRES);
        stmtSetWachtwoordHash = dbConnection.prepareStatement(SETWACHTWOORDHASH);
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
                                rs.getInt("aanvraag_goedgekeurd"),
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
                    rs.getInt("aanvraag_goedgekeurd"),
                    rs.getInt("geregistreerd"),
                    rs.getBytes("wachtwoord_hash"),
                    rs.getBytes("salt")
            );
        }
        return user;
    }

    public void setWachtWoordHash(int id, byte[] hash, byte[] salt) throws SQLException {
        stmtSetWachtwoordHash.setBytes(1, hash);
        stmtSetWachtwoordHash.setBytes(2, salt);
        stmtSetWachtwoordHash.setInt(3, id);
        //stmtSetWachtwoordHash.executeQuery();
        //aanpassing Bart Maes:
        stmtSetWachtwoordHash.executeUpdate();
    }
}
