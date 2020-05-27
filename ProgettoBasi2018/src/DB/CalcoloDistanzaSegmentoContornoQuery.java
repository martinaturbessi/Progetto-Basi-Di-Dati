package DB;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CalcoloDistanzaSegmentoContornoQuery {
    private static DataSource dataSource;

    public CalcoloDistanzaSegmentoContornoQuery() {
        dataSource = new DataSource();
    }

    public double calcoloDistanzaMinima(int idbranch) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        //Query per calcolare la distanza tra l'estremo minimo del vertice e il contorno
        final String minV = "SELECT MIN(SQRT(POW(((\"GLON_CONT\") - (\"GLON_BR\")), 2) + POW(((\"GLAT_CONT\") - (\"GLAT_BR\")), 2))) as distance1\n" +
                "FROM punti_segmenti AS segm JOIN punti_contorni_filamenti as cont ON segm.\"IDFIL\" = cont.\"IDFIL\"\n" +
                "WHERE \"N\" = ANY (SELECT MIN(\"N\") FROM punti_segmenti) AND \"IDBRANCH\" = '" + idbranch + "'";

        double distanzaMinima = 0;

        try {
            connection = this.dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            rs = statement.executeQuery(minV);

            while (rs.next()) {
                distanzaMinima = rs.getDouble("distance1");
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

        return distanzaMinima;
    }

    public double calcoloDistanzaMassima(int idbranch) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        //Query per calcolare la distanza tra l'estremo massimo del vertice e il contorno
        final String maxV = "SELECT MIN(SQRT(POW(((\"GLON_CONT\") - (\"GLON_BR\")), 2) + POW(((\"GLAT_CONT\") - (\"GLAT_BR\")), 2))) as distance2\n" +
                "FROM punti_segmenti AS segm JOIN punti_contorni_filamenti as cont ON segm.\"IDFIL\" = cont.\"IDFIL\"\n" +
                "WHERE \"N\" = ANY (SELECT MAX(\"N\") FROM punti_segmenti WHERE \"IDBRANCH\" = '" + idbranch + "') AND \"IDBRANCH\" = '" + idbranch + "'";
        double distanzaMassima = 0;

        try {
            connection = this.dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            rs = statement.executeQuery(maxV);
            while (rs.next()) {
                distanzaMassima = rs.getDouble("distance2");
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

        return distanzaMassima;
    }

    public boolean findSegmento(int idbranch) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            final String query = "SELECT * FROM punti_segmenti WHERE \"IDBRANCH\" = '"+ idbranch +"'";

            result = statement.executeQuery(query);
            if (result.next()) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            // release resources
            if(result != null){
                result.close();
            }
            // release resources
            if(statement != null){
                statement.close();
            }
            if(connection  != null){
                connection.close();
            }
        }
        return false;
    }

}