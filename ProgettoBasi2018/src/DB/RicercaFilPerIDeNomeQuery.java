package DB;

import Entity.Filamento;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RicercaFilPerIDeNomeQuery {
    private static DataSource dataSource;
    public RicercaFilPerIDeNomeQuery()  {
        dataSource = new DataSource();
    }

    public static Filamento getCentroideAndEstensione(int idfil) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        //DataSource dataSource = new DataSource();
        Filamento fil = new Filamento();
        double p1;
        double p2;
        String name;
        final String query_name = "SELECT \"NAME\" as name FROM filamenti WHERE \"IDFIL\" = '" + idfil + "'";
        final String query_latCentr = "SELECT AVG(\"GLAT_CONT\") as media_lat FROM punti_contorni_filamenti WHERE \"IDFIL\" = '" + idfil + "'";
        final String query_longCentr = "SELECT AVG(\"GLON_CONT\") as media_lon FROM punti_contorni_filamenti WHERE \"IDFIL\" = '" + idfil + "'";
        final String query_maxLat = "SELECT MAX(\"GLAT_CONT\") as max_lat FROM punti_contorni_filamenti WHERE \"IDFIL\" = '" + idfil + "'";
        final String query_maxLong = "SELECT MAX(\"GLON_CONT\") as max_lon FROM punti_contorni_filamenti WHERE \"IDFIL\" = '" + idfil + "'";
        final String query_minLat = "SELECT MIN(\"GLAT_CONT\") as min_lat FROM punti_contorni_filamenti WHERE \"IDFIL\" = '" + idfil + "'";
        final String query_minLong = "SELECT MIN(\"GLON_CONT\") as min_lon FROM punti_contorni_filamenti WHERE \"IDFIL\" = '" + idfil + "'";
        final String query_numeroSegmenti = "SELECT COUNT(\"IDBRANCH\") as numeroSegmenti FROM punti_segmenti WHERE \"IDFIL\" = '" + idfil + "'";
        fil.setIdfil(idfil);

        try {
            connection = dataSource.getConnection();

            //Latitudine del Centroide
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query_latCentr);
            if (!rs.first()) // rs not empty
                return null;
            double latCentroide = rs.getDouble("media_lat");
            fil.setLatCentroide(latCentroide);


            //Longitudine del Centroide
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query_longCentr);
            if (!rs.first()) // rs not empty
                return null;
            double longCentroide = rs.getDouble("media_lon");
            fil.setLongCentroide(longCentroide);


            //Max Latitudine
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query_maxLat);
            if (!rs.first()) // rs not empty
                return null;
            p1 = rs.getDouble("max_lat");

            //Min Latitudine
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query_minLat);
            if (!rs.first()) // rs not empty
                return null;
            p2 = rs.getDouble("min_lat");
            //distanza
            double estensioneLat = distance(p1,p2);


            //Max Longitudine
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query_maxLong);
            if (!rs.first()) // rs not empty
                return null;
            p1 = rs.getDouble("max_lon");


            //Min Longitudine
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query_minLong);
            if (!rs.first()) // rs not empty
                return null;
            p2 = rs.getDouble("min_lon");

            //Name
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query_name);
            if (!rs.first()) // rs not empty
                return null;
            name = rs.getString("name");
            fil.setName(name);

            //Numero Segmenti
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query_numeroSegmenti);
            if (!rs.first()) // rs not empty
                return null;
            int numeroSegmenti = rs.getInt("numeroSegmenti");
            fil.setNumeroSegmenti(numeroSegmenti);

            //distanza
            double estensioneLong = distance(p1,p2);
            fil.setEstensioneLat(estensioneLat);
            fil.setEstensioneLong(estensioneLong);
            //fil = new Filamento(idfil, name, latCentroide, longCentroide, estensioneLat, estensioneLong, numeroSegmenti);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return fil;
    }

    public static int getIdFilFromName(String name){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        DataSource dataSource = new DataSource();
        int id = -1;

        final String query = "SELECT \"IDFIL\" FROM filamenti WHERE \"NAME\" = '" + name + "'";

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query);

            if(rs.first()){
                id = rs.getInt("IDFIL");
            }
            return id;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  id;
    }

    public static double distance (double p1, double p2)
    {
        double dist = Math.sqrt(Math.pow(p1 - p2, 2.0) + Math.pow(p1 - p2, 2.0));
        return dist;
    }

}
