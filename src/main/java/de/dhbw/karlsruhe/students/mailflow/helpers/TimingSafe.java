package de.dhbw.karlsruhe.students.mailflow.helpers;

import java.security.MessageDigest;

final public class TimingSafe {
    /**
     * Timing safe comparison whether `str1 == str2`
     *
     * @param str1
     * @param str2
     * @return boolean representing whether `str1 == str2`
     */
    public static boolean areStringsEqual(String str1, String str2) {
        byte[] bytes1 = str1.getBytes();
        byte[] bytes2 = str2.getBytes();

        return MessageDigest.isEqual(bytes1, bytes2);
    }
}
