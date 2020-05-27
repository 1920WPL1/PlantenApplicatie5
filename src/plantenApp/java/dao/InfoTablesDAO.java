package plantenApp.java.dao;

import plantenApp.java.model.InfoTables;

import java.sql.*;
import java.util.ArrayList;

/**@author Siebe*/
public class InfoTablesDAO implements Queries {
    private Connection dbConnection;

    public InfoTablesDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
    }

    /**
     * @param Query
     * @return
     */
    public ArrayList<String> getInfoTableString(String Query, String eigenschapnaam) throws SQLException{
        ArrayList<String> strings = new ArrayList<>();;

            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Query);
            while (rs.next()) {
                strings.add(rs.getString(eigenschapnaam));


            }
       // System.out.println(strings.toString());
        return strings;
    }

    /**
     * @param Query
     * @return
     */
    public ArrayList<Integer> getInfoTableInt(String Query, String eigenschapnaam) throws SQLException {
        ArrayList<Integer> ints = new ArrayList<>();;

        Statement stmt = dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery(Query);
        while (rs.next()) {
            ints.add(rs.getInt(eigenschapnaam));
        }
        return ints;
    }

    /**
     * @param Query
     * @return
     */
    public ArrayList<Blob> getInfoTableBlob(String Query,String eigenschapnaam) throws SQLException {
        ArrayList<Blob> blobs = new ArrayList<>();

        Statement stmt = dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery(Query);
        while (rs.next()) {
            blobs.add(rs.getBlob(eigenschapnaam));
        }
        return blobs;
    }

    // functie om de String Query in te vullen bij 'getInfoTable'
  public InfoTables getInfoTables() throws SQLException{
        InfoTables infoTables = new InfoTables(
                getInfoTableString(NTTYPE, "type_naam"),
                getInfoTableString(NTFAMILIE, "familie_naam"),
                getInfoTableString(NTKLEUREN, "kleur"),
                getInfoTableString(NTBLADGROOTTE, "waarde"),
                getInfoTableString(NTBLADVORM, "waarde"),
                getInfoTableString(NTRATIOBLOEIBLAD, "waarde"),
                getInfoTableString(NTSPRUITFENOLOGIE, "waarde"),
                getInfoTableString(NTBLOEIWIJZE, "waarde"),
                //getInfoTableBlob(GETFOTOBlOEIWIJZE, "afbeelding"),
                getInfoTableString(NTHABITUS, "waarde"),
                getInfoTableBlob(NTFOTOHABITUS, "afbeelding"),
                //getInfoTableString(GETLEVENSVORM, "waarde"),
                getInfoTableString(NTBEZONNING, "waarde"),
                getInfoTableString(NTGRONDSOORT, "waarde"),
                getInfoTableString(NTVOCHTBEHOEFTE, "waarde"),
                getInfoTableString(NTVOEDINGSBEHOEFTE, "waarde"),
                getInfoTableString(NTREACTIEOMGEVING, "waarde"),
                getInfoTableString(NTHABITAT, "waarde"),
                getInfoTableString(NTONTWIKKELINGSSNELHEID, "waarde"),
                getInfoTableString(NTLEVENSDUURCONCURRENTIEKRACHT, "waarde"),
                getInfoTableInt(NTSOCIABILITEIT, "waarde"),
                getInfoTableString(NTSTRATEGIE, "waarde"),
                getInfoTableString(NTBEHEERDAAD,"waarde"),
                getInfoTableInt(NTNECTARWAARDE, "waarde"),
                getInfoTableInt(NTPOLLENWAARDE, "waarde")
        );
        return infoTables;
    }
}
