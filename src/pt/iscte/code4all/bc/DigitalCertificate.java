package pt.iscte.code4all.bc;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v1CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.OutputStream;
import java.security.KeyPair;

public class DigitalCertificate {
    public static X509CertificateHolder createTrustAnchor(KeyPair pair, String sigAlg) throws Exception {
        X500NameBuilder x500NameBuilder = new X500NameBuilder(BCStyle.INSTANCE)
                .addRDN(BCStyle.C, "PT")
                .addRDN(BCStyle.ST, "Lisboa")
                .addRDN(BCStyle.L, "Lisboa")
                .addRDN(BCStyle.O, "ISCTE-IUL")
                .addRDN(BCStyle.CN, "Carlos Serrao");

        X500Name x500Name = x500NameBuilder.build();

        X509v1CertificateBuilder certificateBuilder = new JcaX509v1CertificateBuilder(x500Name, Utils.calculateSerialNumber(), Utils.calculateDate(0), Utils.calculateDate(24 *31), x500Name, pair.getPublic());
        ContentSigner signer = new JcaContentSignerBuilder(sigAlg).setProvider("BC").build(pair.getPrivate());
        return certificateBuilder.build(signer);
    }

}
