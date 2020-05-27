package DB;

import Entity.Strumento;

import java.sql.*;

public class StrumentoQuery {
    private DataSource dataSource;

    public StrumentoQuery() {
        dataSource = new DataSource();
    }

    public Strumento aggiungiStrumentoBanda(String strumento, String satellite, double banda) throws SQLException {
        Strumento s;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        String insertStrumento = "INSERT INTO strumento (\"NOME\", \"NOME_SAT\", \"WAVE_LENGTH\") values (?,?, ?)";

        try {
            connection = this.dataSource.getConnection();

            statement = connection.prepareStatement(insertStrumento);
            statement.setString(1, strumento);
            statement.setString(2, satellite);
            statement.setDouble(3,banda);
            statement.executeUpdate();
            s = new Strumento(strumento, satellite, banda);
            return s;

        } catch(Exception e){
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


    public boolean findStrumentoBanda(String strumento, String satellite, double banda) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        final String query = "SELECT * FROM strumento WHERE \"NOME\" = ? AND \"NOME_SAT\" = ? AND \"WAVE_LENGTH\" = ?";

        try{
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, strumento);
            statement.setString(2, satellite);
            statement.setDouble(3, banda);
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
}
