package org.home.hone.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class BlockEncryptUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockEncryptUtil.class);
    private ThreadLocal<Cipher> encryptCipherHolder = new ThreadLocal<>();
    private ThreadLocal<Cipher> decryptCipherHolder = new ThreadLocal<>();

    private final String encryptKey;
    private final String algorithmSpec;
    private final String algorithm;

    public BlockEncryptUtil(String encryptKey, String algorithmSpec) {
        this.encryptKey = encryptKey;
        this.algorithmSpec = algorithmSpec;
        String[] algorithmStr = algorithmSpec.split("/");
        if (algorithmStr.length <= 0)
            throw new IllegalArgumentException("Invalid algorithmSpec: " + algorithmSpec);
        this.algorithm = algorithmStr[0];
    }

    public String encrypt(String plainText) {
        try {
            Cipher cipher = encryptCipherHolder.get();
            if (cipher == null)
                cipher = initEncryptCipher();
            byte[] encrypted = cipher.doFinal(plainText.getBytes("utf-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        }
        catch (IllegalBlockSizeException e) {
            LOGGER.error("IllegalBlockSizeException", e);
            throw new RuntimeException("IllegalBlockSizeException");
        }
        catch (BadPaddingException e) {
            LOGGER.error("BadPaddingException", e);
            throw new RuntimeException("BadPaddingException");
        }
        catch (UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException", e);
            throw new RuntimeException("UnsupportedEncodingException");
        }
    }

    public String decrypt(String cypher) {
        try {
            byte[] encrypted1 = Base64.getDecoder().decode(cypher);
            Cipher cipher = decryptCipherHolder.get();
            if (cipher == null)
                cipher = initDecryptCipher();

            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "utf-8");
        }
        catch (BadPaddingException e) {
            LOGGER.error("BadPaddingException", e);
            throw new RuntimeException("BadPaddingException");
        }
        catch (UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException", e);
            throw new RuntimeException("UnsupportedEncodingException");
        }
        catch (IllegalBlockSizeException e) {
            LOGGER.error("IllegalBlockSizeException", e);
            throw new RuntimeException("IllegalBlockSizeException");
        }
    }

    private Cipher initDecryptCipher() {
        try {
            byte[] raw = encryptKey.getBytes("utf-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, algorithm);
            Cipher cipher = Cipher.getInstance(algorithmSpec);//算法/模式/补码方式
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            decryptCipherHolder.set(cipher);
            return cipher;
        }
        catch(UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException", e);
            throw new RuntimeException("UnsupportedEncodingException");
        }
        catch (NoSuchPaddingException e) {
            LOGGER.error("NoSuchPaddingException", e);
            throw new RuntimeException("NoSuchPaddingException");
        }
        catch (NoSuchAlgorithmException e) {
            LOGGER.error("NoSuchAlgorithmException", e);
            throw new RuntimeException("NoSuchAlgorithmException");
        }
        catch (InvalidKeyException e) {
            LOGGER.error("InvalidKeyException", e);
            throw new RuntimeException("InvalidKeyException");
        }
    }

    private Cipher initEncryptCipher() {
        try {
            byte[] raw = encryptKey.getBytes("utf-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, algorithm);
            Cipher cipher = Cipher.getInstance(algorithmSpec);//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encryptCipherHolder.set(cipher);
            return cipher;
        }
        catch(UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException", e);
            throw new RuntimeException("UnsupportedEncodingException");
        }
        catch (NoSuchPaddingException e) {
            LOGGER.error("NoSuchPaddingException", e);
            throw new RuntimeException("NoSuchPaddingException");
        }
        catch (NoSuchAlgorithmException e) {
            LOGGER.error("NoSuchAlgorithmException", e);
            throw new RuntimeException("NoSuchAlgorithmException");
        }
        catch (InvalidKeyException e) {
            LOGGER.error("InvalidKeyException", e);
            throw new RuntimeException("InvalidKeyException");
        }
    }

}
