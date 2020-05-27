package plantenApp.java.dao;

import plantenApp.java.model.CommMulti_Eigenschap;
import plantenApp.java.model.Commensalisme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**@author Siebe*/
public class CommensalismeDAO implements Queries {
    private Connection dbConnection;
    private PreparedStatement stmtSelectCommeByID;
    private PreparedStatement stmtSelectCommeMultiByID;
    private PreparedStatement stmtSelectIdsByComm;
    private PreparedStatement stmtSelectIdsByCommMulti;

    public CommensalismeDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        stmtSelectCommeByID = dbConnection.prepareStatement(GETCOMMENSALISMEBYPLANTID);
        stmtSelectCommeMultiByID = dbConnection.prepareStatement(GETCOMMENSALISMEMULTIBYPLANTID);
        stmtSelectIdsByComm = dbConnection.prepareStatement(GETIDSBYCOMM);
        stmtSelectIdsByCommMulti = dbConnection.prepareStatement(GETIDSBYCOMMMULTI);
    }

    /**@author Siebe
     * @param id -> plant_id
     * @return alle abiotische factoren van de specifieke plant
     */
    public Commensalisme getById(int id) throws SQLException {
        Commensalisme comm = null;

        stmtSelectCommeByID.setInt(1, id);
        ResultSet rs = stmtSelectCommeByID.executeQuery();
        if (rs.next()) {
            comm = new Commensalisme(
                    rs.getInt("commensialisme_id"),
                    rs.getInt("plant_id"),
                    rs.getString("strategie"),
                    rs.getString("ontwikkelingssnelheid"),
                    getByIdMulti(id)
            );
        }
        return comm;
    }

    /**@author Siebe
     * word alleen gebruikt in getById
     * @param id -> plant_id
     * @return -> alle commensalisme_multi van de specifieke plant
     */
    private ArrayList<CommMulti_Eigenschap> getByIdMulti(int id) throws SQLException {
        ArrayList<CommMulti_Eigenschap> commMulti = new ArrayList<>();;

        stmtSelectCommeMultiByID.setInt(1, id);
        ResultSet rs = stmtSelectCommeMultiByID.executeQuery();
        while (rs.next()) {
            CommMulti_Eigenschap commEigenschap = new CommMulti_Eigenschap(
                    rs.getInt("commensialisme_id"),
                    rs.getString("eigenschap"),
                    rs.getString("waarde")
            );
            commMulti.add(commEigenschap);
        }
        return commMulti;
    }

    /**@author Siebe
     * @param sPlant_ids -> de te filteren ids
     * @param eigenschap -> de naam van de eigenschap om op te filteren
     * @param waarde -> de waarde van de eigenschap
     * @return -> de gefilterde ids
     */
    public ArrayList<Integer> KenmerkenMultiFilter(String sPlant_ids,String eigenschap,String waarde) throws SQLException {
        ArrayList<Integer> ids = new ArrayList<>();;
        stmtSelectIdsByCommMulti.setString(1,sPlant_ids);
        stmtSelectIdsByCommMulti.setString(2,eigenschap);

        int iTrue = (waarde.isBlank()) ? 1 : 0;
        stmtSelectIdsByCommMulti.setString(2, waarde);
        stmtSelectIdsByCommMulti.setInt(3, iTrue);

        ResultSet rs = stmtSelectIdsByCommMulti.executeQuery();
        while (rs.next()) {
            ids.add(rs.getInt("plant_id"));
        }
        return ids;
    }

    /**@author Siebe
     * @param sPlant_ids -> de te filteren ids
     * @param strategie -> waarde van stratergie om op te filteren
     * @param ontwikkelingssnelheid -> waarde van ontwikkelingssnelheid om op te filteren
     * @return -> de gefilterde ids
     */
    public ArrayList<Integer> KenmerkenFilter(String sPlant_ids, String strategie, String ontwikkelingssnelheid) throws SQLException {
        ArrayList<Integer> ids = new ArrayList<>();;

        stmtSelectIdsByComm.setString(1, sPlant_ids);

        int iTrue = (strategie.isBlank()) ? 1 : 0;
        stmtSelectIdsByComm.setString(2, strategie);
        stmtSelectIdsByComm.setInt(3, iTrue);

        iTrue = (ontwikkelingssnelheid.isBlank()) ? 1 : 0;
        stmtSelectIdsByComm.setString(4, ontwikkelingssnelheid);
        stmtSelectIdsByComm.setInt(5, iTrue);

        ResultSet rs = stmtSelectIdsByComm.executeQuery();
        while (rs.next()) {
            ids.add(rs.getInt("plant_id"));
        }
        return ids;
    }
}

