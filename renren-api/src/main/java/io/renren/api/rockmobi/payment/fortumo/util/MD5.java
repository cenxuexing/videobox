package io.renren.api.rockmobi.payment.fortumo.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @program: renren-security
 * @description:
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-08-05 17:34
 **/
public class MD5 {
    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String args[]) throws NoSuchAlgorithmException
    {
        String s = "cuid=86526265display_type=useroperation_reference=G201904010008271955product_name=Fortumo_Indonesia_GameBox_Av=mobile3973b44728145e07156c6cdbd43cd949";
        System.out.println("Your HashCode Generated by MD5 is: " + getMd5(s));//274a9b153618c2c0a928e318343984af
    }
}