package it.castelli.ksv.sqlUtils;

import it.castelli.ksv.sqlUtils.filters.BetweenFilter;
import it.castelli.ksv.sqlUtils.filters.EqualFilter;
import it.castelli.ksv.sqlUtils.filters.Filter;

public class QueryGenerator {
	public static String generateSelectQuery(String[] fieldList, String tableName, Filter[] whereFilters) {
		StringBuilder builder = new StringBuilder();

		builder.append("SELECT ");
		for (int i = 0; i < fieldList.length; i++) {
			builder.append(fieldList[i]);
			if (i < fieldList.length - 1)
				builder.append(",");
			builder.append(" ");
		}

		builder.append("FROM ");
		builder.append(tableName);

		if (whereFilters != null) {
			if (whereFilters.length > 0) {
				builder.append(" WHERE ");
				for (int i = 0; i < whereFilters.length; i++) {
					if (i != 0)
						builder.append(" AND ");

					if (whereFilters[i] instanceof EqualFilter) {
						builder.append(((EqualFilter) whereFilters[i]).getFieldName());
						builder.append(" = ");
						builder.append(((EqualFilter) whereFilters[i]).getFieldValue());
					}
					else if (whereFilters[i] instanceof BetweenFilter) {
						builder.append(((BetweenFilter) whereFilters[i]).getFieldName());
						builder.append(" BETWEEN ");
						builder.append(((BetweenFilter) whereFilters[i]).getSmallValue());
						builder.append(" AND ");
						builder.append(((BetweenFilter) whereFilters[i]).getBigValue());
					}
				}
			}
		}

		return builder.toString();
	}
}
