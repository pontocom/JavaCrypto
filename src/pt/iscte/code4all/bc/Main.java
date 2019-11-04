package pt.iscte.code4all.bc;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import java.security.*;

public class Main {
    public static void ListProviders() {
        Provider[] installedProviders = Security.getProviders();
        for(int i = 0; i != installedProviders.length; i++) {
            System.out.print(installedProviders[i].getName());
            System.out.print(": ");
            System.out.print(installedProviders[i].getInfo());
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        ListProviders();

        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey RSAPublicKey = keyPair.getPublic();
            PrivateKey RSAPrivateKey = keyPair.getPrivate();

            X500Name subjectDN = new X500Name("CN = carlos.serrao@iscte.pt");
            SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(ASN1Sequence.getInstance(RSAPublicKey.getEncoded()));

            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
