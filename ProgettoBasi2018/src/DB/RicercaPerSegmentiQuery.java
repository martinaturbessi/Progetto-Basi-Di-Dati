package DB;

import Entity.PuntiSegmenti;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;

public class RicercaPerSegmentiQuery {
	private static DataSource dataSource;

	public RicercaPerSegmentiQuery() {

		dataSource = new DataSource();
	}


	public ArrayList<PuntiSegmenti> getNumeroSegmenti(int min, int max) throws SQLException{
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
        ArrayList<PuntiSegmenti> fil = new ArrayList<>();


		final String query_idFil = "SELECT (\"IDFIL\") as idfil FROM punti_segmenti WHERE \"N\" >= '" + min + "' and \"N\" <= '" + max + "'";


		try {
			connection = this.dataSource.getConnection();
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery(query_idFil);
			while (rs.next()) {
				PuntiSegmenti id = new PuntiSegmenti(rs.getInt("idfil"));
				fil.add(id);
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

		return fil;
	}
}

