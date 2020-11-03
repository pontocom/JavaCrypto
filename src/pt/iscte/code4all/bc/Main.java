package pt.iscte.code4all.bc;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.encoders.Base64;

import java.io.StringWriter;
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
        //ListProviders();

        try {
            /* This is the SUBJECT KeiPair */
            KeyPair subjectKeyPair = Utils.generateKeyPair(2048);
            /* This is the CA (Issuer) KeyPair */
            KeyPair issuerKeyPair = Utils.generateKeyPair(4096);

            X509Certificate certificate = DigitalCertificate.createTrustAnchor(subjectKeyPair.getPublic(), issuerKeyPair.getPrivate(), "SHA512withRSA");
            //X509Certificate certificate = new JcaX509CertificateConverter().setProvider("BC").getCertificate(holder);

            System.out.println("CERTIFICATE = ");
            System.out.println(certificate.toString());
            /* writes in Base64 format */
            System.out.println(new String(Base64.encode(certificate.getEncoded())));
            /* writes in PEM format */
            StringWriter stringWriter = new StringWriter();
            JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter);
            pemWriter.writeObject(certificate);
            pemWriter.flush();
            pemWriter.close();
            System.out.println(stringWriter.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
