package plantenApp.java.dao;

import plantenApp.java.model.Beheer;
import plantenApp.java.model.Beheerdaad_Eigenschap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**@author Siebe*/
public class BeheerDAO implements Queries {
    private Connection dbConnection;
    private PreparedStatement stmtSelectBeheerByID;
    private PreparedStatement stmtSelectByBeheer;

    public BeheerDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        stmtSelectBeheerByID = dbConnection.prepareStatement(GETBEHEERBYPLANTID);
        stmtSelectByBeheer = dbConnection.prepareStatement(GETIDSBYBEHEER);
    }

    /**@author Siebe
     * @param id -> plant_id
     * @return -> beheer van de specifieke plant
     */
    public Beheer getById(int id) throws SQLException {
        Beheer beheer = null;

        beheer = new Beheer(
                id,
                getBeheerdaden(id)
        );
        return beheer;
    }

    /**@author Siebe
     * word alleen gebruikt in getById
     * @param id -> plant_id
     * @return -> alle beheerdaden van de specifieke plant
     */
    private ArrayList<Beheerdaad_Eigenschap> getBeheerdaden(int id) throws SQLException {
        ArrayList<Beheerdaad_Eigenschap> abioMulti = new ArrayList<>();;

        stmtSelectBeheerByID.setInt(1, id);
        ResultSet rs = stmtSelectBeheerByID.executeQuery();
        while (rs.next()) {
            Beheerdaad_Eigenschap beheerdaad = new Beheerdaad_Eigenschap(
                    rs.getInt("beheer_id"),
                    rs.getString("beheerdaad"),
                    rs.getString("opmerking"),
                    rs.getString("maand"),
                    rs.getInt("frequentie_jaar")
            );
            abioMulti.add(beheerdaad);
        }
        return abioMulti;
    }

    /**@author Siebe
     * @param sPlant_ids -> de te filteren ids
     * @param beheer -> naam van de behandeling om op te filteren
     * @param maand -> maand van de behandeling om op te filteren
     * @param frequentie -> frequentie om op te filteren
     * @return -> de gefilterde ids
     */
    public ArrayList<Integer> KenmerkenFilter(String sPlant_ids, String beheer, String maand, int frequentie) throws SQLException {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        stmtSelectByBeheer.setString(1, sPlant_ids);

        int iTrue = (beheer.isBlank()) ? 1 : 0;
        stmtSelectByBeheer.setString(2, beheer);
        stmtSelectByBeheer.setInt(3, iTrue);

        iTrue = (maand.isBlank()) ? 1 : 0;
        stmtSelectByBeheer.setString(4, maand);
        stmtSelectByBeheer.setInt(5, iTrue);

        iTrue = (frequentie == 0) ? 1 : 0;
        stmtSelectByBeheer.setInt(6, frequentie);
        stmtSelectByBeheer.setInt(7, iTrue);

        ResultSet rs = stmtSelectByBeheer.executeQuery();
        while (rs.next()) {
            ids.add(rs.getInt("plant_id"));
        }
        return ids;
    }
}
