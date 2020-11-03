/**
 * Class to generate a X.509 v3 Certificate
 */
package pt.iscte.code4all.bc;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v1CertificateBuilder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.OutputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

/**
 * Class to create a digital certificate
 */
public class DigitalCertificate {
    /**
     * Creates a X.509 Digital Certificate
     * @param subjectPublicKey The subject public key
     * @param issuerPrivateKey The issuer (CA) private key
     * @param sigAlg The signature algorithm to be applied
     * @return The X.509 certificate
     * @throws Exception
     */
    public static X509Certificate createTrustAnchor(PublicKey subjectPublicKey, PrivateKey issuerPrivateKey, String sigAlg) throws Exception {
        /* X500 DN of the Subject*/
        X500NameBuilder x500NameBuilderSubject = new X500NameBuilder(BCStyle.INSTANCE)
                .addRDN(BCStyle.C, "PT")
                .addRDN(BCStyle.ST, "Lisboa")
                .addRDN(BCStyle.L, "Lisboa")
                .addRDN(BCStyle.O, "ISCTE-IUL")
                .addRDN(BCStyle.CN, "Carlos Serrao");

        /* X500 DN of the CA (Issuer)*/
        X500NameBuilder x500NameBuilderIssuer = new X500NameBuilder(BCStyle.INSTANCE)
                .addRDN(BCStyle.C, "PT")
                .addRDN(BCStyle.ST, "Almada")
                .addRDN(BCStyle.L, "Almada")
                .addRDN(BCStyle.O, "FEIJO")
                .addRDN(BCStyle.CN, "FEIJO-HOME");

        X500Name x500NameSubject = x500NameBuilderSubject.build();
        X500Name x500NameIssuer = x500NameBuilderIssuer.build();

        X509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(x500NameIssuer, Utils.calculateSerialNumber(), Utils.calculateDate(0), Utils.calculateDate(24 *31), x500NameSubject, subjectPublicKey);
        ContentSigner signer = new JcaContentSignerBuilder(sigAlg).setProvider("BC").build(issuerPrivateKey);

        X509Certificate certificate = new JcaX509CertificateConverter().setProvider("BC").getCertificate(certificateBuilder.build(signer));

        return certificate;
    }
}
