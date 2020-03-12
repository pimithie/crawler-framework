package com.xiaqi.crawl.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileMd5Checker {

    private FileMd5Checker() {
    }

    public static boolean isEqualsMd5(String expected, String file) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(getAllBytes(file));
        byte[] digest = md.digest();
        StringBuilder builder = new StringBuilder();
        for (byte b : digest) {
            builder.append(getHexByBinary(b));
        }
        return builder.toString().equalsIgnoreCase(expected);
    }

    private static byte[] getAllBytes(String file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        return bytes;
    }

    private static String getHexByBinary(byte b) {
        final int mask = (1 << 3) + (1 << 2) + (1 << 1) + 1;
        StringBuilder builder = new StringBuilder();
        return builder.append(getHexRepresention((b >> 4) & mask)).append(getHexRepresention(b & mask)).toString();
    }

    private static char getHexRepresention(int n) {
        if (n > 15 || n < 0) {
            throw new RuntimeException("n > 15 or n < 0");
        }
        if (n >= 0 && n <= 9) {
            return (char) ('0' + n);
        }
        switch (n) {
            case 10 : return 'a';
            case 11 : return 'b';
            case 12 : return 'c';
            case 13 : return 'd';
            case 14 : return 'e';
            case 15 : return 'f';
        }
        throw new RuntimeException("invalid number:"+n);
    }

}
