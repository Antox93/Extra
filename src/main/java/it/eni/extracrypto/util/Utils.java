package it.eni.extracrypto.util;

import java.security.SecureRandom;
import java.util.Base64;

public class Utils {
    private Utils() {}

    public static String generateSalt(){
        SecureRandom sr = new SecureRandom();
        byte[] byteSalt = new byte[10];

        sr.nextBytes(byteSalt);

        return Base64.getEncoder().encodeToString(byteSalt);
    }
}
