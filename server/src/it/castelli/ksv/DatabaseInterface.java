package it.castelli.ksv;

import it.castelli.ksv.sqlUtils.QueryGenerator;
import it.castelli.ksv.sqlUtils.filters.EqualFilter;
import it.castelli.ksv.sqlUtils.filters.Filter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to interact with the MySQL database. initialize() should be called before using this class.
 */
public final class DatabaseInterface {
	private static final String DB_URL = "jdbc:mysql://localhost/ksv-links";
	private static final String DB_USERNAME = "root";

	private static Connection sqlConnection;

	private DatabaseInterface() {
	}

	/**
	 * Initialize the connection with the database.
	 */
	public static void initialize() {
		try {
			sqlConnection = DriverManager.getConnection(DB_URL + "?user=" + DB_USERNAME);
			System.out.println("Connected to " + DB_URL + " as " + DB_USERNAME);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Provides an array of all authors in the database.
	 *
	 * @param filterMap A map containing filters to apply to the query (see the REST documentation)
	 * @return An array of the authors of the database.
	 */
	public static Author[] getAllAuthors(HashMap<String, String> filterMap) {
		ArrayList<Author> authorList = new ArrayList<>();
		try {
			ArrayList<Filter> filterArr = new ArrayList<>();
			for (var entry : filterMap.entrySet()) {
				switch (entry.getKey().toLowerCase()) {
					case "firstname", "deathyear", "lastname", "birthyear" -> filterArr.add(new EqualFilter(entry.getKey(), entry.getValue()));
				}
			}
			String[] fields = new String[]{"nome", "cognome", "data_nascita", "data_morte", "vita"};
			String sql = QueryGenerator.generateSelectQuery(fields, "autori", filterArr.toArray(new Filter[0]));
			ResultSet result = sqlConnection.createStatement().executeQuery(sql);
			while (result.next()) {
				authorList.add(new Author(
						result.getString("nome"),
						result.getString("cognome"),
						result.getDate("data_nascita"),
						result.getDate("data_morte"),
						result.getString("vita")
				));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return authorList.toArray(new Author[0]);
	}

	/**
	 * Provides an array of all topics in the database.
	 *
	 * @param filterMap A map containing filters to apply to the query (see the REST documentation)
	 * @return An array of the topics of the database.
	 */
	public static Topic[] getAllTopics(HashMap<String, String> filterMap) {
		ArrayList<Topic> topicList = new ArrayList<>();

		try {
			ArrayList<Filter> filterArr = new ArrayList<>();
			for (var entry : filterMap.entrySet()) {
				switch (entry.getKey().toLowerCase()) {
					case "name", "place" -> filterArr.add(new EqualFilter(entry.getKey(), entry.getValue()));
				}
			}
			String[] fields = new String[]{"nome", "data_inizio", "data_fine", "descrizione", "luogo"};
			String sql = QueryGenerator.generateSelectQuery(fields, "argomenti", filterArr.toArray(new Filter[0]));
			ResultSet result = sqlConnection.createStatement().executeQuery(sql);
			while (result.next()) {
				topicList.add(new Topic(
						result.getString("nome"),
						result.getDate("data_inizio"),
						result.getDate("data_fine"),
						result.getString("descrizione"),
						result.getString("luogo")
				));
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return topicList.toArray(new Topic[0]);
	}
}
