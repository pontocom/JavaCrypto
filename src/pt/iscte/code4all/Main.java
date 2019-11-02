package pt.iscte.code4all;

import pt.iscte.code4all.jce.*;

import java.security.KeyPair;
import java.security.Provider;
import java.security.Security;
import java.util.Base64;
import java.util.Scanner;

public class Main {
    public static void runDigestTests() {
        String message = null;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your own message: ");
            message = sc.nextLine();

            // Generating different types of hashs over a message (from weaker to stronger)
            // MD5 - 128 bits
            MessageHash mdmd5 = new MessageHash("MD5");
            String hashmd5 = mdmd5.generateHash(message);
            System.out.println("Message hash (MD5) Hex format: " + hashmd5);

            // SHA-1 - 160 bits
            MessageHash mdsha1 = new MessageHash("SHA-1");
            String hashsha1 = mdsha1.generateHash(message);
            System.out.println("Message hash (SHA-1) Hex format: " + hashsha1);

            // SHA-256 - 256 bits
            MessageHash mdsha256 = new MessageHash("SHA-256");
            String hashsha256 = mdsha256.generateHash(message);
            System.out.println("Message hash (SHA-256) Hex format: " + hashsha256);

            // SHA-512 - 512 bits
            MessageHash mdsha512 = new MessageHash("SHA-512");
            String hashsha512 = mdsha512.generateHash(message);
            System.out.println("Message hash (SHA-512) Hex format: " + hashsha512);

        } catch (Exception e) {
            System.out.println("Exception error -> " + e.getMessage());
        }
    }

    public static void ListProviders() {
        Provider[] installedProviders = Security.getProviders();
        for(int i = 0; i != installedProviders.length; i++) {
            System.out.print(installedProviders[i].getName());
            System.out.print(": ");
            System.out.print(installedProviders[i].getInfo());
            System.out.println();
        }
    }

    public static void runMacTest() {
        try {
            String message = null;
            String password = null;

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter message to compute the MAC: ");
            message = sc.nextLine();

            System.out.println("Enter password to compute the key: ");
            password = sc.nextLine();

            //Generating a MAC - Message Authentication Code (using a self generated and random key)
            MacGenerator macGenerator = new MacGenerator();
            String mac = macGenerator.generateMac(message);
            System.out.println("MAC (with auto-generated key): " + mac);

            //Generating a MAC - Message Authentication Code (a specific password as key)
            MacGenerator macGeneratorKey = new MacGenerator();
            String macKey = macGeneratorKey.generateMac(password, message);
            System.out.println("MAC (with specified password): " + macKey);
        } catch (Exception e) {
            System.out.println("Some error occurred -> " + e.getMessage());
        }
    }

    public static void runSecretKeyTest() {
        try {
            String message = null;
            String password, passwordd = null;

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter message to cipher: ");
            message = sc.nextLine();

            System.out.println("Enter password to compute the key: ");
            password = sc.nextLine();

            //Encrypting a message with a specific secret key
            System.out.println("Starting ENCRYPTION...");
            SecretKeyCrypto secretKeyCrypto = new SecretKeyCrypto();
            byte[] encryptedMsg = secretKeyCrypto.encryptWithSecretKey(message, password);

            System.out.println("Encrypted result (raw): " + new String(encryptedMsg));
            System.out.println("Encrypted result (Base64): " + Base64.getEncoder().encodeToString(encryptedMsg));

            //Decrypting a message with a specific secret key
            System.out.println("Starting DECRYPTION...");
            System.out.println("Enter password to compute the key (same as used for encryption): ");
            passwordd = sc.nextLine();
            byte[] decryptedMsg = secretKeyCrypto.decryptWithSecretKey(encryptedMsg, passwordd);

            System.out.println("Decrypted result: " + new String(decryptedMsg));

        } catch (Exception e) {
            System.out.println("Some error occurred -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void runPublicKeyTest() {
        try {
            String message = null;

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter message to cipher: ");
            message = sc.nextLine();

            //Encrypting a message with a key pair - encrypting with public-key
            System.out.println("Starting ENCRYPTION...");
            PublicKeyCrypto publicKeyCrypto = new PublicKeyCrypto();
            publicKeyCrypto.generateKeyPair(2048);
            byte[] encryptedMsg = publicKeyCrypto.cipherWithPublicKey(message);

            System.out.println("Encrypted result (raw): " + new String(encryptedMsg));
            System.out.println("Encrypted result (Base64): " + Base64.getEncoder().encodeToString(encryptedMsg));

            //Decrypting a message with a key pair - encrypting with private-key
            System.out.println("Starting DECRYPTION...");
            byte[] decryptedMsg = publicKeyCrypto.decipherWithPrivateKey(encryptedMsg);

            System.out.println("Decrypted result: " + new String(decryptedMsg));

        } catch (Exception e) {
            System.out.println("Some error occurred -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void runDigitalSignatureTest() {
        try {
            DigitalSignatureCrypto digitalSignatureCrypto = new DigitalSignatureCrypto();

            //Creating key pair
            System.out.println("Starting creating a key pair...");
            KeyPair keyPair = digitalSignatureCrypto.generateKeyPair(2048);

            //Read a message to sign
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter message to cipher: ");
            String message = sc.nextLine();

            //Sign the message with the private key
            byte[] signature = digitalSignatureCrypto.signMessage(message, keyPair.getPrivate());
            String sign = new String(signature);
            System.out.println("Signature: " + sign);

            //Verify the signature with a public key
            boolean result = digitalSignatureCrypto.verifySignature(message, signature, keyPair.getPublic());
            if(result)
                System.out.println("Signature: IS VALID");
            else
                System.out.println("Signature: IS INVALID");

        } catch (Exception e) {
            System.out.println("Some error occurred -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Security.addProvider(new BouncyCastleProvider());
        int option = 0;
        do {
            System.out.println();
            System.out.println("Please select one of the available options:");
            System.out.println("[1] List available cryptography providers");
            System.out.println("[2] Message Digests");
            System.out.println("[3] Message Authentication Codes");
            System.out.println("[4] Using SecretKey crypto");
            System.out.println("[5] Using PublicKey crypto");
            System.out.println("[6] Using Digital signatures");

            System.out.println("[0] Exit");

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your option: ");
            option = sc.nextInt();

            // Process the selected option
            switch (option) {
                case 1:
                    // List the currently available cryptography providers
                    ListProviders();
                    break;
                case 2:
                    // Execute the different hashes tests
                    runDigestTests();
                    break;
                case 3:
                    // Execute the MAC tests
                    runMacTest();
                    break;
                case 4:
                    // Execute the secret key tests
                    runSecretKeyTest();
                    break;
                case 5:
                    // Execute the public key tests
                    runPublicKeyTest();
                    break;
                case 6:
                    // Execute the digital signature tests
                    runDigitalSignatureTest();
                    break;
                default: break;
            }
        } while(option!=0);
    }

}
