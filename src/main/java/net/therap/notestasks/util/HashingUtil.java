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

    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i]
                    & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String md5Hex(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest md =
                MessageDigest.getInstance("MD5");
        return hex(md.digest(message.getBytes("CP1252")));
    }
}