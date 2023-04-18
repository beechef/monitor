package Server.ServerInstance;

public interface Sender {
    void send(Object target, Message msg);

    void broadCast(Message msg);

    int getBuffer();
}
