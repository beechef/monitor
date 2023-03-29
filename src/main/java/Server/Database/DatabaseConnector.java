package Server.Database;

import java.sql.*;
import java.util.List;

public class DatabaseConnector {

    //region Config
    private static final String HOST_NAME = "localhost";
    private static final int PORT = 3306;
    private static final String DB_NAME = "Monitor";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static final String CONNECT_STRING = "jdbc:mysql://%s:%d/%s";
    private static final String DB_URL = String.format(CONNECT_STRING, HOST_NAME, PORT, DB_NAME);

    //endregion

    private static final String QUOTATION_MARK = "\"%s\"";
    private static final String SELECT_QUERY = "SELECT %s FROM %s WHERE %s";
    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String DELETE_QUERY = "DELETE FROM %s WHERE %s";
    private static final String UPDATE_QUERY = "UPDATE %s SET %s WHERE %s";

    private static Connection connection;
    private static Statement cachedStatement;

    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        cachedStatement = connection.createStatement();

        System.out.printf("Connect Successful to %s:%d - Database Name: %s", HOST_NAME, PORT, DB_NAME);
        System.out.println();
    }

    private static String fieldsToString(String[] fields) {
        if (fields == null || fields.length == 0) return "*";
        var stringBuilder = new StringBuilder();

        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];

            stringBuilder.append(field);
            if (i != fields.length - 1) stringBuilder.append(",");
        }

        return stringBuilder.toString();
    }

    private static String conditionsToString(Condition[] conditions) {
        if (conditions == null || conditions.length == 0) return "1=1";
        var stringBuilder = new StringBuilder();

        for (var condition : conditions) {
            stringBuilder.append(condition.toString());
        }

        return stringBuilder.toString();
    }

    public static ResultSet select(String tableName, String[] fields, Condition[] conditions) throws SQLException {
        var query = String.format(SELECT_QUERY, fieldsToString(fields), tableName, conditionsToString(conditions));

        System.out.println(query);
        return cachedStatement.executeQuery(query);
    }

    private static KeyPair<String, String> combineInsertFieldValues(List<KeyPair<String, String>> fieldValues, String separate) {
        var combinedFields = new StringBuilder();
        var combinedValues = new StringBuilder();

        for (int i = 0; i < fieldValues.size(); i++) {
            KeyPair<String, String> fieldValue = fieldValues.get(i);

            combinedFields.append(fieldValue.key);
            combinedValues.append(String.format(QUOTATION_MARK, fieldValue.value));

            if (i != fieldValues.size() - 1) {
                combinedFields.append(separate);
                combinedValues.append(separate);
            }
        }

        return new KeyPair<>(combinedFields.toString(), combinedValues.toString());
    }

    private static String combineFieldValues(List<KeyPair<String, String>> fieldValues, String separate) {
        var combinedFieldValues = new StringBuilder();

        for (int i = 0; i < fieldValues.size(); i++) {
            KeyPair<String, String> fieldValue = fieldValues.get(i);

            combinedFieldValues.append(fieldValue.key);
            combinedFieldValues.append("=");
            combinedFieldValues.append(String.format(QUOTATION_MARK, fieldValue.value));

            if (i != fieldValues.size() - 1) {
                combinedFieldValues.append(separate);
            }
        }

        return combinedFieldValues.toString();
    }

    public static boolean insert(String tableName, List<KeyPair<String, String>> fieldValues) throws SQLException {
        var combinedFieldValues = combineInsertFieldValues(fieldValues, ",");
        var query = String.format(INSERT_QUERY, tableName, combinedFieldValues.key, combinedFieldValues.value);

        System.out.println(query);
        return cachedStatement.execute(query);
    }

    public static boolean delete(String tableName, Condition[] conditions) throws SQLException {
        var query = String.format(DELETE_QUERY, tableName, conditionsToString(conditions));

        System.out.println(query);
        return cachedStatement.execute(query);
    }

    public static boolean update(String tableName, List<KeyPair<String, String>> fieldValues, Condition[] conditions) throws SQLException {
        var query = String.format(UPDATE_QUERY, tableName, combineFieldValues(fieldValues, ","), conditionsToString(conditions));

        System.out.println(query);
        return cachedStatement.execute(query);
    }

    public static void stop() throws SQLException {
        cachedStatement.close();
        connection.close();
    }
}
