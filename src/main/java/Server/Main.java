package Server;

import Server.Database.CombineCondition;
import Server.Database.Condition;
import Server.Database.DatabaseConnector;
import Server.Database.Operator;
import SocketMessageReceiver.SocketMessageReceiverController;
import Server.ServerInstance.TCPServer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException, ClassNotFoundException {

        TCPServer server = new TCPServer(4445);
        DatabaseConnector.connect();

        server.addOnStart(SocketMessageReceiverController::registerAll);
        server.start();

        var result = DatabaseConnector.select("client", new String[]{"id", "email"}, new Condition[]{
                new Condition("username", Operator.Equal, "andel", CombineCondition.NONE)
        });
        if (result.next()) System.out.println(result.getInt("id"));

        // Keep the server running
        Thread.currentThread().join();
    }
}
