package pt.iscte.code4all.bc;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Utility class
 */
public class Utils {
    /**
     * Generate a RSA keypair
     * @param size Size of the key
     * @return Returns the keypair
     * @throws Exception
     */
    public static KeyPair generateKeyPair(int size) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(size, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * Calculates the time interval
     * @param hoursInFuture The number of hours in the future for the validity of the certificate
     * @return The new date
     */
    public static Date calculateDate(int hoursInFuture) {
        long secs = System.currentTimeMillis() / 1000;
        return new Date((secs + (hoursInFuture * 60 * 60)) * 1000);
    }

    /**
     * Returns the base for the serial number counter
     */
    public static long serialNumberBase = System.currentTimeMillis();

    /**
     * Calculates the serial number of the certificate to issue
     * @return The new serial number
     */
    public static synchronized BigInteger calculateSerialNumber() {
        return BigInteger.valueOf(serialNumberBase++);
    }
}
