package pt.iscte.code4all;

import java.security.*;
import java.util.Base64;

public class DigitalSignatureCrypto {
    public DigitalSignatureCrypto() {
    }

    public KeyPair generateKeyPair(int size) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(size);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // print the keys?
        System.out.println("Public key: " + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        System.out.println("Private key: " + Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));

        return keyPair;
    }

    public byte[] signMessage(String message, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.initSign(privateKey);
        byte[] dataToSign = message.getBytes();
        signature.update(dataToSign);
        return signature.sign();
    }

    public boolean verifySignature(String message, byte[] signature, PublicKey publicKey) throws Exception {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initVerify(publicKey);
        sign.update(message.getBytes());
        return sign.verify(signature);
    }
}
