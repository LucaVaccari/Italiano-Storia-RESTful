package it.castelli.ksv;

import java.sql.Date;
import java.util.ArrayList;

public class Opus implements LogicalLink {
    private final String title;
    private final Date publishDate;
    private final String linguisticExpression;
    private final String current;
    private final String description;
    private final ArrayList<LogicalLink> connections;

    public Opus(String title, Date publishDate, String linguisticExpression, String current, String description, ArrayList<LogicalLink> connections) {
        this.title = title;
        this.publishDate = publishDate;
        this.linguisticExpression = linguisticExpression;
        this.current = current;
        this.description = description;
        this.connections = connections;
    }

    public String getTitle() {
        return title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getLinguisticExpression() {
        return linguisticExpression;
    }

    public String getCurrent() {
        return current;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<LogicalLink> getConnections() {
        return connections;
    }
}
