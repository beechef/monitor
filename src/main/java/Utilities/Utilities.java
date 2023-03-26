package Utilities;

import java.io.*;

public class Utilities {
    public static <T> T castBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        return (T) in.readObject();
    }

    public static byte[] toBytes(Object data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);

        out.writeObject(data);
        out.flush();

        return bos.toByteArray();
    }
}
