package it.castelli.ksv.entities;

import java.sql.Date;

public class Topic implements Entity {
    private final String name;
    private final Date startDate, endDate;
    private final String description, place;
    // TODO: private final int[] topicsIds;
    // TODO: private final int[] authorsIds;

    // empty constructor is necessary for deserializing from JSON
    public Topic() {
        this.name = "";
        this.startDate = null;
        this.endDate = null;
        this.description = "";
        this.place = "";
    }

    public Topic(String name, Date startDate, Date endDate, String description, String place) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.place = place;
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

    @Override
    public String toString() {
        return "Topic{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
