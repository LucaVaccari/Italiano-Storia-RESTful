package it.castelli.ksv.sqlUtils;

import it.castelli.ksv.sqlUtils.filters.BetweenFilter;
import it.castelli.ksv.sqlUtils.filters.EqualFilter;
import it.castelli.ksv.sqlUtils.filters.Filter;

/**
 * Utility class which provides useful methods for generating SQL queries
 */
public class QueryGenerator {
    private static void addWhereFilters(Filter[] whereFilters, StringBuilder builder) {
        if (whereFilters != null) {
            if (whereFilters.length > 0) {
                builder.append(" WHERE ");
                for (int i = 0; i < whereFilters.length; i++) {
                    if (i != 0)
                        builder.append(" AND ");

                    if (whereFilters[i] instanceof EqualFilter) {
                        builder.append(((EqualFilter) whereFilters[i]).getFieldName());
                        builder.append(" = '");
                        builder.append(((EqualFilter) whereFilters[i]).getFieldValue());
                        builder.append("'");
                    } else if (whereFilters[i] instanceof BetweenFilter) {
                        builder.append(((BetweenFilter) whereFilters[i]).getValue());
                        builder.append(" BETWEEN ");
                        builder.append(((BetweenFilter) whereFilters[i]).getSmallValue());
                        builder.append(" AND ");
                        builder.append(((BetweenFilter) whereFilters[i]).getBigValue());
                    }
                }
            }
        }
    }

    /**
     * Generates a SELECT SQL query
     *
     * @param fieldList    The list of fields to project
     * @param tableName    The name of the table (can be more than one separated by commas for joining)
     * @param whereFilters A list of filters which will be applied with the WHERE clause
     * @return The SQL query as a string
     */
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

        addWhereFilters(whereFilters, builder);

        return builder.toString();
    }

    /**
     * Generates a DELETE SQL query
     *
     * @param tableName    The name of the table
     * @param whereFilters A list of filters which will be applied with the WHERE clause
     * @return The SQL query as a string
     */
    public static String generateDeleteQuery(String tableName, Filter[] whereFilters) {
        StringBuilder builder = new StringBuilder();

        builder.append("DELETE FROM ");
        builder.append(tableName);
        addWhereFilters(whereFilters, builder);

        return builder.toString();
    }
}
