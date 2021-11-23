package it.castelli.ksv;

import java.sql.Date;
import java.util.ArrayList;

public class Topic implements LogicalLink {
    private final String name;
    private final Date startDate, endDate;
    private final String description, place;
    private final ArrayList<LogicalLink> connections;

    public Topic(String name, Date startDate, Date endDate, String description, String place, ArrayList<LogicalLink> connections) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.place = place;
        this.connections = connections;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public ArrayList<LogicalLink> getConnections() {
        return connections;
    }
}
