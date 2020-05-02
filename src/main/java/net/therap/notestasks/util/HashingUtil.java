package net.therap.notestasks.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author tanmoy.das
 * @since 4/13/20
 */
public class HashingUtil {

    public static String getHash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        password = DataProcessorUtil.bytesToHex(encodedhash);
        return password;
    }
}