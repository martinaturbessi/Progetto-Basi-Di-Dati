package DB;

import Entity.PuntoContorno;

import java.sql.*;
import java.util.ArrayList;

public class RicercaFilInRegioneQuery {
    private DataSource dataSource;

    public RicercaFilInRegioneQuery() {
        dataSource = new DataSource();
    }

    public ArrayList<PuntoContorno> ricercaFilamentiCerchio(double lat, double longi, double raggio) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<PuntoContorno> fil = new ArrayList<>();

        try {
            connection = this.dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            final String query = "SELECT (\"IDFIL\") as idfil, SQRT(POW(( AVG(\"GLON_CONT\") - (" + longi + ")), 2) + POW(( AVG(\"GLAT_CONT\") - (" + lat + ")), 2)) as distance FROM punti_contorni_filamenti GROUP BY idfil ORDER BY idfil ASC";

            rs = statement.executeQuery(query);
            while (rs.next()) {
                PuntoContorno id = new PuntoContorno(rs.getInt("idfil"));
                double dist = rs.getDouble("distance");
                if (dist < raggio) {
                    id.setIdfil(rs.getInt("idfil"));
                    fil.add(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // release resources
            if (rs != null) {
                rs.close();
            }
            // release resources
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return fil;
    }

    public ArrayList<PuntoContorno> ricercaFilamentiQuadrato(double lat, double longi, double lato) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<PuntoContorno> fil = new ArrayList<>();


        try {
            connection = this.dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            final String query = "SELECT (\"IDFIL\") as idfil, SQRT(POW(( AVG(\"GLON_CONT\") - (" + lat + ")), 2) + POW(( AVG(\"GLAT_CONT\") - (" + longi + ")), 2)) as distance FROM punti_contorni_filamenti GROUP BY idfil ORDER BY idfil ASC";

            rs = statement.executeQuery(query);
            while (rs.next()) {
                PuntoContorno id = new PuntoContorno(rs.getInt("idfil"));
                double dist = rs.getDouble("distance");
                if (dist < lato/2) {
                    id.setIdfil(rs.getInt("idfil"));
                    fil.add(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // release resources
            if (rs != null) {
                rs.close();
            }
            // release resources
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return fil;
    }

}
