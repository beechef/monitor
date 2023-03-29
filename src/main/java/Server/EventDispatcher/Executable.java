package Server.EventDispatcher;

import java.sql.SQLException;

public interface Executable {
    void execute() throws SQLException, ClassNotFoundException;
}
