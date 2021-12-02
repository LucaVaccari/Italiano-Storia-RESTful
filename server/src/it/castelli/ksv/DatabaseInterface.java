package it.castelli.ksv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class to interact with the MySQL database.
 * initialize() should be called before using this class.
 */
public final class DatabaseInterface {
    private static final String DB_URL = "jdbc:mysql://localhost/ksv-links";
    private static final String DB_USERNAME = "root";

    private static Connection sqlConnection;

    private DatabaseInterface() {
    }

    /**
     * Sets the defaults of the class.
     */
    public static void initialize() {
        // TEST
//        ArrayList<Opus> opuses = new ArrayList<>();
//        addData(new Opus("I malavoglia", new Date(1890, 4, 23), "prosa", "verismo", "I malavoglia"
//        ));
//
//        addData(new Author("Luigi", "Pirandello", new Date(1885, 2, 19),
//                new Date(1950, 4, 23), "Was born and dead"));
//        addData(new Author("Giovanni", "Verga", new Date(1885, 2, 19),
//                new Date(1949, 4, 23), "Was born and dead"));
//
//        addData(new Topic("Prima Guerra Mondiale", new Date(1914, 5, 24), new Date(1918, 11, 4), "Prima guerra",
//                "Mondo"));
//        addData(new Topic("Decadentismo", new Date(1860, 1, 1), new Date(1910, 4, 6), "Corrente culturale " +
//                "decadentista", "Europa"));
        // END TEST

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
     * @return An array of the authors of the database.
     */
    public static Author[] getAllAuthors() {
        ArrayList<Author> authorList = new ArrayList<>();

        return authorList.toArray(new Author[0]);
    }

    /**
     * Provides an array of all topics in the database.
     *
     * @return An array of the topics of the database.
     */
    public static Topic[] getAllTopics() {
        ArrayList<Topic> topicList = new ArrayList<>();

        return topicList.toArray(new Topic[0]);
    }

    /**
     * Replaces an entity in the database.
     *
     * @param oldE The old entity which will be replaced by the new one.
     * @param newE The new entity which will replace the old one.
     */
    public static void modifyData(Entity oldE, Entity newE) {

    }
}
