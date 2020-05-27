package DB;


import Entity.PuntiSegmenti;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CalcoloDistanzaStelleSpinaDorsaleQuery {
    private DataSource dataSource;

    public CalcoloDistanzaStelleSpinaDorsaleQuery(){
        dataSource = new DataSource();
    }

    public ArrayList<PuntiSegmenti> getPuntiBranch(int idfil) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String type = "S";
        ArrayList<PuntiSegmenti> puntiSegmenti = new ArrayList<>();

        try {
            connection = this.dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            final String query = "SELECT * FROM punti_segmenti as ps JOIN segmenti as s" +
                    "ON ps.\"IDFIL\" = s.\"IDFIL\" WHERE s.\"IDFIL\" = '" + idfil + "' AND \"TYPE\" = '" + type + "';";

            rs = statement.executeQuery(query);
            while (rs.next()) {
                PuntiSegmenti p = new PuntiSegmenti(rs.getDouble("GLON_BR"), rs.getDouble("GLAT_BR"));
                puntiSegmenti.add(p);
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
        return puntiSegmenti;
    }

}







