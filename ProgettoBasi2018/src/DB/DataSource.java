package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private String dbURL = "jdbc:postgresql://localhost:5432/progettoBD";
    private String user = "postgres";
    private String password = "maurof";

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(dbURL,user, password);
        return connection;
    }

}
