package Server;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class RecoveryOTP {
    public static final String DEFAULT_DICTIONARY = "1234567890" + "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "!@#$%^&*()_+";

    public static final int DEFAULT_LENGTH = 6;

    public static RecoveryOTP instance = new RecoveryOTP();

    private final HashMap<String, OTPItem> otps = new HashMap<>();
    private final String dictionary;
    private final int length;

    private final IntervalThread intervalThread;

    public RecoveryOTP(String dictionary, int length) {
        this.dictionary = dictionary;
        this.length = length;

        this.intervalThread = new IntervalThread(1);
        this.intervalThread.addAction(this::update);
        this.intervalThread.start();
    }

    public RecoveryOTP() {
        this(DEFAULT_DICTIONARY, DEFAULT_LENGTH);
    }

    private String generateRandomString() {
        var random = new Random();
        var builder = new StringBuilder();

        for (var i = 0; i < length; i++) {
            builder.append(dictionary.charAt(random.nextInt(dictionary.length() + 1)) - 1);
        }

        return builder.toString();
    }

    private OTPItem generateOTP(Date expired) {
        return new OTPItem() {{
            otp = generateRandomString();
            time = expired.getTime();
        }};
    }

    public OTPItem generateOTP(String username, Date expired) {
        otps.remove(username);

        var otp = generateOTP(expired);
        otps.put(username, otp);

        return otp;
    }

    public boolean verifyOTP(String username, String otp) {
        var item = otps.get(username);

        if (item == null) {
            return false;
        }

        return item.otp.equals(otp);
    }

    public void removeOTP(String username) {
        otps.remove(username);
    }

    private void update() {
        var now = new Date().getTime();

        for (var otp : otps.entrySet()) {
            if (otp.getValue().time < now) {
                otps.remove(otp.getKey());

                System.out.println("Remove OTP" + otp.getKey());
            }
        }
    }

    public static class OTPItem {
        public String otp;
        public long time;
    }
}
