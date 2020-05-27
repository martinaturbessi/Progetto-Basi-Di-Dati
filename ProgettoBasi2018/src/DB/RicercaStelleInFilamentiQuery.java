package DB;

import Entity.PuntoContorno;
import Entity.Stella;

import java.sql.*;
import java.util.ArrayList;

public class RicercaStelleInFilamentiQuery {
    private DataSource dataSource;

    public RicercaStelleInFilamentiQuery() {
        dataSource = new DataSource();
    }

    public ArrayList<PuntoContorno> posizioneCont(int idfil) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<PuntoContorno> contorno = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            final String query = "SELECT DISTINCT ON (\"GLON_CONT\", \"GLAT_CONT\") * FROM punti_contorni_filamenti WHERE \"IDFIL\" = '"+ idfil +"' ORDER BY \"GLON_CONT\", \"GLAT_CONT\"";
            rs = statement.executeQuery(query);
            while (rs.next()) {
                PuntoContorno p = new PuntoContorno(rs.getDouble("GLON_CONT"), rs.getDouble("GLAT_CONT"));
                contorno.add(p);
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
        return contorno;
    }

    public ArrayList<Stella> LoadAllStelle() throws SQLException {
        ArrayList<Stella> stelle = new ArrayList<Stella>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            final String query = "SELECT * FROM stelle";
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Stella s = new Stella(rs.getInt("IDSTAR"), rs.getString("NAMESTAR"), rs.getDouble("GLON_ST"),
                        rs.getDouble("GLAT_ST"), rs.getDouble("FLUX_ST"), rs.getString("TYPE_ST"));
                stelle.add(s);
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
        return stelle;
    }

    public ArrayList<Stella> FindStelleInFilamento(int idfil) throws SQLException {
        ArrayList<Stella> stelle = LoadAllStelle();
        ArrayList<Stella> stelleInFil = new ArrayList<>();
        ArrayList<PuntoContorno> posizioniContorno;
        //stelle = LoadAllStelle();
        posizioniContorno = posizioneCont(idfil);
        if (posizioniContorno.size() == 0) {
            return stelle;
        }
        for (int i = 0; i <= stelle.size() - 1; i++) {
            Stella stella = stelle.get(i);
            double sum = 0;
            for (int j = 0; j <= posizioniContorno.size() - 2; j++) {
                PuntoContorno pos0 = posizioniContorno.get(j);
                PuntoContorno pos1 = posizioniContorno.get(j + 1);
                double num = (pos0.getLongitudine() - stella.getLongi())
                        * (pos1.getLatitudine() - stella.getLat())
                        - (pos0.getLatitudine() - stella.getLat())
                        * (pos1.getLongitudine() - stella.getLongi());
                double den = (pos0.getLongitudine() - stella.getLongi())
                        * (pos1.getLongitudine() - stella.getLongi())
                        + (pos0.getLatitudine() - stella.getLat())
                        * (pos1.getLatitudine() - stella.getLat());
                double arctan = (double) Math.atan(num / den);
                sum += arctan;
            }
            if (Math.abs(sum) >= 0.01) {
                stelleInFil.add(stella);
                //InclusioneQuery.updateInclusione(stelle.get(i).getIdstar(), idfil);
            }
        }
        return stelleInFil;
    }

    public boolean findFilamento(int idfil) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            final String query = "SELECT * FROM punti_contorni_filamenti WHERE \"IDFIL\" = '"+ idfil +"'";

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
