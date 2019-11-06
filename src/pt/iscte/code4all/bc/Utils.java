package pt.iscte.code4all.bc;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Date;

public class Utils {
    public static KeyPair generateKeyPair(int size) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(size, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    public static Date calculateDate(int hoursInFuture) {
        long secs = System.currentTimeMillis() / 1000;
        return new Date((secs + (hoursInFuture * 60 * 60)) * 1000);
    }

    public static long serialNumberBase = System.currentTimeMillis();

    public static synchronized BigInteger calculateSerialNumber() {
        return BigInteger.valueOf(serialNumberBase++);
    }
}
