package pt.iscte.code4all.bc;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.cert.X509Certificate;

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
            KeyPair keyPair = Utils.generateKeyPair(2048);
            X509CertificateHolder holder = DigitalCertificate.createTrustAnchor(keyPair, "SHA256withRSA");
            X509Certificate certificate = new JcaX509CertificateConverter().setProvider("BC").getCertificate(holder);


            System.out.println(certificate.getEncoded().toString());
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
