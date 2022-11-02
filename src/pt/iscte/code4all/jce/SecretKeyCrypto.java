package pt.iscte.code4all.jce;

import pt.iscte.code4all.Utils.HexConverter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Base64;

public class SecretKeyCrypto {

    public SecretKeyCrypto() {
    }

    public byte[] encryptWithSecretKey(String message, String password) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] k = messageDigest.digest(password.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(k, "AES");
        System.out.println("SecretKey to be used: " + Base64.getEncoder().encodeToString(secretKeySpec.getEncoded()));
        cipher.init(cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(message.getBytes());
    }

    public byte[] decryptWithSecretKey(byte[] encriptedMessage, String password) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] k = messageDigest.digest(password.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(k, "AES");
        System.out.println("SecretKey to be used: " + Base64.getEncoder().encodeToString(secretKeySpec.getEncoded()));
        cipher.init(cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(encriptedMessage);

    }
}