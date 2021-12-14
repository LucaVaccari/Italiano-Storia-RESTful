package it.castelli.ksv.sqlUtils.filters;

public class BetweenFilter implements Filter {
    private String value, smallValue, bigValue;

    public BetweenFilter(String fieldName, String smallValue, String bigValue) {
        this.value = fieldName;
        this.smallValue = smallValue;
        this.bigValue = bigValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSmallValue() {
        return smallValue;
    }

    public void setSmallValue(String smallValue) {
        this.smallValue = smallValue;
    }

    public String getBigValue() {
        return bigValue;
    }

    public void setBigValue(String bigValue) {
        this.bigValue = bigValue;
    }
}
