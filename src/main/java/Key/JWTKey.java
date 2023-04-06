package Key;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

public class JWTKey {
    private static final String KEY_PATH = "key\\private_key_pkcs8.der";

    private static PrivateKey key;

    public static PrivateKey getKey() {
        if (key == null) {
            readKey();
        }

        return key;
    }


    public static void readKey() {
        try {
            var path = Paths.get("", KEY_PATH).toAbsolutePath();
            var buffer = Files.readAllBytes(path);
            var keySpec = new java.security.spec.PKCS8EncodedKeySpec(buffer);

            key = KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
