package DB;

import Entity.Satellite;

import java.sql.*;
import java.time.LocalDate;

public class SatelliteQuery {
    private DataSource dataSource;

    public SatelliteQuery() {
        dataSource = new DataSource();
    }

    public boolean findSatellite(String nomesat) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        final String query = "SELECT * FROM satellite WHERE \"NOME\" = ?";

        try{
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, nomesat);
            result = statement.executeQuery();
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


    public Satellite aggiungiSatellite(String nomesat, LocalDate primaoss, LocalDate termineop) throws SQLException {
        Satellite s;
        Connection connection = null;
        PreparedStatement statement = null;
        final String insert = "INSERT INTO satellite (\"NOME\", \"PRIMA_OSS\", \"TERMINE_OP\") values (?,?,?)";

        try{
            connection = this.dataSource.getConnection();

            statement = connection.prepareStatement(insert);
            statement.setString(1, nomesat);
            statement.setDate(2, Date.valueOf(primaoss));
            statement.setDate(3, Date.valueOf(termineop));
            statement.executeUpdate();

            s = new Satellite(nomesat, primaoss, termineop);
            return s;

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            // release resources
            if(statement != null){
                statement.close();
            }
            if(connection  != null){
                connection.close();
            }
        }
        return null;
    }
}
