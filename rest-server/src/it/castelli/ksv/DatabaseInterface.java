package it.castelli.ksv;

import it.castelli.ksv.entities.Author;
import it.castelli.ksv.entities.Topic;
import it.castelli.ksv.sqlUtils.QueryGenerator;
import it.castelli.ksv.sqlUtils.filters.EqualFilter;
import it.castelli.ksv.sqlUtils.filters.Filter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to interact with the MySQL database. initialize() should be called before using this class.
 */
public final class DatabaseInterface {
	private static final String DB_URL = "jdbc:mysql://localhost/ksv-links";
	private static final String DB_USERNAME = "root";
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static Connection sqlConnection;

	// prevent class instantiation
	private DatabaseInterface() {
	}

	/**
	 * Initialize the connection with the database.
	 */
	public static void initialize() throws SQLException {
		sqlConnection = DriverManager.getConnection(DB_URL + "?user=" + DB_USERNAME);
		System.out.println("Connected to " + DB_URL + " as " + DB_USERNAME);
	}

	// TODO: getOpusIds
	// TODO: getOpusById

	/**
	 * Gets a list of ids of authors filtered by a filter map
	 *
	 * @param filterMap A list of query for selecting specific authors
	 * @return An array of ids of authors
	 * @throws SQLException In case the query is bad formed
	 */
	public static Integer[] getAuthorIds(HashMap<String, String> filterMap) throws SQLException {
		ArrayList<Integer> ids = new ArrayList<>();
		ArrayList<Filter> filterArr = new ArrayList<>();
		for (var entry : filterMap.entrySet()) {
			switch (entry.getKey().toLowerCase()) {
				case "firstname" -> filterArr.add(new EqualFilter("nome", entry.getValue()));
				case "lastname" -> filterArr.add(new EqualFilter("cognome", entry.getValue()));
				case "birthyear" -> filterArr.add(new EqualFilter("EXTRACT(YEAR FROM data_nascita)",
						entry.getValue()));
				case "deathyear" -> filterArr.add(new EqualFilter("EXTRACT(YEAR FROM data_morte)", entry.getValue()));
			}
		}
		String[] fields = new String[]{"id_autore", "data_nascita", "data_morte"};
		String sql = QueryGenerator.generateSelectQuery(fields, "autori", filterArr.toArray(new Filter[0]));
		ResultSet result = sqlConnection.createStatement().executeQuery(sql);
		while (result.next()) {
			boolean shouldAdd = true;
			if (filterMap.containsKey("lifeyear")) {
				int birthYear = Utility.dateFromMillis(result.getDate("data_nascita").getTime()).getYear();
				int deathYear = Utility.dateFromMillis(result.getDate("data_morte").getTime()).getYear();
				int lifeYear = Integer.parseInt(filterMap.get("lifeyear"));
				shouldAdd = lifeYear >= birthYear && lifeYear <= deathYear;
			}

			if (shouldAdd)
				ids.add(result.getInt("id_autore"));
		}
		return ids.toArray(new Integer[0]);
	}

	/**
	 * Gets a list of ids of topics filtered by a filter map
	 *
	 * @param filterMap A list of query for selecting specific topics
	 * @return An array of ids of topics
	 */
	public static Integer[] getTopicIds(HashMap<String, String> filterMap) throws SQLException {
		ArrayList<Integer> ids = new ArrayList<>();

		ArrayList<Filter> filterArr = new ArrayList<>();
		for (var entry : filterMap.entrySet()) {
			switch (entry.getKey().toLowerCase()) {
				case "name" -> filterArr.add(new EqualFilter("nome", entry.getValue()));
				case "place" -> filterArr.add(new EqualFilter("luogo", entry.getValue()));
			}
		}
		String[] fields = new String[]{"id_argomento", "data_inizio", "data_fine"};
		String sql = QueryGenerator.generateSelectQuery(fields, "argomenti", filterArr.toArray(new Filter[0]));
		ResultSet result = sqlConnection.createStatement().executeQuery(sql);
		while (result.next()) {
			boolean shouldAdd = true;
			if (filterMap.containsKey("year")) {
				int startYear = Utility.dateFromMillis(result.getDate("data_inizio").getTime()).getYear();
				int endYear = Utility.dateFromMillis(result.getDate("data_fine").getTime()).getYear();
				int year = Integer.parseInt(filterMap.get("year"));
				shouldAdd = year >= startYear && year <= endYear;
			}

			if (shouldAdd)
				ids.add(result.getInt("id_argomento"));
		}

		return ids.toArray(new Integer[0]);
	}

	/**
	 * Gets an author from its id
	 *
	 * @param id The id of the author
	 * @return The author
	 * @throws SQLException In case the query is bad formed
	 */
	public static Author getAuthorById(int id) throws SQLException {
		String[] fields = new String[]{"nome", "cognome", "data_nascita", "data_morte", "vita"};
		Filter[] filters = new Filter[]{
				new EqualFilter("id_autore", String.valueOf(id))
		};
		String sql = QueryGenerator.generateSelectQuery(fields, "autori", filters);
		ResultSet result = sqlConnection.createStatement().executeQuery(sql);
		if (result.next()) {
			return new Author(
					id,
					result.getString("nome"),
					result.getString("cognome"),
					result.getDate("data_nascita"),
					result.getDate("data_morte"),
					result.getString("vita")
			);
		}

		return null;
	}

	/**
	 * Gets a topic from its id
	 *
	 * @param id The id of the topic
	 * @return The topic
	 * @throws SQLException In case the query is bad formed
	 */
	public static Topic getTopicById(int id) throws SQLException {
		Filter[] filters = new Filter[]{
				new EqualFilter("id_argomento", String.valueOf(id))
		};
		String[] fields = new String[]{"nome", "data_inizio", "data_fine", "descrizione", "luogo"};
		String sql = QueryGenerator.generateSelectQuery(fields, "argomenti", filters);
		ResultSet result = sqlConnection.createStatement().executeQuery(sql);
		if (result.next()) {
			return new Topic(
					id, result.getString("nome"),
					result.getDate("data_inizio"),
					result.getDate("data_fine"),
					result.getString("descrizione"),
					result.getString("luogo")
			);
		}
		return null;
	}

	/**
	 * Provides an array of all authors in the database.
	 *
	 * @param filterMap A map containing filters to apply to the query (see the REST documentation)
	 * @return An array of the authors of the database.
	 * @throws SQLException In case the query is bad formed
	 */
	public static Author[] getAuthors(HashMap<String, String> filterMap) throws SQLException {
		Integer[] authorIds = getAuthorIds(filterMap);
		Author[] authors = new Author[authorIds.length];
		for (int i = 0; i < authorIds.length; i++) authors[i] = getAuthorById(authorIds[i]);
		return authors;
	}

	/**
	 * Provides an array of all topics in the database
	 *
	 * @param filterMap A map containing filters to apply to the query (see the REST documentation)
	 * @return An array of the topics of the database
	 * @throws SQLException In case the query is bad formed
	 */
	public static Topic[] getTopics(HashMap<String, String> filterMap) throws SQLException {
		Integer[] topicIds = getTopicIds(filterMap);
		Topic[] topics = new Topic[topicIds.length];
		for (int i = 0; i < topicIds.length; i++) topics[i] = getTopicById(topicIds[i]);
		return topics;
	}

	/**
	 * Adds the passed author in the database
	 *
	 * @param author The author to add
	 * @throws SQLException In case the query is bad formed
	 */
	public static void postAuthor(Author author) throws SQLException {
		String sql =
				"INSERT INTO autori (nome, cognome, data_nascita, data_morte, vita) " +
						"VALUES ('" +
						author.getFirstName() + "', '" +
						author.getLastName() + "', '" +
						dateFormat.format(author.getBirthDate()) + "', '" +
						dateFormat.format(author.getDeathDate()) + "', '" +
						author.getLife() +
						"')";
		sqlConnection.createStatement().executeUpdate(sql);
	}

	/**
	 * Adds the passed topic in the database
	 *
	 * @param topic The topic to add
	 * @throws SQLException In case the query is bad formed
	 */
	public static void postTopic(Topic topic) throws SQLException {
		String sql =
				"INSERT INTO argomenti (nome, data_inizio, data_fine, descrizione, luogo) " +
						"VALUES (" +
						topic.getName() + "', '" +
						dateFormat.format(topic.getStartDate()) + "', '" +
						dateFormat.format(topic.getEndDate()) + "', '" +
						topic.getDescription() + "', '" +
						topic.getPlace() +
						"')";
		sqlConnection.createStatement().executeUpdate(sql);
	}

	/**
	 * Updates an author in the database
	 *
	 * @param author The new author information
	 * @param id     the id of the author to update
	 * @throws SQLException In case the query is bad formed
	 */
	public static void putAuthor(Author author, int id) throws SQLException {
		String sql = "UPDATE autori SET " +
				"nome = '" + author.getFirstName() +
				"', cognome = '" + author.getLastName() +
				"', data_nascita = '" + dateFormat.format(author.getBirthDate()) +
				"', data_morte = '" + dateFormat.format(author.getDeathDate()) +
				"', vita = '" + author.getLife() +
				"' WHERE id_autore = " + id;
		sqlConnection.createStatement().executeUpdate(sql);
	}

	/**
	 * Updates a topic in the database
	 *
	 * @param topic The new topic information
	 * @param id    The id of the topic to update
	 * @throws SQLException In case the query is bad formed
	 */
	public static void putTopic(Topic topic, int id) throws SQLException {
		String sql = "UPDATE argomenti SET " +
				"nome = '" + topic.getName() +
				"', data_inizio = '" + dateFormat.format(topic.getStartDate()) +
				"', data_fine = '" + dateFormat.format(topic.getEndDate()) +
				"', descrizione = '" + topic.getDescription() +
				"', luogo = '" + topic.getPlace() +
				"'WHERE id_argomento = " + id;
		sqlConnection.createStatement().executeUpdate(sql);
	}

	/**
	 * Delete an author from the database
	 *
	 * @param id The id of the author to remove
	 * @throws SQLException In case the query is bad formed
	 */
	public static void deleteAuthor(int id) throws SQLException {
		Filter[] filters = new Filter[]{
				new EqualFilter("id_autore", String.valueOf(id))
		};
		String sql = QueryGenerator.generateDeleteQuery("autori", filters);
		sqlConnection.createStatement().executeUpdate(sql);
	}

	/**
	 * Delete a topic from the database
	 *
	 * @param id The id of the topic to remove
	 * @throws SQLException In case the query is bad formed
	 */
	public static void deleteTopic(int id) throws SQLException {
		Filter[] filters = new Filter[]{
				new EqualFilter("id_argomento", String.valueOf(id))
		};
		String sql = QueryGenerator.generateDeleteQuery("argomenti", filters);
		sqlConnection.createStatement().executeUpdate(sql);
	}
}
