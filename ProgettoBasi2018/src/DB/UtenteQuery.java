package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Entity.Utente;

public class UtenteQuery {

	private DataSource dataSource;
	public UtenteQuery() {
		dataSource = new DataSource();
	}
	public Utente cercaUtente(String username, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Utente utente = null;
		final String query = "SELECT * FROM utente WHERE \"USERNAME\" = ? AND \"PASSWORD\" = ?";
		try {
			System.out.println("per adesso ok 1111111111");
			connection = this.dataSource.getConnection();
			System.out.println("per adesso ok 2222222222");
			statement = connection.prepareStatement(query);
			System.out.println("per adesso ok 33333333");
			statement.setString(1, username);
			statement.setString(2, password);
			result = statement.executeQuery();
			System.out.println("per adesso ok 44444444444");
			System.out.println("utente loggato");
			while (result.next()) {
				utente = new Utente();
				utente.setNome(result.getString("NOME"));
				utente.setCognome(result.getString("COGNOME"));
				utente.setUsername(result.getString("USERNAME"));
				utente.setPassword(result.getString("PASSWORD"));
				utente.setEmail(result.getString("EMAIL"));
				utente.setRuolo(result.getString("RUOLO").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// release resources
			if (result != null) {
				result.close();
			}
			// release resources
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return utente;
	}

	public Utente aggiungiUtente(String nome, String cognome, String username, String password, String email,
								 String ruolo) throws SQLException {
		Utente u;
		Connection connection = null;
		PreparedStatement statement = null;
		final String insert = "INSERT INTO utente (\"NOME\", \"COGNOME\", \"USERNAME\", \"PASSWORD\", \"EMAIL\", \"RUOLO\") VALUES (?,?,?,?,?,?)";

		try {
			connection = this.dataSource.getConnection();

			statement = connection.prepareStatement(insert);
			statement.setString(1, nome);
			statement.setString(2, cognome);
			statement.setString(3, username);
			statement.setString(4, email);
			statement.setString(5, password);
			statement.setString(6, ruolo);
			statement.executeUpdate();


			u = new Utente(nome, cognome, username, password, email, ruolo);
			return u;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// release resources
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return null;
	}

	public boolean findUtente(String username) throws SQLException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final String query = "SELECT * FROM utente WHERE \"USERNAME\" = ?";

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			result = statement.executeQuery();
			if (result.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// release resources
			if (result != null) {
				result.close();
			}
			// release resources
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return false;
	}
}
