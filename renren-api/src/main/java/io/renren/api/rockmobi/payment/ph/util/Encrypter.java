package io.renren.api.rockmobi.payment.ph.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

public class Encrypter {
    private static Cipher ecipher;
    private static Cipher dcipher;
    final static String keyPassword = "awdrgyji";
    static SecretKeySpec key = new SecretKeySpec(keyPassword.getBytes(), "DES");

    static AlgorithmParameterSpec paramSpec = new IvParameterSpec(keyPassword.getBytes());

    public static String encrypt(String str) throws Exception {
        ecipher = Cipher.getInstance("DES/CFB8/NoPadding");
        ecipher.init(Cipher.ENCRYPT_MODE, key,paramSpec);
        byte[] utf8 = str.getBytes("UTF8");
        byte[] enc = ecipher.doFinal(utf8);
        return new sun.misc.BASE64Encoder().encode(enc);
    }

    public static String decrypt(String str) throws Exception {
        dcipher = Cipher.getInstance("DES/CFB8/NoPadding");
        dcipher.init(Cipher.DECRYPT_MODE, key,paramSpec);
        byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
        byte[] utf8 = dcipher.doFinal(dec);
        return new String(utf8, "UTF-8");
    }
}
