package DB;

import Entity.Filamento;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RicercaPerContrastoEllitticitaQuery {
	private static DataSource dataSource;

	public RicercaPerContrastoEllitticitaQuery() {
		dataSource = new DataSource();
	}


	public ArrayList<Filamento> getNumerofilamenti(double brillanza, int min, int max) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		ArrayList<Filamento> numeroFilamenti = new ArrayList<>();
		Double contrast = 1+(brillanza/100);

		try{
			connection = this.dataSource.getConnection();
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			final String query_idFil = "SELECT (\"IDFIL\") as idfil FROM filamenti WHERE \"CONTRAST\" > '" + contrast + "' AND \"ELLIPTICITY\" >= '" + min + "' AND \"ELLIPTICITY\" <= '" + max + "'";
			rs = statement.executeQuery(query_idFil);
			while (rs.next()) {
				Filamento fil = new Filamento(rs.getInt("idfil"));
				fil.setIdfil(rs.getInt("idfil"));
				numeroFilamenti.add(fil);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			// release resources
			if(rs != null){
				rs.close();
			}
			// release resources
			if(statement != null){
				statement.close();
			}
			if(connection  != null){
				connection.close();
			}
		}
		return numeroFilamenti;
	}
}