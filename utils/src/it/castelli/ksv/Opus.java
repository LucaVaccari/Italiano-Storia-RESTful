package it.castelli.ksv;

import java.sql.Date;

public class Opus implements Entity {
    private final String title;
    private final Date publishDate;
    private final String linguisticExpression;
    private final String current;
    private final String description;

    public Opus() {
        title = "";
        publishDate = null;
        linguisticExpression = "";
        current = "";
        description = "";
    }

    public Opus(String title, Date publishDate, String linguisticExpression, String current, String description) {
        this.title = title;
        this.publishDate = publishDate;
        this.linguisticExpression = linguisticExpression;
        this.current = current;
        this.description = description;
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
}
