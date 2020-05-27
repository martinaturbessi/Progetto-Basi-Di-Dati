package DB;

import Entity.PuntoContorno;
import Entity.Stella;

import java.sql.*;
import java.util.ArrayList;

public class RicercaStelleInRettangoloQuery {

    /*
        Metodo che restituisce tutte le stelle che sono all'interno di un Rettangolo
     */
    public static ArrayList<Stella> searchInRectangle(double lat_centroide, double lon_centroide, double lato_lat, double lato_long){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        DataSource dataSource = new DataSource();

        ArrayList<Stella> stelle = new ArrayList<Stella>();


        String expression_lat = "  \"GLAT_ST\" >= " + (lat_centroide - lato_lat/2) + " AND \"GLAT_ST\" <= " + (lat_centroide + lato_lat/2) + " ";
        String expression_lon = "  \"GLON_ST\" >= " + (lon_centroide - lato_long/2) + " AND \"GLON_ST\" <= " + (lon_centroide + lato_long/2) + " ";

        String query = "SELECT * FROM stelle WHERE (" + expression_lat + ") = TRUE AND (" + expression_lon + ") = TRUE ;";

        System.out.println(query);

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query);

            while (rs.next()){
                int id = rs.getInt("IDSTAR");
                double st_lat = rs.getDouble("GLAT_ST");
                double st_long = rs.getDouble("GLON_ST");
                String type = rs.getString("TYPE_ST");
                stelle.add(new Stella(id, st_lat, type, st_long));
            }

            rs.close();
            statement.close();
            connection.close();

        }catch(SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stelle;
    }


    public static ArrayList<Integer> getIdFilInRectangle(double lat_centroide, double lon_centroide, double lato_lat, double lato_long){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        DataSource dataSource = new DataSource();

        ArrayList<Integer> result = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String expression_lat = "  \"GLAT_CONT\" >= " + (lat_centroide - lato_lat/2) + " AND \"GLAT_CONT\" <= " + (lat_centroide + lato_lat/2) + " ";
            String expression_lon = "  \"GLON_CONT\" >= " + (lon_centroide - lato_long/2) + " AND \"GLON_CONT\" <= " + (lon_centroide + lato_long/2) + " ";



            //final String query = "SELECT DISTINCT \"IDFIL\" FROM punti_contorno WHERE (" + expression_lat + ") = TRUE AND (" + expression_lon + ") = TRUE ;";
            final String query = "SELECT DISTINCT \"IDFIL\" FROM punti_contorni_filamenti WHERE (" + expression_lat + ") AND (" + expression_lon + ") ;";

            //System.out.println(query);
            rs = statement.executeQuery(query);

            while(rs.next()) {
                result.add(rs.getInt("IDFIL"));
            }
            rs.close();
            statement.close();
            connection.close();

        }catch(SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    static public ArrayList<PuntoContorno> getPuntiByIdFil(int id) {

        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        DataSource dataSource = new DataSource();
        ArrayList<PuntoContorno> punti = new ArrayList<PuntoContorno>();
        PuntoContorno punto;
        double latitudine;
        double longitudine;

        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        //String query = "SELECT \"GLON_CONT\", \"GLAT_CONT\" FROM punti_contorno WHERE \"IDFIL\" = '" + id + "' ORDER BY \"N\"";
            String query = "SELECT \"GLON_CONT\", \"GLAT_CONT\" FROM punti_contorni_filamenti WHERE \"IDFIL\" = '" + id + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                latitudine = rs.getDouble("GLAT_CONT");
                longitudine = rs.getDouble("GLON_CONT");
                punto = new PuntoContorno(id, latitudine, longitudine);
                punti.add(punto);
            }
            rs.close();
            stmt.close();
            conn.close();

        }catch(SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return punti;
    }


}
