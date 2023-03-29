package Server.ServerInstance;

public interface Server {
    void send(Object target, Message msg);
    void broadCast(Message msg);
}
