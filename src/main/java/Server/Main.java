package Server;

import Server.Database.DatabaseConnector;
import Server.MiddleWare.EncryptPassword;
import Server.MiddleWare.EncryptPasswordMiddleWare;
import Server.ServerInstance.TCPServer;
import Server.ServerInstance.UDPServer;
import SocketMessageReceiver.SocketMessageReceiverController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException, ClassNotFoundException, NoSuchAlgorithmException {

        TCPServer tcpServer = new TCPServer(4445);
        tcpServer.setBuffer(1024 * 16);

        tcpServer.addOnStart(Key.JWTKey::readKey);
        tcpServer.addOnStart(DatabaseConnector::connect);
        tcpServer.addOnStart(UserController::registerEvents);
        tcpServer.addOnStart(SocketMessageReceiverController::registerEvents);

        tcpServer.addOnStop(DatabaseConnector::stop);
        tcpServer.addOnStop(UserController::unRegisterEvents);
        tcpServer.addOnStop(SocketMessageReceiverController::unRegisterEvents);

        tcpServer.addMiddleware(new EncryptPasswordMiddleWare(EncryptPassword.SALT));

        tcpServer.start();

        UDPServer udpServer = new UDPServer(4446);
        udpServer.setBuffer(1024 * 16);

        udpServer.start();

        // Keep the server running
        Thread.currentThread().join();
    }
}
