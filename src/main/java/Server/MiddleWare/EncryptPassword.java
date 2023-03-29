package Server.MiddleWare;

import Server.EventDispatcher.MiddlewareSocketMessageEvent;
import Server.EventDispatcher.SocketMessage;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptPassword implements MiddlewareSocketMessageEvent {
    private static final String ENCRYPT_ALGORITHM = "MD5";

    private final MessageDigest md;
    private final String salt;

    public EncryptPassword(String salt) throws NoSuchAlgorithmException {
        this.salt = salt;
        md = MessageDigest.getInstance(ENCRYPT_ALGORITHM);
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

        password = encrypt(password);
        mark.setPassword(password);

        System.out.println("After: " + password);

        return true;
    }

    private String encrypt(String password) {
        password += salt;
        md.update(password.getBytes());

        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder hash = new StringBuilder(bigInt.toString(16));
        while (hash.length() < 32) {
            hash.insert(0, "0");
        }

        return hash.toString();
    }
}
