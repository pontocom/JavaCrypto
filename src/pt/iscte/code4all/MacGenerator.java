package pt.iscte.code4all;

import pt.iscte.code4all.Utils.HexConverter;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class MacGenerator {
    protected static String SALT = "This is my salt";

    public MacGenerator() {
    }

    public String generateMac(String message) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(secureRandom);
        Key key = keyGenerator.generateKey();
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] msgBytes = message.getBytes();
        byte[] macResult = mac.doFinal(msgBytes);
        HexConverter hexConverter = new HexConverter();
        return hexConverter.convert(macResult);
    }

    public String generateMac(String password, String message) throws Exception {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey tmp = secretKeyFactory.generateSecret(keySpec);
        SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        byte[] msgBytes = message.getBytes();
        byte[] macResult = mac.doFinal(msgBytes);
        HexConverter hexConverter = new HexConverter();
        return hexConverter.convert(macResult);
    }
}
