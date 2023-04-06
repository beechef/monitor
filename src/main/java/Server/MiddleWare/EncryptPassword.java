package Server.MiddleWare;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptPassword {
    private static final String ENCRYPT_ALGORITHM = "MD5";
    public static final String SALT = "Andel Nguyen :))";

    private final MessageDigest md;
    private final String salt;


    public EncryptPassword(String salt) throws NoSuchAlgorithmException {
        this.salt = salt;
        md = MessageDigest.getInstance(ENCRYPT_ALGORITHM);
    }

    public String encrypt(String password) {
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
