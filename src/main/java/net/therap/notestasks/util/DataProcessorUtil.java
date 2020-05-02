package net.therap.notestasks.util;

/**
 * @author tanmoy.das
 * @since 4/13/20
 */
public class DataProcessorUtil {

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}