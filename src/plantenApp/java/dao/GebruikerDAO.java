package plantenApp.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GebruikerDAO implements Queries {
    private Connection dbConnection;
//    private PreparedStatement stmtSelectGebruikerByLogin;


    public GebruikerDAO(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }
}
