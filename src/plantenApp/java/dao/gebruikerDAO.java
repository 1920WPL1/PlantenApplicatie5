package plantenApp.java.dao;

import java.sql.Connection;

public class gebruikerDAO implements Queries {
    private Connection dbConnection;
    private PreparedStatement stmtSelectGebruikerByGebruikerId;

    public gebruikerDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        stmtSelectGebruikerById = dbConnection.prepareStatement(GETGEBRUIKERBYGEBRUIKERID);


    }

    public gebruiker getById(int id) throws SQLException {
        gebruiker gebr = null;

        stmtSelectGebruikerByGebruikerId.setInt(1, id);
        ResultSet rs = stmtSelectGebruikerByGebruikerId.executeQuery();
        if (rs.next()) {
            abio = new gebruiker(
                    rs.getInt("gebruiker_id"),
                    rs.getString("voornaam"),
                    rs.getString("achternaam"),
                    rs.getString("email"),
                    rs.getString("rol"),
                    rs.getInt("aanvraag_datum"),
                    rs.getInt("aanvraag_goedgekeurd"),
                    rs.getString("wachtwoord_hash"),
                    getByGebruikerId(id)
            );
        }
        return gebr;
    }

    private ArrayList<gebruiker_id> getByGebruikerId(int id) throws SQLException {
        ArrayList<gebruiker_id> gebruiker = new ArrayList<>();;

        stmtSelectGebruikerByGebruikerId.setInt(1, id);
        ResultSet rs = stmtSelectGebruikerByGebruikerId.executeQuery();
        while (rs.next()) {
            gebruiker_id gebruiker = new gebruiker_id(
                    rs.getInt("gebruiker_id"),
                    rs.getString("voornaam"),
                    rs.getString("achternaam")
            );
            gebruker.add(gebruiker_id);
        }
        return gebruiker;
    }







}

