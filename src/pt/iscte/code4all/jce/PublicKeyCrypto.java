package pt.iscte.code4all.jce;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class PublicKeyCrypto {
    private KeyPair keyPair;

    public PublicKeyCrypto() {
    }

    public void generateKeyPair(int dimension) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(dimension);
        keyPair = keyPairGenerator.generateKeyPair();

        // print the keys?
        System.out.println("Public key: " + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        System.out.println("Private key: " + Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
    }

    public byte[] cipherWithPublicKey(String message) throws Exception {
        PublicKey publicKey = keyPair.getPublic();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        cipher.update(message.getBytes());
        byte[] cipherText = cipher.doFinal();
        return cipherText;
    }

    public byte[] decipherWithPrivateKey(byte[] encryptedMessage) throws Exception {
        PrivateKey privateKey = keyPair.getPrivate();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plainText = cipher.doFinal(encryptedMessage);
        return plainText;
    }
}
