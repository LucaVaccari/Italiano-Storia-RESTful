package it.castelli.ksv.sqlUtils.filters;

public class BetweenFilter implements Filter {
	private String fieldName, smallValue, bigValue;

	public BetweenFilter(String fieldName, String smallValue, String bigValue) {
		this.fieldName = fieldName;
		this.smallValue = smallValue;
		this.bigValue = bigValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
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
