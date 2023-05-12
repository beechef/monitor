package Utilities;

import oshi.SystemInfo;

import java.io.*;

public class Utilities {

    public static Object castBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        var obj = in.readObject();
        
        in.close();
        return obj;
    }

    public static byte[] toBytes(Object data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);

        out.writeObject(data);
        out.flush();
        
        var bytes = bos.toByteArray();
        out.close();
        bos.close();
        
        return bytes;
    }

    public static String getUUID() {
        var systemInfo = new SystemInfo();
        var hardwareInfo = systemInfo.getHardware();

        return hardwareInfo.getComputerSystem().getHardwareUUID();
    }

    public static double byteToGB(long bytes) {
        return bytes / Math.pow(1024, 3);
    }

    public static double hzToGHz(long hz) {
        return hz / Math.pow(10, 9);
    }
}
