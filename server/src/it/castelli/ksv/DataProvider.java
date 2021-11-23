package it.castelli.ksv;

import java.util.ArrayList;

public class DataProvider {
    private static final ArrayList<LogicalLink> data = new ArrayList<>();

    public static void addData(LogicalLink element) {
        data.add(element);
    }

    public static void removeData(LogicalLink element) {
        data.remove(element);
    }

    public static ArrayList<Author> getAllAuthors() {
        return null;
    }

    public static ArrayList<Topic> getAllTopics() {
        return null;
    }
}
