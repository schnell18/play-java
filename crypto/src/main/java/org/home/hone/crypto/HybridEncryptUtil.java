package org.home.hone.crypto;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class HybridEncryptUtil {

    // private static final String encodedPubPey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDoIjY+VacM/v0q47oQkbE4eVo4AS/Px07EMCmlYmRjY9x1OeippSppQ1eNRIuFCbZRqpMoayDO68UdWPCSqOt1I8Uw03MzVDmy38ZBo6dVTRrqWW9z7vbQQ1nWkEcUWcRTIQIktQ2ptO4AOlZa1x1/zvsNBodTNqhqCGPeTNUwyQIDAQAB";
    private static final String encodedPubPey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAPDFIRehienrogWS8Ikn/335eAKxCniHLaDqBo4erZaIz/ve/tAwYbdBz31VQ+/gFeF5BvFt9Xj0H2l6zbXIq48CAwEAAQ==";


    private static RSAPublicKey publicKey;

    static {
        try {
            byte[] pubKey = Base64.getDecoder().decode(encodedPubPey.getBytes("UTF-8"));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            if (pubKey.length > 0) {
                publicKey = (RSAPublicKey)keyFactory.generatePublic(new X509EncodedKeySpec(pubKey));
            }
        }
        catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static String encrypt(String plaintext)  {
        String key = String.format("%016d", System.currentTimeMillis());
        BlockEncryptUtil aesEncryptUtil = new BlockEncryptUtil(key, "AES/ECB/PKCS5Padding");
        return String.format("%s %s", aesEncryptUtil.encrypt(plaintext), encryptKey(key));
    }

    private static String encryptKey(String plaintext)  {
       try {
           byte[] content = plaintext.getBytes("UTF-8");
           Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
           cipher.init(1, publicKey);
           if (content.length > publicKey.getModulus().bitLength() / 8 - 11) {
               throw new IllegalArgumentException("Content overflow");
           }
           return new String(Base64.getEncoder().encode(cipher.doFinal(content)), "UTF-8");
       }
       catch (Exception var7) {
           throw new RuntimeException(var7);
       }
    }

    public static void main(String[] args) {
        String content = "{\"idType\": 2, \"idNo\": \"310110198810083833\", \"name\": \"Jack\", \"gender\": \"M\", \"birthDate\": 19881008, \"xRefNo\": \"1344242342434234\", \"serviceType\": 28 }";
        String encrypted = HybridEncryptUtil.encryptKey(content);
        System.out.println(encrypted);
    }
}
