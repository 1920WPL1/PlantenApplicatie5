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
    private PreparedStatement stmtSetWachtwoordHash;

    public GebruikerDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        stmtSelectGebruikerByEmail = dbConnection.prepareStatement(GETGEBRUIKERBYEMAILADRES);
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
                                rs.getBoolean("aanvraag_goedgekeurd"),
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
                    rs.getBoolean("aanvraag_goedgekeurd"),
                    rs.getBoolean("geregistreerd"),
                    rs.getBytes("wachtwoord_hash")
            );
        }
        return user;
    }

    public void setWachtWoordHash(int id, byte[] hash) throws SQLException {
        stmtSetWachtwoordHash.setBytes(1, hash);
        stmtSetWachtwoordHash.setInt(2, id);
        stmtSetWachtwoordHash.executeQuery();
    }
}
