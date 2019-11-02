package pt.iscte.code4all.jce;

import pt.iscte.code4all.Utils.HexConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageHash {
    protected String hashAlgorithm;

    public MessageHash(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public String generateHash(String message) throws Exception {
        MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
        md.update(message.getBytes());

        byte[] digest = md.digest();

        HexConverter hexConverter = new HexConverter();
        return hexConverter.convert(digest);
    }
}
