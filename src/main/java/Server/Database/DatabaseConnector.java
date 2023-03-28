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

    private static final String SELECT_QUERY = "SELECT %s FROM %s WHERE %s";

    private static Connection _connection;

    public static void connect() throws SQLException, ClassNotFoundException {
        Connection connection;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

        _connection = connection;
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
        var statement = _connection.createStatement();

        return statement.executeQuery(query);
    }

}
