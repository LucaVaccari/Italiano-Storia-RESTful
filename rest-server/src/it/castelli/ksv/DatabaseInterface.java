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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to interact with the MySQL database. initialize() should be called before using this class.
 */
public final class DatabaseInterface {
    private static final String DB_URL = "jdbc:mysql://localhost/ksv-links";
    private static final String DB_USERNAME = "root";

    private static Connection sqlConnection;

    // prevent class instantiation
    private DatabaseInterface() {
    }

    // TODO: getOpusIds
    // TODO: getOpusById

    // TODO: implement author lifeYear search
    public static Integer[] getAuthorIds(HashMap<String, String> filterMap) throws SQLException {
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Filter> filterArr = new ArrayList<>();
        for (var entry : filterMap.entrySet()) {
            switch (entry.getKey().toLowerCase()) {
                case "firstname",
                        "lastname",
                        "birthyear",
                        "deathyear" -> filterArr.add(new EqualFilter(entry.getKey(), entry.getValue()));
            }
        }
        String[] fields = new String[]{"id_autore"};
        String sql = QueryGenerator.generateSelectQuery(fields, "autori", filterArr.toArray(new Filter[0]));
        ResultSet result = sqlConnection.createStatement().executeQuery(sql);
        while (result.next()) {
            ids.add(result.getInt("id_autore"));
        }
        return ids.toArray(new Integer[0]);
    }

    // TODO: implement topic year search
    public static Integer[] getTopicIds(HashMap<String, String> filterMap) {
        ArrayList<Integer> ids = new ArrayList<>();

        try {
            ArrayList<Filter> filterArr = new ArrayList<>();
            for (var entry : filterMap.entrySet()) {
                switch (entry.getKey().toLowerCase()) {
                    case "name" -> filterArr.add(new EqualFilter("nome", entry.getValue()));
                    case "place" -> filterArr.add(new EqualFilter("luogo", entry.getValue()));
                }
            }
            String[] fields = new String[]{"id_argomento"};
            String sql = QueryGenerator.generateSelectQuery(fields, "argomenti", filterArr.toArray(new Filter[0]));
            ResultSet result = sqlConnection.createStatement().executeQuery(sql);
            while (result.next()) {
                ids.add(result.getInt("id_argomento"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids.toArray(new Integer[0]);
    }

    public static Author getAuthorById(int id) throws SQLException {
        String[] fields = new String[]{"nome", "cognome", "data_nascita", "data_morte", "vita"};
        Filter[] filters = new Filter[]{
                new EqualFilter("id_autore", String.valueOf(id))
        };
        String sql = QueryGenerator.generateSelectQuery(fields, "autori", filters);
        ResultSet result = sqlConnection.createStatement().executeQuery(sql);
        if (result.next()) {
            return new Author(
                    result.getString("nome"),
                    result.getString("cognome"),
                    result.getDate("data_nascita"),
                    result.getDate("data_morte"),
                    result.getString("vita")
            );
        }

        return null;
    }

    public static Topic getTopicById(int id) throws SQLException {
        Filter[] filters = new Filter[]{
                new EqualFilter("id_argomento", String.valueOf(id))
        };
        String[] fields = new String[]{"nome", "data_inizio", "data_fine", "descrizione", "luogo"};
        String sql = QueryGenerator.generateSelectQuery(fields, "argomenti", filters);
        ResultSet result = sqlConnection.createStatement().executeQuery(sql);
        if (result.next()) {
            return new Topic(
                    result.getString("nome"),
                    result.getDate("data_inizio"),
                    result.getDate("data_fine"),
                    result.getString("descrizione"),
                    result.getString("luogo")
            );
        }
        return null;
    }

    /**
     * Initialize the connection with the database.
     */
    public static void initialize() {
        try {
            sqlConnection = DriverManager.getConnection(DB_URL + "?user=" + DB_USERNAME);
            System.out.println("Connected to " + DB_URL + " as " + DB_USERNAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Provides an array of all authors in the database.
     *
     * @param filterMap A map containing filters to apply to the query (see the REST documentation)
     * @return An array of the authors of the database.
     */
    public static Author[] getAuthors(HashMap<String, String> filterMap) throws SQLException {
        Integer[] authorIds = getAuthorIds(filterMap);
        Author[] authors = new Author[authorIds.length];
        for (int i = 0; i < authorIds.length; i++) authors[i] = getAuthorById(authorIds[i]);
        return authors;
    }

    /**
     * Provides an array of all topics in the database.
     *
     * @param filterMap A map containing filters to apply to the query (see the REST documentation)
     * @return An array of the topics of the database.
     */
    public static Topic[] getTopics(HashMap<String, String> filterMap) throws SQLException {
        Integer[] topicIds = getTopicIds(filterMap);
        Topic[] topics = new Topic[topicIds.length];
        for (int i = 0; i < topicIds.length; i++) topics[i] = getTopicById(topicIds[i]);
        return topics;
    }
}
