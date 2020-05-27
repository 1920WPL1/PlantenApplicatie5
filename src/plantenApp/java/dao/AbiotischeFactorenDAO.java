package plantenApp.java.dao;

import plantenApp.java.model.AbioMulti_Eigenschap;
import plantenApp.java.model.AbiotischeFactoren;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**@author Siebe*/
public class AbiotischeFactorenDAO implements Queries {
    private Connection dbConnection;
    private PreparedStatement stmtSelectAbioByID;
    private PreparedStatement stmtSelectAbioMultiByID;
    private PreparedStatement stmtSelectIdsByAbio;
    private PreparedStatement stmtSelectIdsByAbioMulti;

    public AbiotischeFactorenDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        stmtSelectAbioByID = dbConnection.prepareStatement(GETABIOTISCHBYPLANTID);
        stmtSelectAbioMultiByID = dbConnection.prepareStatement(GETABIOTISCHBMULTIYPLANTID);
        stmtSelectIdsByAbio = dbConnection.prepareStatement(GETIDSBYABIO);
        stmtSelectIdsByAbioMulti = dbConnection.prepareStatement(GETIDSBYABIOMULTI);
    }

    /**@author Siebe
     * @param id -> plant_id
     * @return -> alle abiotische factoren van de specifieke plant
     */
    public AbiotischeFactoren getById(int id) throws SQLException {
        AbiotischeFactoren abio = null;

        stmtSelectAbioByID.setInt(1, id);
        ResultSet rs = stmtSelectAbioByID.executeQuery();
        if (rs.next()) {
            abio = new AbiotischeFactoren(
                    rs.getInt("abiotische_id"),
                    rs.getInt("plant_id"),
                    rs.getString("bezonning"),
                    rs.getString("grondsoort"),
                    rs.getString("vochtbehoefte"),
                    rs.getString("voedingsbehoefte"),
                    rs.getString("reactie_antagonistische_omg"),
                    getByIdMulti(id)
            );
        }
        return abio;
    }

    /**@author Siebe
     * word alleen gebruikt in getById
     * @param id -> plant_id
     * @return -> alle abiotische_multi factoren van de specifieke plant
     */
    private ArrayList<AbioMulti_Eigenschap> getByIdMulti(int id) throws SQLException {
        ArrayList<AbioMulti_Eigenschap> abioMulti = new ArrayList<>();;

        stmtSelectAbioMultiByID.setInt(1, id);
        ResultSet rs = stmtSelectAbioMultiByID.executeQuery();
        while (rs.next()) {
            AbioMulti_Eigenschap abioEigenschap = new AbioMulti_Eigenschap(
                    rs.getInt("abiotische_id"),
                    rs.getString("eigenschap"),
                    rs.getString("waarde")
            );
            abioMulti.add(abioEigenschap);
        }
        return abioMulti;
    }

    /**@author Siebe
     * @param sPlant_ids -> de te filteren ids
     * @param eigenschap -> de naam van de eigenschap om op te filteren
     * @param waarde -> de waarde van de eigenschap
     * @return -> de gefilterde ids
     */
    public ArrayList<Integer> KenmerkenMultiFilter(String sPlant_ids,String eigenschap,String waarde) throws SQLException {
        ArrayList<Integer> ids = new ArrayList<>();;
        stmtSelectIdsByAbioMulti.setString(1,sPlant_ids);
        stmtSelectIdsByAbioMulti.setString(2,eigenschap);

        int iTrue = (waarde.isBlank()) ? 1 : 0;
        stmtSelectIdsByAbioMulti.setString(2, waarde);
        stmtSelectIdsByAbioMulti.setInt(3, iTrue);

        ResultSet rs = stmtSelectIdsByAbioMulti.executeQuery();
        while (rs.next()) {
            ids.add(rs.getInt("plant_id"));
        }
        return ids;
    }

    /**@author Siebe
     * @param sPlant_ids -> de te filteren ids
     * @param bezonning -> waarde van bezonning om op te filteren
     * @param grondsoort -> waarde van grondsoort om op te filteren
     * @param vochtbehoefte -> waarde van vochtbehoefte om op te filteren
     * @param voedingsbehoefte -> waarde van voedingsbehoefte om op te filteren
     * @param reactieAntagonistischeOmgeving -> waarde van reactie antagonistische omgeving om op te filteren
     * @return -> de gefilterde ids
     */
    public ArrayList<Integer> KenmerkenFilter(String sPlant_ids, String bezonning, String grondsoort, String vochtbehoefte, String voedingsbehoefte, String reactieAntagonistischeOmgeving) throws SQLException {
        ArrayList<Integer> ids = new ArrayList<>();;

        stmtSelectIdsByAbio.setString(1, sPlant_ids);

        int iTrue = (bezonning.isBlank()) ? 1 : 0;
        stmtSelectIdsByAbio.setString(2, bezonning);
        stmtSelectIdsByAbio.setInt(3, iTrue);

        iTrue = (grondsoort.isBlank()) ? 1 : 0;
        stmtSelectIdsByAbio.setString(4, grondsoort);
        stmtSelectIdsByAbio.setInt(5, iTrue);

        iTrue = (vochtbehoefte.isBlank()) ? 1 : 0;
        stmtSelectIdsByAbio.setString(6, vochtbehoefte);
        stmtSelectIdsByAbio.setInt(7, iTrue);

        iTrue = (voedingsbehoefte.isBlank()) ? 1 : 0;
        stmtSelectIdsByAbio.setString(8, voedingsbehoefte);
        stmtSelectIdsByAbio.setInt(9, iTrue);

        iTrue = (reactieAntagonistischeOmgeving.isBlank()) ? 1 : 0;
        stmtSelectIdsByAbio.setString(10, reactieAntagonistischeOmgeving);
        stmtSelectIdsByAbio.setInt(11, iTrue);

        ResultSet rs = stmtSelectIdsByAbio.executeQuery();
        while (rs.next()) {
            ids.add(rs.getInt("plant_id"));
        }
        return ids;
    }
}
