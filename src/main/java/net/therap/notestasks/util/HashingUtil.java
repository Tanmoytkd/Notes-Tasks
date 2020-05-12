package net.therap.notestasks.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author tanmoy.das
 * @since 4/13/20
 */
public class HashingUtil {

    public static String sha256Hash(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                text.getBytes(StandardCharsets.UTF_8));
        text = DataProcessorUtil.bytesToHex(encodedhash);
        return text;
    }

    public static String md5Hex(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return DataProcessorUtil.bytesToHex(md.digest(message.getBytes("CP1252")));
    }
}