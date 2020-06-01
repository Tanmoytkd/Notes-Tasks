package net.therap.notestasks.util;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author tanmoy.das
 * @since 4/24/20
 */
public class RandomGeneratorUtil {

    private RandomGeneratorUtil() {
    }

    public static String createRandomString(int n) {
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString = new String(array, StandardCharsets.UTF_8);

        StringBuilder r = new StringBuilder();

        String alphaNumericString = randomString.replaceAll("[^A-Za-z0-9]", "");

        for (int k = 0; k < alphaNumericString.length(); k++) {
            if (Character.isLetter(alphaNumericString.charAt(k))
                    && (n > 0)
                    || Character.isDigit(alphaNumericString.charAt(k))
                    && (n > 0)) {

                r.append(alphaNumericString.charAt(k));
                n--;
            }
        }

        return r.toString();
    }

    public static String createRandomString() {
        return createRandomString(11);
    }
}
