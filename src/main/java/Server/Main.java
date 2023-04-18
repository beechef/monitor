package Server;

import Server.Database.DatabaseConnector;
import Server.MiddleWare.EncryptPassword;
import Server.MiddleWare.EncryptPasswordMiddleWare;
import Server.ServerInstance.TCPServer;
import SocketMessageReceiver.SocketMessageReceiverController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException, ClassNotFoundException, NoSuchAlgorithmException {

        TCPServer server = new TCPServer(4445);
        server.setBuffer(1024 * 8);

        server.addOnStart(Key.JWTKey::readKey);
        server.addOnStart(DatabaseConnector::connect);
        server.addOnStart(UserController::registerEvents);
        server.addOnStart(SocketMessageReceiverController::registerEvents);

        server.addOnStop(DatabaseConnector::stop);
        server.addOnStop(UserController::unRegisterEvents);
        server.addOnStop(SocketMessageReceiverController::unRegisterEvents);

        server.addMiddleware(new EncryptPasswordMiddleWare(EncryptPassword.SALT));

        server.start();

        // Keep the server running
        Thread.currentThread().join();
    }
}
