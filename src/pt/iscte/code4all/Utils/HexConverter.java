package pt.iscte.code4all.Utils;

public class HexConverter {
    public HexConverter() {
    }

    public String convert(byte[] bytes) {
        StringBuffer hexString = new StringBuffer();
        for(int i=0; i<bytes.length; i++) {
            hexString.append(Integer.toHexString(0xFF & bytes[i]));
        }
        return hexString.toString();
    }
}
