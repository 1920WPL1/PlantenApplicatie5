package plantenApp.java.dao;

import plantenApp.java.model.Foto;
import plantenApp.java.model.Foto_Eigenschap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**@author Siebe*/
public class FotoDAO implements Queries {
    private Connection dbConnection;
    private PreparedStatement stmtSelectFotoByID;

    public FotoDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        stmtSelectFotoByID = dbConnection.prepareStatement(GETFOTOBYPLANTID);
    }

    /**@author Siebe
     * @param id -> plant_id
     * @return -> verzameling van de fotos van de specifieke plant
     */
    public Foto getFotoById(int id) throws SQLException {
        Foto foto = null;

        foto = new Foto(
                id,
                getFotos(id)
        );
        return foto;
    }

    /**@author Siebe
     * @param id -> plant_id
     * @return -> fotos van de specifieke plant
     */
    private ArrayList<Foto_Eigenschap> getFotos(int id) throws SQLException {
        ArrayList<Foto_Eigenschap> fotos = null;

        stmtSelectFotoByID.setInt(1, id);
        ResultSet rs = stmtSelectFotoByID.executeQuery();
        while (rs.next()) {
            Foto_Eigenschap foto = new Foto_Eigenschap(
                    rs.getInt("foto_id"),
                    rs.getString("eigenschap"),
                    rs.getString("url"),
                    rs.getBlob("figuur")
            );
            fotos.add(foto);
        }
        return fotos;
    }
}
