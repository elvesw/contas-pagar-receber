package br.com.pontek.util.jsf;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {
 
    public static String convertPasswordToMD5(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
        return String.format("%32x", hash);
    }
    
}