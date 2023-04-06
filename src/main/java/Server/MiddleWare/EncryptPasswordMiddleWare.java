package Server.MiddleWare;

import Server.EventDispatcher.MiddlewareSocketMessageEvent;
import Server.EventDispatcher.SocketMessage;
import java.security.NoSuchAlgorithmException;

public class EncryptPasswordMiddleWare implements MiddlewareSocketMessageEvent {

    private final EncryptPassword encryptModule;

    public EncryptPasswordMiddleWare(String salt) throws NoSuchAlgorithmException {
        encryptModule = new EncryptPassword(salt);
    }

    private boolean hasEncryptMark(SocketMessage data) {
        return data.msg.data instanceof EncryptPasswordMark;
    }

    @Override
    public boolean execute(SocketMessage data) {
        if (!hasEncryptMark(data)) return true;

        var mark = (EncryptPasswordMark) data.msg.data;
        var password = mark.getPassword();
        System.out.println("Before: " + password);

        password = encryptModule.encrypt(password);
        mark.setPassword(password);

        System.out.println("After: " + password);

        return true;
    }

}
