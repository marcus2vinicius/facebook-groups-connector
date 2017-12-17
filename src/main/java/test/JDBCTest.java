package test;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by NTI-Sistema on 24/08/2015.
 */
public class JDBCTest {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgres://dasdasdas:gdfgdfgdfgdf-S@ec2-54-235-147-211.compute-1.amazonaws.com:5432/756fggfhgf";
    static final String PASS = "534534534534-S";
    static final String USER = "534534543fggdf";
    public static void main(String[] args) throws ClassNotFoundException, SQLException, URISyntaxException {
        Class.forName(JDBC_DRIVER);
        /*Connection con = DriverManager.getConnection(DB_URL, USER, PASS);*/
        getConnection();
    }

    private static Connection getConnection() throws URISyntaxException, SQLException {
        //Coloque sua url connection
        URI dbUri = new URI("postgres://ababababab:url.com:5432/database");

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl+"?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", username, password);
    }
}
